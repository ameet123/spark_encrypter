package com.anthem.codec;

import com.anthem.codec.cipher.EncryptDecrypt;
import com.anthem.codec.cipher.PgPProvider;
import com.anthem.codec.compress.NoopCompressionOutputStream;
import org.apache.hadoop.io.compress.CompressionOutputStream;
import org.apache.hadoop.io.compress.GzipCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.OutputStream;

/**
 * main class invoked from Spark via dataframe write methods and codecs.
 */
public class EncryptionCodec extends GzipCodec {
    private static final Logger LOGGER = LoggerFactory.getLogger(EncryptionCodec.class);
    private EncryptDecrypt provider;

    public EncryptionCodec() {
        LOGGER.info(">>Instantiating Key/Cipher provider based on provided password");
        provider = new PgPProvider();
        LOGGER.info(">>Initialization completed.");
    }

    @Override
    public String getDefaultExtension() {
        return provider.extension();
    }

    @Override
    public CompressionOutputStream createOutputStream(OutputStream out) {
        return new NoopCompressionOutputStream(provider.cipherStream(out), provider);
    }

    /**
     * so that during testing, we can call the terminate method on the provider
     *
     * @return EncryptDecrypt provider
     */
    public EncryptDecrypt getProvider() {
        return provider;
    }

}
