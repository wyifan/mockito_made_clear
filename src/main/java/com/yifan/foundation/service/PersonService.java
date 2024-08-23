package com.yifan.foundation.service;

import com.yifan.foundation.base.Person;
import com.yifan.foundation.repository.PersonRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<Person> findByIds(int... ids) {
        return Arrays.stream(ids)
                .mapToObj(personRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
//                .toList(); // different: toList() available since 16 and unmodified
    }

    public void deleteAll() {
        personRepository.findAll()
                .forEach(personRepository::delete);
    }

    public Integer getHighestId() {
        return personRepository.findAll()
                .stream()
                .map(Person::getId)
                .max(Integer::compareTo)
                .orElse(0);
    }

    public Person createPerson(int id, String first, String last, String dob) {
        Person person = new Person(id, first, last, LocalDate.parse(dob));
        return personRepository.save(person);
    }

    public List<Integer> savePersons(Person... persons) {
        return Arrays.stream(persons)
                .map(personRepository::save)
                .map(Person::getId)
                .collect(Collectors.toList());
    }

}
