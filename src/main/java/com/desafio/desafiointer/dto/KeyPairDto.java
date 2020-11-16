package com.desafio.desafiointer.dto;

import com.desafio.desafiointer.models.KeyPair;

/**
 * Class responsible to return the data of the Key Pair
 */
public class KeyPairDto {

    private String PublicKey;

    public KeyPairDto (KeyPair keyPair) {
        this.PublicKey = keyPair.getPublicKey();
    }

    public String getPublicKey() {
        return PublicKey;
    }
}
