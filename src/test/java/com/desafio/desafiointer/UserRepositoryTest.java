package com.desafio.desafiointer;

import com.desafio.desafiointer.models.User;
import com.desafio.desafiointer.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Test
    public void testEmptyFindByIdAndDeletedAtIsNull() {
        long Userid = 2;

        Optional<User> user = repository.findByIdAndDeletedAtIsNull(Userid);

        Assert.isTrue(user.isEmpty(),"Exist some user inserted in database");
    }

    @Test
    public void testNotEmptyFindByIdAndDeletedAtIsNull() {
        long Userid = 1;

        Optional<User> user = repository.findByIdAndDeletedAtIsNull(Userid);

        Assert.isTrue(user.isPresent(),"Doesnt exist some user inserted in database.");
        Assert.isInstanceOf(User.class, user.get(),"Invalid class type.");
        Assert.isTrue(user.get().getName().equals("JKx0gKzAb515Oh40pJcxK91o+e4TKuKpI7nDEDFOal6/QO7ik5XS0977IDOpA3rQ47l" +
                "ggtMB1xoTHqBL8On76dT8O2Pc4Ram+dohF7x/cL6iG4fQAOZK2qWYS8sbd8aX0Fl3Xmbx8ZAz0oTO7QKjDt4bGwU/" +
                "rEedop+RSrK05NBIWIZteuvzPhgre68tIm2/Kb8wx5v0o7QWlyK8CUXCEoS2Ah87tKn4gWHhjQi4y/t/x7bGJPuFi8ZVZ+FAOjyq3" +
                "CMhMc1RdkpOrt9vSGOYejCXNBAgqhGkau99dhiEHoVtMeu8kSxVmk+7vOsqg6aZYz7rtAJ4ELkD7BfhtmstQw=="), "Data expected invalid.");
    }

}
