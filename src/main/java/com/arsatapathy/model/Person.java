package com.arsatapathy.model;

import com.arsatapathy.parser.json.annocation.JsonElement;
import com.arsatapathy.parser.json.annocation.JsonInitializer;
import com.arsatapathy.parser.json.annocation.JsonParser;

@JsonParser
public class Person {
    @JsonElement
    private String firstName;

    @JsonElement(key = "lastName")
    private String secondName;

    @JsonElement
    private String age;

    public Person(String firstName, String secondName, String age) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @JsonInitializer
    private void init() {
       this.firstName = this.firstName.substring(0, 1).toUpperCase() + this.firstName.substring(1);
       this.secondName = this.secondName.substring(0, 1).toUpperCase() + this.secondName.substring(1);
    }
}
