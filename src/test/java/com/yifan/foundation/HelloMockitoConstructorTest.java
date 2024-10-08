package com.yifan.foundation;

import com.yifan.foundation.base.Person;
import com.yifan.foundation.repository.PersonRepository;
import com.yifan.foundation.service.DefaultTranslationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedConstruction;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

/**
 * package_name: com.yifan.foundation
 * author: wyifa
 * date: 2024/8/24
 * description:
 */
@ExtendWith(MockitoExtension.class)
class HelloMockitoConstructorTest {

    @Test
    @DisplayName("test constructor with answer")
    void greetWithMockedConstructorWithAnswer() {
        // mock for repo
        PersonRepository personRepository = mock(PersonRepository.class);

        when(personRepository.findById(anyInt()))
                .thenReturn(Optional.of(new Person(1, "Grace", "Hopper", LocalDate.now())));

        // Mock for translator (instantiated inside HelloMockito constructor)
        try (MockedConstruction<DefaultTranslationService> ignored =
                     mockConstructionWithAnswer(DefaultTranslationService.class,
                             invocation -> invocation.getArgument(0) + " (translated)",
                             invocation -> invocation.getArgument(0) + " (translated again)")) {

            // Instantiate HelloMockito with mocked repo and locally instantiated translator
            HelloMockito hello = new HelloMockito(personRepository);
            String greeting = hello.greet(1, "en", "en");
            assertThat(greeting).isEqualTo("Hello Grace, from Mockito! (translated)");
        }
    }

    @Test
    @DisplayName("test constructor")
    void greetWithMockedConstructor() {
        // Mock for repo (needed for HelloMockito constructor)
        PersonRepository mockRepo = mock(PersonRepository.class);
        when(mockRepo.findById(anyInt()))
                .thenReturn(Optional.of(new Person(1, "Grace", "Hopper", LocalDate.now())));

        // Mock for translator (instantiated inside HelloMockito constructor)
        try (MockedConstruction<DefaultTranslationService> ignored =
                     mockConstruction(DefaultTranslationService.class,
                             (mock, context) -> when(mock.translate(anyString(), anyString(), anyString()))
                                     .thenAnswer(invocation -> invocation.getArgument(0) + " (translated)"))) {

            // Instantiate HelloMockito with mocked repo and locally instantiated translator
            HelloMockito hello = new HelloMockito(mockRepo);
            String greeting = hello.greet(1, "en", "en");
            assertThat(greeting).isEqualTo("Hello Grace, from Mockito! (translated)");

            // Any instantiation of DefaultTranslationService will return the mocked instance
            DefaultTranslationService translator = new DefaultTranslationService();
            String translate = translator.translate("What up?", "en", "en");
            assertThat(translate).isEqualTo("What up? (translated)");
        }
    }

}