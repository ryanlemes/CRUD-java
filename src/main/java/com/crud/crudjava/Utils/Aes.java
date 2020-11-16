package com.crud.crudjava.Utils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Arrays;
import java.util.Base64;

/**
 * Class Created to encrypt the RSA keys to save it on database
 */
public class Aes {

    // Set a hard coded secret key
    private static final String secretKey = "C5B3CF3D3742667B5B312F46C51FE";
    private static SecretKeySpec secretKeySpec;
    private static byte[] key;

    /**
     * Generate a key based on Sha-1 with length of 32 and the AES algorithm.
     */
    public static void generateKey() {

        MessageDigest sha = null;
        try {
            key = secretKey.getBytes(StandardCharsets.UTF_8);
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 32);
            secretKeySpec = new SecretKeySpec(key, "AES");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * Encrypt some String using Aes method.
     * @param strToEncrypt the string to be encrypted.
     * @return a string encrypted.
     */
    public static String encrypt(String strToEncrypt) {
        try
        {
            // First generate the key based on secretKey
            generateKey();

            //Set on encryption mode than encode to base64 and return it.
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

            return Base64
                    .getEncoder()
                    .encodeToString(cipher
                            .doFinal(strToEncrypt
                                    .getBytes("UTF-8")));
        }
        catch (Exception e)
        {
            return null;
        }
    }

    /**
     * Decrypt some String encrypted by Aes.
     * @param strToDecrypt the string to be decrypted.
     * @return a string with the original value.
     */
    public static String decrypt(String strToDecrypt) {
        try
        {
            // First generate the key based on secretKey
            generateKey();

            //Set on decryption mode than decode from base64 and return it as a string.
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

            return new String(cipher
                        .doFinal(Base64.getDecoder().decode(strToDecrypt)));
        }
        catch (Exception e)
        {
            return null;
        }
    }
}

