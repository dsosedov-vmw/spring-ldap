package com.dsosedov.springldap.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/foo")
public class FooController {

    @GetMapping
    public String[] get() {
        return new String[]{
                "foo",
                "bar",
                "baz"
        };
    }

}
