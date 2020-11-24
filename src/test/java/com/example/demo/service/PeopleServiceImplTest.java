package com.example.demo.service;

import com.example.demo.model.Person;
import com.example.demo.persistence.PeopleRepository;
import com.example.demo.persistence.entities.PersonEntity;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PeopleServiceImplTest {

    @InjectMocks
    private PeopleServiceImpl classUnderTest;

    @Mock
    private PeopleRepository peopleRepository;

    @Test
    @DisplayName("Able to get a Person")
    void getPerson() {
        PersonEntity entity = PersonEntity.builder()
                .id(1L)
                .name("John Doe")
                .address("Street Ville")
                .build();

        when(peopleRepository.findById(1L)).thenReturn(Optional.of(entity));

        Person result = classUnderTest.getPerson(1L);

        Assertions.assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(new Person(1L, "John Doe", "Street Ville"))
        ;
    }

    @Test
    @DisplayName("Unable to get a Person - Not found")
    void get_person_fails() {
        when(peopleRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> classUnderTest.getPerson(1L));
    }

    @Test
    @DisplayName("Able to get everyone")
    void getEveryone() {
        List<PersonEntity> entities = Lists.newArrayList(
                PersonEntity.builder().id(1L).name("Carl").address("Carl's Street").build()
                , PersonEntity.builder().id(2L).name("Peter").address("Peter's Street").build()
                , PersonEntity.builder().id(3L).name("John").address("John's Street").build()
                , PersonEntity.builder().id(4L).name("Tom").address("Tom's Street").build()
                , PersonEntity.builder().id(5L).name("Marc").address("Marc's Street").build()
        );

        List<Person> expected = Lists.newArrayList(
                new Person(1L, "Carl", "Carl's Street"),
                new Person(2L, "Peter", "Peter's Street"),
                new Person(3L, "John", "John's Street"),
                new Person(4L, "Tom", "Tom's Street"),
                new Person(5L, "Marc", "Marc's Street")
        );

        when(peopleRepository.findAll()).thenReturn(entities);

        List<Person> result = classUnderTest.getEveryone();

        Assertions.assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expected)
        ;
    }
}
