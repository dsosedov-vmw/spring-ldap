package com.dsosedov.springldap.services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class FooServiceTests {

    private final FooService fooService;

    public FooServiceTests() {
        this.fooService = new FooService();
    }

    @Test
    void fooShouldReturnArray() {
        assertArrayEquals(new String[]{"foo", "bar", "baz"}, fooService.foo());
    }
}
