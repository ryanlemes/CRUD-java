package com.desafio.desafiointer.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SingleDigit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String n;
    private int k;

    private long result;

    public SingleDigit(){};

    public SingleDigit(String n, int k) {
        this.setK(k);
        this.setN(n);
    }

    public SingleDigit(SingleDigit singleDigit) {
        this.setK(singleDigit.getK());
        this.setN(singleDigit.getN());
        this.setResult(singleDigit.getResult());
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

    public void setN(String n){
        if(!n.contains("-") && n != "0" && n.trim() != "") {
            this.n = n;
        }
    }

    public void setK(int k) {
        if(k >= 1 && k <= 100000) {
            this.k = k;
        }
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setResult(long result) {
        this.result = result;
    }
}
