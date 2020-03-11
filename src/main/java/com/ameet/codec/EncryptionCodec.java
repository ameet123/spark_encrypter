package com.ameet.codec;

import com.ameet.codec.cipher.PgPProvider;
import com.ameet.codec.config.EncrConstants;
import com.ameet.codec.cipher.AESNoPaddingProvider;
import com.ameet.codec.cipher.EncryptDecrypt;
import com.ameet.codec.compress.NoopCompressionOutputStream;
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
        switch (EncrConstants.encryptionStrategy) {
            case "PgPProvider":
                LOGGER.info(">>Instantiating PgP encryption strategy");
                provider = new PgPProvider();
                break;
            case "AESNoPaddingProvider":
                LOGGER.info(">>Instantiating AES No padding encryption strategy");
                provider = new AESNoPaddingProvider();
                break;
            default:
                LOGGER.info("ERR: Unknown strategy:{}", EncrConstants.encryptionStrategy);
                throw new RuntimeException("ERR: Unknown strategy");
        }
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
