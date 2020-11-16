package ccom.crud.crudjava.repository;

import com.crud.crudjava.models.SingleDigit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SingleDigitRepository  extends JpaRepository<SingleDigit, Long> {
    /**
     * Select to find some singleDigit on the String N and the integer K.
     * This is the select to get the Cache SingleDigit
     * @param n A string representing the number
     * @param k a integer representing the number of times of n concatenations
     * @return a Single digit if exist.
     */
    Optional<SingleDigit> findByNAndK(String n, int k);
}
