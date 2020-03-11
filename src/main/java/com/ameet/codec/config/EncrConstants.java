package com.ameet.codec.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EncrConstants {
    private static final String PROP_FILE = "application.properties";
    private static final String PASSWORD_KEY = "password";
    private static final String EXT_KEY = "extension";
    private static final String IS_SIGN_KEY = "decrypt_w_signing";
    private static final String ENCR_STRAT_KEY = "encrypt_strategy";
    private static final Logger LOGGER = LoggerFactory.getLogger(EncrConstants.class);
    public static String password;
    public static String extension;
    public static String pubKey, privKey;
    public static String recipient;
    public static String encryptionStrategy;
    public static boolean isSignRequired;

    static {
        LOGGER.info(">>Reading properties from :{}", PROP_FILE);
        Properties properties = new Properties();
        InputStream propertyStream = EncrConstants.class.getClassLoader().getResourceAsStream(EncrConstants.PROP_FILE);
        try {
            properties.load(propertyStream);
        } catch (IOException e) {
            LOGGER.error("ERR: Could not load property file", e);
            throw new RuntimeException(e);
        }
        password = properties.getProperty(EncrConstants.PASSWORD_KEY);
        extension = properties.getProperty(EncrConstants.EXT_KEY);
        pubKey = properties.getProperty("public_key");
        privKey = properties.getProperty("private_key");
        isSignRequired = Boolean.parseBoolean(properties.getProperty(IS_SIGN_KEY));
        recipient = properties.getProperty("recipient");
        encryptionStrategy = properties.getProperty(ENCR_STRAT_KEY);
        LOGGER.info(">>Encryption Strategy:{} Encrypted file extension:{}", encryptionStrategy, extension);
    }
}
