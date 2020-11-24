package com.example.demo.service;

import com.example.demo.model.Person;

import java.util.List;

public interface PeopleService {
    Person getPerson(Long id);

    List<Person> getEveryone();
}
