package com.yifan.foundation.wikipedia;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.MockedStatic;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;

/**
 * package_name: com.yifan.foundation.wikipedia
 * author: wyifa
 * date: 2024/8/23
 * description:
 *      The MockedStatic instance is created in a try-with-resources block.
 * The expectations on the static method use a lambda expression and a thenAnswer call because the mocked reply is based on the input argument.
 * You can call the getBios method in the class under test and check the number of results.
 * Optionally, you can verify that the static method was called the right number of times. This version of verify takes a MockedStatic.Verification argument,
 * whose single abstract method, apply, takes no arguments and returns void. This call to verify therefore uses a compatible expression lambda, followed by the required multiplicity.
 */
class BioServiceTest {

    @Test
    @DisplayName("Test normal bioservice ")
    void checkBios() {
        BioService service = new BioService("Anita Borg", "Ada Lovelace",
                "Grace Hopper", "Barbara Liskov");

        List<String> bios = service.getBios();
        assertThat(bios).hasSize(4);
        assertEquals(4, bios.size());

        bios.forEach(bio -> {
//            System.out.println(bio);
            String[] strings = bio.split("=");
            String[] bioStrings = strings[1].split("\\n");
            System.out.println("Title: " + strings[0].substring(1));
            Arrays.stream(bioStrings)
                    .forEach(System.out::println);
            System.out.println("-------------------");
        });
    }

    @Test
    @DisplayName("Test the static method ")
    void testBioServiceWithMocks() {
        BioService service = new BioService(
                "Anita Borg", "Ada Lovelace", "Grace Hopper", "Barbara Liskov");
        try (MockedStatic<WikiUtil> mocked = mockStatic(WikiUtil.class)) {
            mocked.when(() -> WikiUtil.getWikipediaExtract(anyString()))
                    .thenAnswer(AdditionalAnswers.returnsFirstArg());
            assertThat(service.getBios()).hasSize(4);
            mocked.verify(() -> WikiUtil.getWikipediaExtract(anyString()), times(4));
        }
    }
}