package com.desafio.desafiointer;

import com.desafio.desafiointer.models.SingleDigit;
import com.desafio.desafiointer.repository.SingleDigitRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SingleDigitRepositoryTest {

    @Autowired
    private SingleDigitRepository repository;

    @Test
    public void notFindIfExistSomeObjectWithASpecificNAndK() {
        String testN = "92";
        int testK = 2;

        Optional<SingleDigit> singleDigit = repository.findByNAndK(testN, testK);

        Assert.isTrue(singleDigit.isEmpty(),"Exist some digit inserted in database");
    }

    @Test
    public void FindIfExistSomeObjectWithASpecificNAndK() {
        String testN = "92";
        int testK = 3;

        Optional<SingleDigit> singleDigit = repository.findByNAndK(testN, testK);

        Assert.isTrue(singleDigit.isPresent(),"Doesnt exist this digit");
        Assert.isTrue(singleDigit.get().getResult() == 6,"Invalid result saved");
    }
}
