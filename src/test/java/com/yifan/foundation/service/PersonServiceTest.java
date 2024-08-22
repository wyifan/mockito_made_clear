package com.yifan.foundation.service;

import com.yifan.foundation.base.Person;
import com.yifan.foundation.repository.PersonRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * package_name: com.yifan.foundation.service
 * author: wyifa
 * date: 2024/8/22
 * description:
 */
class PersonServiceTest {

    private final List<Person> people = Arrays.asList(
            new Person(1, "Grace", "Hopper", LocalDate.of(1906, Month.DECEMBER, 9)),
            new Person(2, "Ada", "Lovelace", LocalDate.of(1815, Month.DECEMBER, 10)),
            new Person(3, "Beast", "Bitch", LocalDate.of(1953, Month.JUNE, 15)),
            new Person(14, "Anita", "Borg", LocalDate.of(1949, Month.JANUARY, 17)),
            new Person(5, "Barbara", "Liskov", LocalDate.of(1939, Month.NOVEMBER, 7))
    );

    private final Map<Integer, Person> peopleMap = people.stream().collect(
            Collectors.toMap(Person::getId, p -> p)
    );

    @Test
    @DisplayName("Mock generate a class with method return default values")
    void defaultImplementations() {
        PersonRepository personRepository = mock(PersonRepository.class);

        // Mockito does better than just return nulls for mocked reference types.
        // eg: if the return type is a method is a list, it return an empty list.
        assertAll(
                () -> assertNull(personRepository.save(any(Person.class))),
                () -> assertTrue(personRepository.findById(anyInt()).isEmpty()),
                () -> assertTrue(personRepository.findAll().isEmpty()),
                () -> assertEquals(0, personRepository.count())
        );
    }

    @Test
    @DisplayName("using mock method")
    void getLastNames_usingMockMethod() {
        // create a stub for the PersonRepository
        PersonRepository mockRepo = mock(PersonRepository.class);
        when(mockRepo.findAll()).thenReturn(people);

        // inject into the service
        PersonService personService = new PersonService(mockRepo);

        // get the last names
        List<String> lastNames = personService.getLastNames();

        // check the results
        assertThat(lastNames).contains("Hopper", "Lovelace", "Bitch", "Borg", "Liskov");

        verify(mockRepo).findAll();
    }
}