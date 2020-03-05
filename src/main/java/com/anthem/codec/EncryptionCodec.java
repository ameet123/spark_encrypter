package com.anthem.codec;

import com.anthem.codec.cipher.AESNoPaddingProvider;
import com.anthem.codec.cipher.KeyCipherProvider;
import com.anthem.codec.config.EncrConstants;
import org.apache.hadoop.io.compress.CompressionOutputStream;
import org.apache.hadoop.io.compress.GzipCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.CipherOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class EncryptionCodec extends GzipCodec {
    private static final Logger LOGGER = LoggerFactory.getLogger(EncryptionCodec.class);
    private String extension;
    private String password;
    private KeyCipherProvider provider;

    public EncryptionCodec() {
        LOGGER.info(">>Loading properties");
        readProperties();
        LOGGER.info(">>Instantiating Key/Cipher provider based on provided password");
        provider = new AESNoPaddingProvider(password);
        LOGGER.info(">>Initialization completed.");
    }

    @Override
    public String getDefaultExtension() {
        return extension;
    }

    private void readProperties() {
        Properties properties = new Properties();
        InputStream propertyStream = getClass().getClassLoader().getResourceAsStream(EncrConstants.PROP_FILE);
        try {
            properties.load(propertyStream);
        } catch (IOException e) {
            LOGGER.error("ERR: Could not load property file", e);
            throw new RuntimeException(e);
        }
        password = properties.getProperty(EncrConstants.PASSWORD_KEY);
        extension = properties.getProperty(EncrConstants.EXT_KEY);
        LOGGER.info(">>Encrypted file extension:{}", extension);
    }

    public KeyCipherProvider getProvider() {
        return provider;
    }

    @Override
    public CompressionOutputStream createOutputStream(OutputStream out) {
        provider.encryptInit();

        CipherOutputStream cf = new CipherOutputStream(out, provider.getCipher());
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
