package com.yifan.foundation.astro;

import com.yifan.foundation.base.Assignment;
import com.yifan.foundation.base.AstroResponse;
import com.yifan.foundation.base.Gateway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * package_name: com.yifan.foundation.astro
 * author: wyifa
 * date: 2024/8/22
 * description:
 */
@ExtendWith(MockitoExtension.class)
class AstroServiceTest {

    private final AstroResponse mockAstroResponse =
            new AstroResponse(7, "Success", Arrays.asList(
                    new Assignment("John Sheridan", "Babylon 5"),
                    new Assignment("Susan Ivanova", "Babylon 5"),
                    new Assignment("Beckett Mariner", "USS Cerritos"),
                    new Assignment("Brad Boimler", "USS Cerritos"),
                    new Assignment("Sam Rutherford", "USS Cerritos"),
                    new Assignment("D'Vana Tendi", "USS Cerritos"),
                    new Assignment("Ellen Ripley", "Nostromo")
            ));

    @Mock
    private Gateway<AstroResponse> gateway;

    @InjectMocks
    private AstroService service;

    @Test
    @DisplayName("Normal integration test")
    void testAstroData_usingRealGateway_withHttpClient() {
        // create a real gateway
        service = new AstroService(new AstroGateway());

        // Call the method under test
        // first time it failed, but when using debugging, it passed! so wired!
        Map<String, Long> astroData = service.getAstroData();

        // Print the results and check that they are reasonable
        astroData.forEach((craft, number) -> {
            System.out.println(number + " astronauts aboard " + craft);
            assertAll(
                    () -> assertThat(number).isPositive(),
                    () -> assertThat(craft).isNotBlank()
            );
        });

    }

    @Test
    @DisplayName("Using fake gateway service")
    void testAstroData_usingOwnMockGateway() {
        service = new AstroService(new FakeGateway());

        // call the results under test
        Map<String, Long> astroData = service.getAstroData();

        // check the results from the method under test
        astroData.forEach((craft, number) -> {
            System.out.println(number + " astronauts aboard " + craft);
            assertAll(
                    () -> assertThat(number).isPositive(),
                    () -> assertThat(craft).isNotBlank()
            );
        });
    }

    @Test
    @DisplayName("Test using mock")
    void testAstroData_usingInjectedMockGateway() {
        // mock Gateway created and injected into AstroService using
        // @Mock and @InjectMock annotations
        //
        // set the expectation on the mock
        when(gateway.getResponse()).thenReturn(mockAstroResponse);

        // Call the method under test
        Map<String, Long> astroData = service.getAstroData();

        // check the results from the method under test
        assertThat(astroData)
                .containsEntry("Babylon 5", 2L)
                .containsEntry("Nostromo", 1L)
                .containsEntry("USS Cerritos", 4L);

        astroData.forEach((craft, number) -> {
            System.out.println(number + " astronauts aboard " + craft);

            assertAll(
                    () -> assertThat((number)).isPositive(),
                    () -> assertThat(craft).isNotBlank()
            );
        });

        // Verify the stubbed method was called
        verify(gateway).getResponse();
    }

    @Test
    @DisplayName("Test a failure")
    void testAstroData_usingFailureGateway() {
        when(gateway.getResponse()).thenThrow(
                new RuntimeException(new IOException("Network problems"))
        );

        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> service.getAstroData())
                .withCauseInstanceOf(IOException.class)
                .withMessageContaining("Network problems");

        verify(gateway).getResponse();
    }
}