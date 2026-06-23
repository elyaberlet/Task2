package org.example.composite.reader;

import org.example.composite.exception.TextReaderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TextFileReader {

    private static final Logger logger = LoggerFactory.getLogger(TextFileReader.class);

    public String read(String filePath) throws TextReaderException {
        if (filePath == null || filePath.isBlank()) {
            throw new TextReaderException("File path cannot be null or blank");
        }

        try {
            String content = Files.readString(Path.of(filePath));
            logger.info("Read {} chars from '{}'", content.length(), filePath);
            return content;
        } catch (IOException e) {
            throw new TextReaderException("Failed to read file: " + filePath, e);
        }
    }
}