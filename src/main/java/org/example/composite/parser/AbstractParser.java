package org.example.composite.parser;

import org.example.composite.model.TextComponent;

public interface AbstractParser {

    String PARAGRAPH_SEPARATOR = "\\n\\s*\\n";
    String SENTENCE_SEPARATOR = "(?<=[.!?])\\s+";
    String TOKEN_SEPARATOR = "\\s+";
    String WHITESPACE_PATTERN = "\\s+";

    TextComponent parse(String input);

    void setNext(AbstractParser next);
}
