package com.dsosedov.springldap.services;

import com.dsosedov.springldap.entities.Foo;
import com.dsosedov.springldap.repositories.FooRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FooServiceTests {

    private FooService fooService;

    @Mock
    private FooRepository fooRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        List<Foo> foos = new ArrayList<>();
        foos.add(new Foo("d"));
        foos.add(new Foo("e"));
        foos.add(new Foo("f"));
        when(fooRepository.findAll()).thenReturn(foos);
        this.fooService = new FooService(fooRepository);
    }

    @Test
    void fooShouldReturnArray() {
        assertArrayEquals(new String[]{"d", "e", "f"}, fooService.foo());
    }

    @Test
    void addShouldSave() {
        fooService.add("foo");
        verify(fooRepository).save(new Foo("foo"));
    }
}
