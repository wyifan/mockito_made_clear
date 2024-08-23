package com.yifan.foundation.matcher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

/**
 * package_name: com.yifan.foundation.matcher
 * author: wyifa
 * date: 2024/8/23
 * description:
 */
class CustomerMatchTest {

    @Test
    @DisplayName("Test custom Matcher")
    void testCustomerMatcher() {
        List mock = mock(List.class);

        // set expectation using argThat and customMatcher
        when(mock.addAll(argThat(new ListOfTwoElements()))).thenReturn(true);

        // actual test
        mock.addAll(Arrays.asList("one", "two"));

        // verify the test called with the cumstom matcher
        verify(mock).addAll(argThat(new ListOfTwoElements()));

        // variation
        verify(mock).addAll(argThat(list -> list.size() == 2));

    }
}
