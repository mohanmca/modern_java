package com.modern.model;

public record User(String firstName, String lastName) {

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}