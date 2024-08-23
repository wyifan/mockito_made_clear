package com.yifan.foundation.service;

import com.yifan.foundation.base.Person;
import com.yifan.foundation.repository.InMemoryPersonRepository;
import com.yifan.foundation.repository.PersonRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

/**
 * package_name: com.yifan.foundation.service
 * author: wyifa
 * date: 2024/8/22
 * description:
 * benefit of annotation approach: Mockito tries to inject the mocks into the class
 * under test for you.
 * How Mockito achieve this:
 * 1. calls the largest constructor on the test class if it takes the right types of arguments
 * 2. calls setter methods of the proper type
 * 3. set the fields directly
 */
// when using annotation mock, need to add extendWith annotation in the class,
// otherwise error-prone with NullPointerException.
@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    private final List<Person> people = Arrays.asList(new Person(1, "Grace", "Hopper", LocalDate.of(1906, Month.DECEMBER, 9)), new Person(2, "Ada", "Lovelace", LocalDate.of(1815, Month.DECEMBER, 10)), new Person(3, "Beast", "Bitch", LocalDate.of(1953, Month.JUNE, 15)), new Person(14, "Anita", "Borg", LocalDate.of(1949, Month.JANUARY, 17)), new Person(5, "Barbara", "Liskov", LocalDate.of(1939, Month.NOVEMBER, 7)));

    private final Map<Integer, Person> peopleMap = people.stream().collect(Collectors.toMap(Person::getId, p -> p));

    @Mock
    private PersonRepository mockRepo;

    @InjectMocks
    private PersonService service;

    @Test
    @DisplayName("Mock generate a class with method return default values")
    void defaultImplementations() {
        PersonRepository personRepository = mock(PersonRepository.class);

        // Mockito does better than just return nulls for mocked reference types.
        // eg: if the return type is a method is a list, it return an empty list.
        assertAll(() -> assertNull(personRepository.save(any(Person.class))), () -> assertTrue(personRepository.findById(anyInt()).isEmpty()), () -> assertTrue(personRepository.findAll().isEmpty()), () -> assertEquals(0, personRepository.count()));
    }

    @Test
    @DisplayName("using mock method")
    void getLastNames_usingMockMethod() {
        // create a stub for the PersonRepository
        PersonRepository mockRepository = mock(PersonRepository.class);
        when(mockRepository.findAll()).thenReturn(people);

        // inject into the service
        PersonService personService = new PersonService(mockRepository);

        // get the last names
        List<String> lastNames = personService.getLastNames();

        // check the results
        assertThat(lastNames).contains("Hopper", "Lovelace", "Bitch", "Borg", "Liskov");

        verify(mockRepo).findAll();
    }

    @Test
    @DisplayName("Using annotation")
    void getLastNames_usingAnnotation() {
        when(mockRepo.findAll()).thenReturn(people);

        assertThat(service.getLastNames()).contains("Hopper", "Lovelace", "Bitch", "Borg", "Liskov");

        verify(mockRepo).findAll();
    }

    @Test
    @DisplayName("Explicit set the findbyid returns")
    void findById_explicitWhens() {
        when(mockRepo.findById(0)).thenReturn(Optional.of(people.get(0)));
        when(mockRepo.findById(1)).thenReturn(Optional.of(people.get(1)));
        when(mockRepo.findById(2)).thenReturn(Optional.of(people.get(2)));
        when(mockRepo.findById(3)).thenReturn(Optional.of(people.get(3)));
        when(mockRepo.findById(4)).thenReturn(Optional.of(people.get(4)));

        when(mockRepo.findById(5)).thenReturn(Optional.empty());

        List<Person> personList = service.findByIds(0, 1, 2, 3, 4, 5);

        assertThat(personList).containsExactlyElementsOf(people);
    }

    @Test
    @DisplayName("use anyInt to set the repository")
    @SuppressWarnings("unchecked")
    void findById_thenReturnWithMultipleArgs() {
        when(mockRepo.findById(anyInt())).thenReturn(Optional.of(people.get(0)), Optional.of(people.get(1)), Optional.of(people.get(2)), Optional.of(people.get(3)), Optional.of(people.get(4)), Optional.empty());

        List<Person> personList = service.findByIds(0, 1, 2, 3, 4, 5);

        assertThat(personList).containsExactlyElementsOf(people);
    }

    @Test
    @DisplayName("Multiple calls of then methods")
    void testMultipleCalls() {
        when(mockRepo.findById(anyInt())).thenReturn(Optional.of(people.get(0))).thenThrow(new IllegalAccessError("Person with id not found")).thenReturn(Optional.of(people.get(1))).thenReturn(Optional.empty());

        List<Person> personList = service.findByIds(0, 1, 2, 3, 4, 5);

        assertThat(personList).containsExactlyElementsOf(people);
    }

    @Test
    @DisplayName("Delete method using null")
    void deleteWithNulls() {
        when(mockRepo.findAll()).thenReturn(Arrays.asList((Person) null));

        // this will not compile
//        when(mockRepo.delete(null)).thenThrow(RuntimeException.class);

        // use alternative
        doThrow(RuntimeException.class).when(mockRepo).delete(null);

        assertThrows(RuntimeException.class, () -> service.deleteAll());

        verify(mockRepo).delete(null);
    }

    @Test
    @DisplayName("Find by id that not exists")
    void findByIdThatDoesNotExist() {
        when(mockRepo.findById(anyInt())).thenReturn(Optional.empty());

        List<Person> personList = service.findByIds(999);
        assertTrue(personList.isEmpty());

        verify(mockRepo).findById(anyInt());

    }

    @Test
    @DisplayName("Do not use argThat with integers, will failed!")
    void findByIdsThatDoNotExist_argThat() {
        when(mockRepo.findById(argThat(id -> id > 14))).thenReturn(Optional.empty());

        List<Person> personList = service.findByIds(15, 42, 78, 999);

        assertTrue(personList.isEmpty());

        // verify the method execute times
        verify(mockRepo, times(4)).findById(anyInt());
    }

    @Test
    @DisplayName("use intThat instead of argThat, success!")
    void findByIdsThatDoNotExist_intThat() {
        when(mockRepo.findById(intThat(id -> id > 14))).thenReturn(Optional.empty());

        List<Person> personList = service.findByIds(15, 42, 78, 999);

        assertTrue(personList.isEmpty());

        // verify the method execute times
        verify(mockRepo, times(4)).findById(anyInt());
    }

    @Test
    @DisplayName("getMaxId test")
    void findMaxId() {
        when(mockRepo.findAll()).thenReturn(people);

        assertThat(service.getHighestId()).isEqualTo(14);

        verify(mockRepo).findAll();
    }

    @Test
    @DisplayName("GetMaxId test using BDD: Behavior Driven Development")
    void findMaxId_BDD() {
        given(mockRepo.findAll()).willReturn(people);

        assertThat(service.getHighestId()).isEqualTo(14);

        // times(1) can be emitted because of override version of should
        then(mockRepo).should(times(1)).findAll();
    }

    @Captor
    private ArgumentCaptor<Person> personCaptor;

    @Test
    @DisplayName("Capturing Argument")
    void createPersonUsingDateString() {
        Person hopper = people.get(0);

        when(mockRepo.save(hopper)).thenReturn(hopper);

        Person actual = service.createPerson(1, "Grace", "Hopper", "1906-12-09");

        // Argument captors allow you to find out exactly what arguments were supplied to a mocked method,
        // even when that argument is created as a local variable in the implementation.
        verify(mockRepo).save(personCaptor.capture());

        assertThat(personCaptor.getValue()).isEqualTo(hopper);
        assertThat(actual).isEqualTo(hopper);
    }

    @Test
    @DisplayName("Save persons test")
    void savePersons() {
        when(mockRepo.save(any(Person.class))).thenReturn(people.get(0), people.get(1), people.get(2), people.get(3), people.get(4));

        // test the service
        assertEquals(List.of(1, 2, 3, 14, 5), service.savePersons(people.toArray(Person[]::new)));

        verify(mockRepo, times(people.size())).save(any(Person.class));
        verify(mockRepo, never()).delete(any(Person.class));
    }

    @Test
    @DisplayName("User answer")
    void savePersons_UserAnswer() {
        // lambda expression implementation of Answer<Person>
        // TODO: why use getArgument(0)？
        when(mockRepo.save(any(Person.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        List<Integer> ids = service.savePersons(people.toArray(Person[]::new));

        List<Integer> actual = people.stream()
                .map(Person::getId)
                .collect(Collectors.toList());

        assertEquals(ids, actual);
    }

    @Test
    @DisplayName("Test using in memory repository")
    void savePersons_InMemoryRepository() {
        PersonRepository personRepository = new InMemoryPersonRepository();
        PersonService personService = new PersonService(personRepository);

        personService.savePersons(people.toArray(Person[]::new));

        assertThat(personRepository.findAll()).isEqualTo(people);

    }

    @Test
    @DisplayName("Test using spy")
    void  spyOnRepository() {
        PersonRepository personRepository = spy(new InMemoryPersonRepository());
        PersonService personService = new PersonService(personRepository);

        personService.savePersons(people.toArray(Person[]::new));

        assertThat(personRepository.findAll()).isEqualTo(people);

        // verify the method calls on the spy
        verify(personRepository, times(people.size())).save(any(Person.class));
    }
}