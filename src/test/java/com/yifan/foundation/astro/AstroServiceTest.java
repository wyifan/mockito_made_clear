package com.yifan.foundation.astro;

import com.yifan.foundation.base.AstroResponse;
import com.yifan.foundation.base.Gateway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

/**
 * package_name: com.yifan.foundation.astro
 * author: wyifa
 * date: 2024/8/22
 * description:
 */
class AstroServiceTest {

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
}