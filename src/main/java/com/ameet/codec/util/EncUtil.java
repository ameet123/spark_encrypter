package com.ameet.codec.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class EncUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(EncUtil.class);

    public static String fileToString(String filename) {
        try {
            return new String(Files.readAllBytes(Paths.get(EncUtil.class.getResource(filename).toURI())));
        } catch (IOException | URISyntaxException e) {
            LOGGER.error("ERR: can't read file from classpath:{}", filename, e);
            throw new RuntimeException(e);
        }
    }
}
