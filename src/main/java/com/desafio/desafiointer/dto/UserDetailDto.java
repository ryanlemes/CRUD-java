package com.desafio.desafiointer.dto;

import com.desafio.desafiointer.Utils.Rsa;
import com.desafio.desafiointer.models.SingleDigit;
import com.desafio.desafiointer.models.User;
import com.desafio.desafiointer.repository.KeyPairRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class responsible to return the data for a single User
 */
public class UserDetailDto {
    private Long id;
    private String name;
    private String email;
    private List<SingleDigit> singleDigits;

    public UserDetailDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.singleDigits = new ArrayList<>();
        this.singleDigits.addAll(user.getSingleDigits().stream().map((SingleDigit::new)).collect(Collectors.toList()));
    }

    public UserDetailDto(KeyPairRepository keyPairRepository, User user) {
        try{
            Rsa rsa = new Rsa();
            rsa = rsa.getKeyPair(keyPairRepository, user.getId());

            this.id = user.getId();
            this.name = rsa.decrypt(user.getName());
            this.email = rsa.decrypt(user.getEmail());
            this.singleDigits = new ArrayList<>();
            this.singleDigits.addAll(user.getSingleDigits().stream().map((SingleDigit::new)).collect(Collectors.toList()));
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<SingleDigit> getSingleDigits() {
        return singleDigits;
    }
}
