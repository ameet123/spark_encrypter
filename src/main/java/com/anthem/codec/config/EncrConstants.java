package com.anthem.codec.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EncrConstants {
    public static final String PROP_FILE = "application.properties";
    public static final String PASSWORD_KEY = "password";
    public static final String EXT_KEY = "extension";
    private static final Logger LOGGER = LoggerFactory.getLogger(EncrConstants.class);
    public static String password;
    public static String extension;
    public static String pubKey;
    public static String recipient;

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
        recipient = properties.getProperty("recipient");
        LOGGER.info(">>Encrypted file extension:{}", extension);
    }
}
