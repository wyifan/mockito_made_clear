package com.yifan.foundation;

import com.yifan.foundation.base.Person;

import java.util.List;
import java.util.Optional;

/**
 * package_name: com.yifan.foundation
 * author: wyifa
 * date: 2024/8/22
 * description:
 */
public interface PersonRepository {
    Person save(Person person);

    Optional<Person> findById(int id);

    List<Person> findAll();

    long count();

    void delete(Person person);
}
