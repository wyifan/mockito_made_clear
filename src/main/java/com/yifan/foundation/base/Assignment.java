package com.yifan.foundation.base;

/**
 * package_name: com.yifan.foundation.base
 * author: wyifa
 * date: 2024/8/22
 * description:
 */
public record Assignment(String name, String craft) {
    // figure out what is a record class
    // Conciseness:
    //Record: Automatically generates boilerplate code for constructors, getters, equals(), hashCode(), and toString().
    //Normal Class: Requires manual implementation of these methods.
    //Immutability:
    //Record: Fields are implicitly final and cannot be changed after the object is created.
    //Normal Class: Fields can be mutable or immutable based on the developer’s choice.
    //Inheritance:
    //Record: Cannot extend other classes because it implicitly extends java.lang.Record.
    //Normal Class: Can extend other classes and implement interfaces.
    //Purpose:
    //Record: Primarily used for data carriers with minimal behavior.
    //Normal Class: Used for a wide range of purposes, including complex behavior and state management.
    //Declaration:
    //Record: Declared with the record keyword.
    //Normal Class: Declared with the class keyword.
}
