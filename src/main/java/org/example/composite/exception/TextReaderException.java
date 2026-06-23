package org.example.composite.exception;

public class TextReaderException extends Exception {
    public TextReaderException(String message) {
        super(message);
    }

    public TextReaderException(String message, Throwable cause) {
        super(message, cause);
    }
}
