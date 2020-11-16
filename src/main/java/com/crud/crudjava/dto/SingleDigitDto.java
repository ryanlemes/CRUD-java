package com.crud.crudjava.dto;

import com.crud.crudjava.models.SingleDigit;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class responsible to return the data of the Single Digit
 */
public class SingleDigitDto {

    private long id;

    private String n;
    private int k;

    private long result;

    public SingleDigitDto(SingleDigit singleDigit) {
        this.id = singleDigit.getId();
        this.n = singleDigit.getN();
        this.k = singleDigit.getK();
        this.result = singleDigit.getResult();
    }

    public long getId() {
        return id;
    }

    public String getN() {
        return n;
    }

    public int getK() {
        return k;
    }

    public long getResult() {
        return result;
    }

    public static List<SingleDigitDto> convert(List<SingleDigit> singleDigits) {
        return singleDigits.stream().map((SingleDigitDto::new)).collect(Collectors.toList());
    }
}
