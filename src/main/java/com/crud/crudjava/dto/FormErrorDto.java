package com.crud.crudjava.dto;

/**
 * Class responsible to return some error on the form
 */
public class FormErrorDto {
    private String field;
    private String error;

    public FormErrorDto(String field, String error) {
        this.field = field;
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public String getField() {
        return field;
    }
}
