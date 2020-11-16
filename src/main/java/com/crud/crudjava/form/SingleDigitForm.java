package com.crud.crudjava.form;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * Class to map the request values for Single Digit.
 */
public class SingleDigitForm {

    @ApiModelProperty(value = "String N - Represents a integer.")
    @NotNull (message = "Field N cannot be null")
    @NotEmpty (message = "Field N cannot be empty")
    private String n;

    @ApiModelProperty(value = "Integer K - Represents a number of times of N concatenation.")
    @NotNull (message = "Field K cannot be null")
    @Positive (message = "Field K need to be positive")
    private int k;

    @ApiModelProperty(value = "User id")
    @Positive (message = "Field userId need to be positive")
    private String user;

    public String getN() {
        return n;
    }

    public int getK() {
        return k;
    }

    public String getUser() {
        return user;
    }
}
