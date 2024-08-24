package com.yifan.foundation;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;


/**
 * package_name: com.yifan.foundation
 * author: wyifa
 * date: 2024/8/24
 * description:
 */
@ExtendWith(MockitoExtension.class)
class MockSpyTest {

    @Mock
    ArrayList<Integer> mockArrayList;

    @Spy
    ArrayList<Integer> spyArrayList;

    @BeforeAll
    static void setup() {
        System.out.println("using annotation before all");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("using annotation before each");
    }

    @Test
    @DisplayName("Add data to mock object will not work")
    void testMockArrayList() {
        mockArrayList.add(1);
        verify(mockArrayList).add(1);

        assertNotEquals(1, mockArrayList.size());
    }

    @Test
    @DisplayName("add data to spy object")
    void testSpyArrayList() {
        spyArrayList.add(1);
        verify(spyArrayList).add(1);

        assertEquals(1, spyArrayList.size());
    }
}
