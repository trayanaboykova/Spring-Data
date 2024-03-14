package org.xmlprocessing_lab.models;

public class PersonDto {
    private String firstName;
    private String lastName;
    private int age;

    public PersonDto(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }
}
