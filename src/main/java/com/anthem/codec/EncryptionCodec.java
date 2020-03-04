package com.anthem.codec;

import org.apache.hadoop.io.compress.CompressionOutputStream;
import org.apache.hadoop.io.compress.GzipCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
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
import java.util.Properties;

public class EncryptionCodec extends GzipCodec {
    private static final Logger LOGGER = LoggerFactory.getLogger(EncryptionCodec.class);
    private final String PROP_FILE = "application.properties";
    private final String PASSWORD_KEY = "password";
    private final String EXT_KEY = "extension";
    private String extension;
    private Properties properties;
    private SecretKeySpec secretKey;
    private String password;
    private Cipher aes;
    private IvParameterSpec ivspec;

    public EncryptionCodec() {
        aes = null;
        try {
            aes = Cipher.getInstance("AES/CFB8/NoPadding");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException("Err no such algorithm");
        }
        // read props
        readProperties();
        generateKey();
    }

    public SecretKeySpec getSecretKey() {
        return secretKey;
    }

    @Override
    public String getDefaultExtension() {
        return super.getDefaultExtension();
    }

    private void readProperties() {
        properties = new Properties();
        InputStream propertyStream = getClass().getClassLoader().getResourceAsStream(PROP_FILE);
        try {
            properties.load(propertyStream);
        } catch (IOException e) {
            LOGGER.error("ERR: Could not load property file", e);
            throw new RuntimeException(e);
        }
        password = properties.getProperty(PASSWORD_KEY);
        extension = properties.getProperty(EXT_KEY);
        LOGGER.info(">>Encrypted file extension:{}", extension);
    }

    private void generateKey() {
        byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        ivspec = new IvParameterSpec(iv);
        secretKey = new SecretKeySpec(password.getBytes(), "AES");
    }


    public Cipher getAes() {
        return aes;
    }

    public IvParameterSpec getIvspec() {
        return ivspec;
    }

    @Override
    public CompressionOutputStream createOutputStream(OutputStream out) {
        try {
            aes.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
        } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
            throw new RuntimeException("invalid key", e);
        }
        CipherOutputStream cf = new CipherOutputStream(out, aes);
        return new NoopCompressionOutputStream(cf);
    }

    public static class NoopCompressionOutputStream extends CompressionOutputStream {
        NoopCompressionOutputStream(OutputStream out) {
            super(out);
        }

        @Override
        public void write(int b) throws IOException {
            out.write(b);
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            out.write(b, off, len);
        }

        @Override
        public void finish() throws IOException {
            LOGGER.info(">>Finishing encryption thr' default mechanism.");
        }

        @Override
        public void resetState() throws IOException {
            LOGGER.info(">>ResetState called in encrypter");
        }
    }
}
