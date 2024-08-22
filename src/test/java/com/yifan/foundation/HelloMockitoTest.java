package com.yifan.foundation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * package_name: com.yifan.foundation
 * author: wyifa
 * date: 2024/8/22
 * description:
 */
class HelloMockitoTest {
    private HelloMockito helloMockito = new HelloMockito();

    @Test
    void greetPerson() {
        String greeting = helloMockito.greet("World");
        assertEquals("Hello World, from Mockito!", greeting);
    }
}