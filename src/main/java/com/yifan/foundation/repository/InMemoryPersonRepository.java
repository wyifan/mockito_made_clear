package com.yifan.foundation.repository;

import com.yifan.foundation.base.Person;

import java.util.*;
import java.util.stream.Collectors;

/**
 * package_name: com.yifan.foundation.repository
 * author: wyifa
 * date: 2024/8/22
 * description:
 */
public class InMemoryPersonRepository implements PersonRepository {
    private final List<Person> people = Collections.synchronizedList(new ArrayList<>());

    /**
     * add final to make the others do not change this logic
     * @param person
     */
    @Override
    public final Person save(Person person) {
        synchronized (people) {
            people.add(person);
        }
        return person;
    }

    @Override
    public Optional<Person> findById(int id) {
        Map<Integer, Person> peopleMap = people.stream()
                .collect(Collectors.toMap(Person::getId, p -> p));
        return Optional.ofNullable(peopleMap.get(id));
    }

    @Override
    public List<Person> findAll() {
        return people;
    }

    @Override
    public long count() {
        return people.size();
    }

    /**
     * add final to make the others do not change this logic
     * @param person
     */
    @Override
    public final void delete(Person person) {
        synchronized (people) {
            people.remove(person);
        }
    }
}
