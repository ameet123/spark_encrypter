package com.ameet.codec.cipher;

import com.ameet.codec.config.EncrConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * symmetric key encryption example provider class
 */
public class AESNoPaddingProvider implements EncryptDecrypt {
    private static final Logger LOGGER = LoggerFactory.getLogger(AESNoPaddingProvider.class);
    private IvParameterSpec ivspec;
    private SecretKeySpec secretKeySpec;
    private Cipher cipherInstance;
    private OutputStream outputStream;
    private InputStream inputStream;

    public AESNoPaddingProvider() {
        byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        ivspec = new IvParameterSpec(iv);
        secretKeySpec = new SecretKeySpec(EncrConstants.password.getBytes(), "AES");

        try {
            cipherInstance = Cipher.getInstance(getCipherAlgorithm());
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException("Err no such algorithm");
        }
    }

    private String getCipherAlgorithm() {
        return "AES/CFB8/NoPadding";
    }

    @Override
    public InputStream decipherStream(InputStream in) {
        try {
            cipherInstance.init(Cipher.DECRYPT_MODE, secretKeySpec, ivspec);
        } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
            throw new RuntimeException("invalid key", e);
        }
        inputStream = new CipherInputStream(in, cipherInstance);
        return inputStream;
    }

    @Override
    public OutputStream cipherStream(OutputStream out) {
        try {
            cipherInstance.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivspec);
        } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
            throw new RuntimeException("invalid key", e);
        }
        outputStream = new CipherOutputStream(out, cipherInstance);
        return outputStream;
    }

    @Override
    public void terminate() {
        try {
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            LOGGER.error("ERR:Closing output stream", e);
        }
    }

    @Override
    public void flush() {
        try {
            outputStream.flush();
        } catch (IOException e) {
            LOGGER.error("ERR:Flushing output stream", e);
        }
    }

    @Override
    public String extension() {
        return EncrConstants.extension;
    }
}
