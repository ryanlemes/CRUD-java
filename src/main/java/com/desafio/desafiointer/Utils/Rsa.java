package com.desafio.desafiointer.Utils;

import com.desafio.desafiointer.repository.KeyPairRepository;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Optional;

/**
 * Class responsible to encrypt the user data.
 */
public class Rsa {

    private PrivateKey privateKey;
    private PublicKey publicKey;

    public Rsa(PublicKey publicKey, PrivateKey privateKey){
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public Rsa() throws NoSuchAlgorithmException {
        generateKeyPair();
    }

    /**
     * Generate the RSA key pair on size 2048.
     * @throws NoSuchAlgorithmException
     */
    public void generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");

        keyGenerator.initialize(2048);

        KeyPair pair = keyGenerator.generateKeyPair();

        this.privateKey = pair.getPrivate();
        this.publicKey = pair.getPublic();
    }

    /**
     * Encrypt some data on RSA method. Using a byte[] param.
     * Internal use.
     * @param data byte array with the data to be encrypted.
     * @return a byte array with the data encrypted.
     * @throws Exception
     */
    private byte[] encrypt(byte[] data) throws Exception {

        Cipher cipher = Cipher.getInstance("RSA");

        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }

    /**
     * Encrypt some data on RSA method. Using a String param.
     * @param data String with the data to be encrypted.
     * @return a String with the data encrypted.
     * @throws Exception
     */
    public String encrypt(String data) throws Exception {

        byte[] encryptedText = encrypt(data.getBytes("UTF-8"));

        return encodeBASE64(encryptedText);
    }

    /**
     * Decrypt some data encrypted by RSA.
     * Internal Use.
     * @param data a byte array with the data to be decrypted.
     * @return a byte array with the decrypted data.
     * @throws Exception
     */
    private byte[] decrypt(byte[] data) throws Exception {

        Cipher cipher = Cipher.getInstance("RSA");

        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        return cipher.doFinal(data);
    }

    /**
     * Decrypt some data encrypted by RSA.
     * @param data a string with the data to be decrypted.
     * @return a String with the value of the data decrypted.
     * @throws Exception
     */
    public String decrypt(String data) throws Exception {

        byte[] decryptedText = decrypt(decodeBASE64(data));

        return new String(decryptedText, "UTF-8");
    }

    /**
     * Save the Rsa key pair on database.
     * @param keyPairRepository repository to save the data
     * @param userId long User id
     */
    public void saveKeyPair(KeyPairRepository keyPairRepository, long userId){

        //Create a new Key pair object, both encrypted with Aes encryption.
        com.desafio.desafiointer.models.KeyPair keyPair =
                new com.desafio.desafiointer.models.KeyPair(userId,
                        Aes.encrypt(encodeBASE64(publicKey.getEncoded())),
                        Aes.encrypt(encodeBASE64(privateKey.getEncoded())));

        keyPairRepository.save(keyPair);
    }

    /**
     * Get the Key Pair from database, using the userId.
     * @param keyPairRepository repository to get the data.
     * @param userId Long User id.
     * @return a Rsa object with the the key Pair.
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     */
    public Rsa getKeyPair(KeyPairRepository keyPairRepository, long userId) throws InvalidKeySpecException, NoSuchAlgorithmException {

        Optional<com.desafio.desafiointer.models.KeyPair> keyPair = keyPairRepository.findByUserId(userId);
        if(keyPair.isPresent()) {
            byte[] privateKeyBytes = decodeBASE64(Aes.decrypt(keyPair.get().getPrivateKey()));
            byte[] publicKeyBytes = decodeBASE64(Aes.decrypt(keyPair.get().getPublicKey()));

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

            EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

            return new Rsa(publicKey, privateKey);
        }
        return null;
    }

    /**
     * Encode some byte array to string.
     * @param bytes byte array with the data.
     * @return a String.
     */
    private static String encodeBASE64(byte[] bytes)
    {
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * Decode some string text to byte array.
     * @param text String with the text to be decoded.
     * @return a array.
     */
    private static byte[] decodeBASE64(String text)
    {
        return Base64.getDecoder().decode(text);
    }
}

