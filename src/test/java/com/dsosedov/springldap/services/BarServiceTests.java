package com.dsosedov.springldap.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BarServiceTests {

    private BarService barService;

    @BeforeEach
    void setUp() {
        barService = new BarService();
    }

    @Test
    void even() {
        assertTrue(barService.bar(2));
    }

    @Test
    void odd() {
        assertFalse(barService.bar(1));
    }

}
