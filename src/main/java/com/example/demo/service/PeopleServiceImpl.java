package com.example.demo.service;

import com.example.demo.model.Person;
import com.example.demo.persistence.PeopleRepository;
import com.example.demo.persistence.entities.PersonEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PeopleServiceImpl implements PeopleService {

    private final PeopleRepository peopleRepository;

    public PeopleServiceImpl(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public Person getPerson(Long id) {
        PersonEntity entity = this.peopleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Person not found!"));

        return this.buildPersonFromEntity(entity);
    }

    @Override
    public List<Person> getEveryone() {
        return this.peopleRepository.findAll().stream()
                .map(this::buildPersonFromEntity)
                .collect(Collectors.toList());
    }

    private Person buildPersonFromEntity(PersonEntity entity) {
        return new Person(entity.getId(), entity.getName(), entity.getAddress());
    }
}
