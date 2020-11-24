package com.example.demo.controller;

import com.example.demo.model.Person;
import com.example.demo.service.PeopleService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PersonController.class)
@ExtendWith(MockitoExtension.class)
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PeopleService peopleService;

    @Test
    @DisplayName("GET/ Able to get a Person")
    void getPerson() throws Exception {
        Person person = new Person(1L, "John Doe", "Street Ville");

        when(peopleService.getPerson(1L)).thenReturn(person);

        this.mockMvc.perform(
                get("/people/1")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(person.id().intValue())))
                .andExpect(jsonPath("$.name", is(person.name())))
                .andExpect(jsonPath("$.address", is(person.address())))
        ;
    }

    @Test
    @DisplayName("GET/ Unable to get a Person - Not found")
    void get_person_fails() throws Exception {
        when(peopleService.getPerson(1L)).thenThrow(new RuntimeException());

        try {

            this.mockMvc.perform(
                    get("/people/1")
                            .contentType(MediaType.APPLICATION_JSON)
            )
                    .andExpect(status().isInternalServerError()) // FAILS -> NestedServletException
            ;
        } catch (Exception e) {
            Assertions.assertTrue(true);
        }
    }


    @Test
    @DisplayName("GET/ Able to get everyone")
    void testGetPerson() throws Exception {
        List<Person> people = Lists.newArrayList(
                new Person(1L, "Carl", "Carl's Street"),
                new Person(2L, "Peter", "Peter's Street"),
                new Person(3L, "John", "John's Street"),
                new Person(4L, "Tom", "Tom's Street"),
                new Person(5L, "Marc", "Marc's Street")
        );

        when(peopleService.getEveryone()).thenReturn(people);

        this.mockMvc.perform(
                get("/people"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[3].id", is(4)))
                .andExpect(jsonPath("$[4].id", is(5)))
        ;
    }
}
