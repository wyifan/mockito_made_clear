package com.yifan.foundation;

import com.yifan.foundation.base.Person;
import com.yifan.foundation.repository.PersonRepository;
import com.yifan.foundation.service.TranslationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

/**
 * package_name: com.yifan.foundation
 * author: wyifa
 * date: 2024/8/22
 * description:
 */
@ExtendWith(MockitoExtension.class)
class HelloMockitoTest {
    @Mock
    private PersonRepository personRepository;

    @Mock
    private TranslationService translationService;

    // TODO: why using injectMock instead of Mock annotation?
    @InjectMocks
    private HelloMockito helloMockito;

    @Test
    @DisplayName("Greet Admiral Hopper")
    void greetAtAPersonThatExists() {
        when(personRepository.findById(anyInt()))
                .thenReturn(Optional.of(
                new Person(1, "Grace", "Hopper",
                        LocalDate.of(1906, Month.DECEMBER, 9))));

        when(translationService
                .translate("Hello Grace, from Mockito!","en","en"))
                .thenReturn("Hello Grace, from Mockito!");

        String greeting = helloMockito.greet(1,"en","en");
        assertEquals("Hello Grace, from Mockito!", greeting);

        // verify the methods are called once, in the right order
        // TODO: do not know why
        InOrder inOrder= Mockito.inOrder(personRepository,translationService);
        inOrder.verify(personRepository).findById(anyInt());
        inOrder.verify(translationService).translate(anyString(), eq("en"), eq("en"));

    }

    @Test
    @DisplayName("Greet a person not in the database")
    void greetAtAPersonThatDoesNotExist() {
        when(personRepository.findById(anyInt())).thenReturn(Optional.empty());
        when(translationService.translate("Hello World, from Mockito!","en","en"))
                .thenReturn("Hello World, from Mockito!");

        String greeting = helloMockito.greet(100,"en","en");
        assertEquals("Hello World, from Mockito!", greeting);

        InOrder inOrder= Mockito.inOrder(personRepository,translationService);
        inOrder.verify(personRepository).findById(anyInt());
        inOrder.verify(translationService).translate(anyString(), eq("en"), eq("en"));
    }


}