package com.anthem.codec.cipher;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;

public abstract class BaseKeyCipher implements KeyCipherProvider {
    Cipher cipherInstance;
    private String password;

    BaseKeyCipher(String password) {
        this.password = password;
        try {
            cipherInstance = Cipher.getInstance(getCipherAlgorithm());
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException("Err no such algorithm");
        }
    }

    @Override
    public Cipher getCipher() {
        return cipherInstance;
    }

}
