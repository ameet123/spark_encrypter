package com.anthem.codec.cipher;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;

public class AESNoPaddingProvider extends BaseKeyCipher {
    private IvParameterSpec ivspec;
    private SecretKeySpec secretKeySpec;

    public AESNoPaddingProvider(String password) {
        super(password);
        byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        ivspec = new IvParameterSpec(iv);
        secretKeySpec = new SecretKeySpec(password.getBytes(), "AES");
    }


    @Override
    public String getCipherAlgorithm() {
        return "AES/CFB8/NoPadding";
    }

    @Override
    public void encryptInit() {
        try {
            cipherInstance.init(Cipher.ENCRYPT_MODE, secretKey(), ivspec);
        } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
            throw new RuntimeException("invalid key", e);
        }
    }

    @Override
    public void decryptInit() {
        try {
            cipherInstance.init(Cipher.DECRYPT_MODE, secretKey(), ivspec);
        } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
            throw new RuntimeException("invalid key", e);
        }
    }

    @Override
    public SecretKeySpec secretKey() {
        return secretKeySpec;
    }
}
