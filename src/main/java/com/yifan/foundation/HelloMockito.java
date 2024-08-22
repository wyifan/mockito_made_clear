package com.yifan.foundation;

import com.yifan.foundation.base.Person;

import java.util.Optional;

public class HelloMockito {
    private String greeting = "Hello %s, from Mockito!";

    private final PersonRepository personRepository;
    private final TranslationService translationService;

    public HelloMockito(PersonRepository personRepository, TranslationService translationService) {
        this.personRepository = personRepository;
        this.translationService = translationService;
    }

    public String greet(int id, String sourceLanguage, String targetLanguage) {
        Optional<Person> person = personRepository.findById(id);
        String name = person.map(Person::getFirst).orElse("World");

        return translationService.translate(name, sourceLanguage, targetLanguage);
    }
}
