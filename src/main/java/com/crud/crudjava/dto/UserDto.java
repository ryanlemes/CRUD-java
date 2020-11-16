package com.crud.crudjava.dto;

import ccom.crud.crudjava.Utils.Rsa;
import com.crud.crudjava.models.SingleDigit;
import ccom.crud.crudjava.models.User;
import com.crud.crudjava.repository.KeyPairRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class responsible to return the data for some user.
 */
public class UserDto {

    private Long id;
    private String name;
    private String email;
    private List<SingleDigit> singleDigits;

    public UserDto(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.singleDigits = user.getSingleDigits();
    }

    public UserDto(KeyPairRepository keyPairRepository, User user){
        try{
            Rsa rsa = new Rsa();
            rsa = rsa.getKeyPair(keyPairRepository, user.getId());

            this.id = user.getId();
            this.name = rsa.decrypt(user.getName());
            this.email = rsa.decrypt(user.getEmail());
            this.singleDigits = user.getSingleDigits();
        }
        catch (Exception ex)
        {
        }
    }

    public static List<UserDto> convert(List<User> users, KeyPairRepository keyPairRepository) {
        return users.stream().map(user -> new UserDto(keyPairRepository, user)).collect(Collectors.toList());
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
