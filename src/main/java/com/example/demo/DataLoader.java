package com.example.demo;

import com.example.demo.persistence.PeopleRepository;
import com.example.demo.persistence.entities.PersonEntity;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Component
public class DataLoader {

    private final PeopleRepository peopleRepository;

    public DataLoader(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @PostConstruct
    public void addData() {
        log.info("Adding data");

        List<PersonEntity> entities = Lists.newArrayList(
                PersonEntity.builder().name("Carl").address("Carl's Street").build()
                , PersonEntity.builder().name("Peter").address("Peter's Street").build()
                , PersonEntity.builder().name("John").address("John's Street").build()
                , PersonEntity.builder().name("Tom").address("Tom's Street").build()
                , PersonEntity.builder().name("Marc").address("Marc's Street").build()
        );

        this.peopleRepository.saveAll(entities);

        log.info("Total of {} records added", this.peopleRepository.count());
    }
}
