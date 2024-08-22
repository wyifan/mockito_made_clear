package com.yifan.foundation.service;

import com.yifan.foundation.base.Person;
import com.yifan.foundation.repository.PersonRepository;

import java.util.List;

/**
 * package_name: com.yifan.foundation.service
 * author: wyifa
 * date: 2024/8/22
 * description:
 */
public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<String> getLastNames() {
        return personRepository.findAll()
                .stream()
                .map(Person::getLast)
                .toList();
    }

}
