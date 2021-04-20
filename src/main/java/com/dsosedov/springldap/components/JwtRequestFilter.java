package com.dsosedov.springldap.components;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Value("${ldap.url}")
    private String ldapUrl;

    @Value("${spring.ldap.embedded.base-dn}")
    private String baseDn;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwt = authorizationHeader.substring(7);
            Claims claims = JwtUtil.decode(jwt);
            username = claims.getSubject();
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            List<String> groups = getUserGroups(username);
            List<GrantedAuthority> authorities = new ArrayList<>();
            for (int i = 0; i < groups.size(); i++) {
                authorities.add(new SimpleGrantedAuthority("ROLE_"+groups.get(i)));
            }
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    username, null, authorities
            );
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
        chain.doFilter(request, response);
    }

    // TODO: re-write this using ldapsdk
    public ArrayList<String> getUserGroups(String username) {
        ArrayList<String> list = new ArrayList<>();
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, ldapUrl);
        try {
            DirContext ctx = new InitialDirContext(env);
            SearchControls ctls = new SearchControls();
            String[] attrIDs = {"ou"};
            ctls.setReturningAttributes(attrIDs);
            ctls.setSearchScope(SearchControls.ONELEVEL_SCOPE);

            NamingEnumeration searchResult = ctx.search("ou=groups", "(uniqueMember=uid="+username+",ou=people,"+baseDn+")", ctls);
            while (searchResult.hasMore()) {
                SearchResult rslt = (SearchResult) searchResult.next();
                Attributes attrs = rslt.getAttributes();
                String groups = attrs.get("ou").toString();
                String[] groupname = groups.split(": ");
                list.add(groupname[1]);
            }
            ctx.close();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return list;
    }
}
