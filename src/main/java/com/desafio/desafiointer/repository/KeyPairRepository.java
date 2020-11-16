package com.desafio.desafiointer.repository;

import com.desafio.desafiointer.models.KeyPair;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KeyPairRepository extends JpaRepository<KeyPair, Long> {
    /**
     * Select to find some KeyPair based on user ID.
     * @param userId Long user id
     * @return if exists, a key pair with the public and private key encrypted.
     */
    Optional<KeyPair> findByUserId(long userId);
}
