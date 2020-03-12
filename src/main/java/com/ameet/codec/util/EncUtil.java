package com.ameet.codec.util;

import com.ameet.codec.config.EncrConstants;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class EncUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(EncUtil.class);

    public static String fileToString(String filename) {
        LOGGER.info(">>Reading file:{}", filename);
        try (InputStream propertyStream = EncrConstants.class.getClassLoader().getResourceAsStream(filename)) {
            assert propertyStream != null;
            return IOUtils.toString(propertyStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            LOGGER.error("ERR: can't read file from classpath:{}", filename, e);
            throw new RuntimeException(e);
        }
    }
}
