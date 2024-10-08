package com.yifan.foundation;

import com.yifan.foundation.base.Person;
import com.yifan.foundation.repository.InMemoryPersonRepository;
import com.yifan.foundation.repository.PersonRepository;
import com.yifan.foundation.service.DefaultTranslationService;
import com.yifan.foundation.service.TranslationService;

import java.util.Optional;

public class HelloMockito {
    private static final String GREETING = "Hello %s, from Mockito!";

    private final PersonRepository personRepository;
    private final TranslationService translationService;

    public HelloMockito(PersonRepository personRepository, TranslationService translationService) {
        this.personRepository = personRepository;
        this.translationService = translationService;
    }

    public HelloMockito(PersonRepository personRepository) {
        this(personRepository, new DefaultTranslationService());
    }

    public HelloMockito(TranslationService service) {
        this(new InMemoryPersonRepository(), service);
    }

    public String greet(int id, String sourceLanguage, String targetLanguage) {
        Optional<Person> person = personRepository.findById(id);
        String name = person.map(Person::getFirst).orElse("World");

        // do not use: String.format(greeting, name) just use name, then prone:
        //      org.mockito.exceptions.misusing.PotentialStubbingProblem:
        //      Strict stubbing argument mismatch.
        return translationService.translate(String.format(GREETING, name), sourceLanguage, targetLanguage);
    }
}
