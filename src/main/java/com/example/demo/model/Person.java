package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Person(
        @JsonProperty Long id,
        @JsonProperty String name,
        @JsonProperty String address) {
}
