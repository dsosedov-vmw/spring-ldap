package com.dsosedov.springldap.repositories;

import com.dsosedov.springldap.entities.Foo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FooRepository extends CrudRepository<Foo, String> {
}
