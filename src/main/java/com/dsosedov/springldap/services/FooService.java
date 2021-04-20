package com.dsosedov.springldap.services;

import com.dsosedov.springldap.entities.Foo;
import com.dsosedov.springldap.repositories.FooRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FooService {

    private final FooRepository fooRepository;

    public FooService(FooRepository fooRepository) {
        this.fooRepository = fooRepository;
    }

    public String[] foo() {
        Iterable<Foo> foos = fooRepository.findAll();
        List<String> fooVals = new ArrayList<>();
        for (Foo foo : foos) {
            fooVals.add(foo.getVal());
        }
        String[] result = new String[fooVals.size()];
        return fooVals.toArray(result);
    }

    public void add(String val) {
        fooRepository.save(new Foo(val));
    }

}
