package com.yifan.foundation.base;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

/**
 * package_name: com.yifan.foundation.base
 * author: wyifa
 * date: 2024/8/22
 * description:
 */
@Data
@AllArgsConstructor
public class Person {
    int id;
    String first;
    String last;
    LocalDate birth;
}
