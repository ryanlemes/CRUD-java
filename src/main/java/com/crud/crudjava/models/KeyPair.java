package com.crud.crudjava.models;

import javax.persistence.*;

@Entity
public class KeyPair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long userId;
    @Lob
    @Column
    private String publicKey;

    @Lob
    @Column
    private String privateKey;

    public KeyPair(){}

    public KeyPair(KeyPair keyPair) {
        this.setUserId(keyPair.getUserId());
        this.setPrivateKey(keyPair.getPrivateKey());
        this.setPublicKey(keyPair.getPublicKey());
    }

    public KeyPair(long userId, String publicKey, String privateKey) {
        this.setUserId(userId);
        this.setPrivateKey(privateKey);
        this.setPublicKey(publicKey);
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
}
