package com.yifan.foundation.matcher;

import org.mockito.ArgumentMatcher;

import java.util.List;

/**
 * package_name: com.yifan.foundation.matcher
 * author: wyifa
 * date: 2024/8/23
 * description:
 */
public class ListOfTwoElements implements ArgumentMatcher<List> {
    @Override
    public boolean matches(List list) {
        return list.size() == 2;
    }

    public String toString(){
        return "[List of 2 elements]";
            }


}
