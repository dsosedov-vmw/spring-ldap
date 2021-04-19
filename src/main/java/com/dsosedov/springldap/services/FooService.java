package com.dsosedov.springldap.services;

import org.springframework.stereotype.Service;

@Service
public class FooService {

    public String[] foo() {
        return new String[]{
                "foo",
                "bar",
                "baz"
        };
    }

}
