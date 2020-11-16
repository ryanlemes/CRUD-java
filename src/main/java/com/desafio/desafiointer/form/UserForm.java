package com.desafio.desafiointer.form;

import com.desafio.desafiointer.Utils.Rsa;
import com.desafio.desafiointer.models.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Class to map the request values for User.
 */
public class UserForm {

    @NotNull(message = "Name cannot be null") @NotEmpty(message = "Name cannot be empty")
    private String name;

    @NotNull(message = "Email cannot be null") @NotEmpty(message = "Email cannot be empty") @Email(message = "Email invalid!")
    private String email;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Convert Userform object to User applying encryption.
     * @param rsa with the pair of keys.
     * @return a new user object with the encryption applied.
     */
    public User converter(Rsa rsa) {
        try {
            // Uses the method rsa.encrypt to encrypt the data than return the created user.
            User user = new User(rsa.encrypt(this.getName()), rsa.encrypt(this.getEmail()));

            return user;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
