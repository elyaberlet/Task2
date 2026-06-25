package org.example.composite.parser;

import org.example.composite.model.TextComponent;

public interface AbstractParser {

    String PARAGRAPH_SEPARATOR = "\\n\\s*\\n";
    String SENTENCE_SEPARATOR = "(?<=[.!?])\\s+";
    String LEXEME_SEPARATOR = "\\s+";
    String WHITESPACE_PATTERN = "\\s+";
    String WORD_PUNCTUATION_REGEX = "(?=[\\p{Punct}])|(?<=[\\p{Punct}])";
    String WORD_CHECK_REGEX = ".*\\w+.*";

    void parse(TextComponent component, String text);

    void setNext(AbstractParser next);
}