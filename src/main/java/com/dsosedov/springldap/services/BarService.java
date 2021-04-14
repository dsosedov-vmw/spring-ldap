package com.dsosedov.springldap.services;

import org.springframework.stereotype.Service;

@Service
public class BarService {

    public Boolean bar(Integer val) {
        return val % 2 == 0;
    }

}
