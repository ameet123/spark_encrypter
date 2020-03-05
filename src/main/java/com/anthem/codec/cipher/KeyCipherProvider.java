package com.anthem.codec.cipher;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * multiple ways of getting desired ciphers and keys
 */
public interface KeyCipherProvider {
    String getCipherAlgorithm();

    Cipher getCipher();

    void encryptInit();

    default void decryptInit() {
        throw new RuntimeException("Not implemented");
    }

    SecretKeySpec secretKey();
}
