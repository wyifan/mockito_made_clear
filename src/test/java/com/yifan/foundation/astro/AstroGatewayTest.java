package com.yifan.foundation.astro;

import com.yifan.foundation.base.AstroResponse;
import com.yifan.foundation.base.Gateway;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * package_name: com.yifan.foundation.astro
 * author: wyifa
 * date: 2024/8/24
 * description:
 */
class AstroGatewayTest {
    private final Gateway<AstroResponse> gateway = new AstroGatewayHttpClient();

    @Test
    void testDeserializeToRecords() {
        AstroResponse result = gateway.getResponse();
        result.people().forEach(System.out::println);
        assertAll(
                () -> assertTrue(result.number() >= 0),
                () -> assertEquals(result.people().size(), result.number())
        );
    }
}