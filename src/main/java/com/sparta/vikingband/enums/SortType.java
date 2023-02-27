package com.sparta.vikingband.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum SortType {
    WISH("wish"),
    CREATED_AT("created_at");

    @JsonValue
    private String option;

    SortType(String option) {
        this.option = option;
    }
}