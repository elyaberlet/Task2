package org.example.composite.parser.impl;

import org.example.composite.model.ComponentType;
import org.example.composite.model.impl.CompositeComponent;
import org.example.composite.model.TextComponent;
import org.example.composite.model.impl.Token;
import org.example.composite.parser.AbstractParser;

public class SentenceParser implements AbstractParser {
    private AbstractParser nextParser;

    @Override
    public void setNext(AbstractParser next) {
        this.nextParser = next;
    }

    @Override
    public TextComponent parse(String input) {
        CompositeComponent sentence = new CompositeComponent(ComponentType.SENTENCE);

        if (input == null || input.isBlank()) {
            return sentence;
        }

        String[] parts = input.trim().split(TOKEN_SEPARATOR);

        for (String part : parts) {
            if (!part.isEmpty()) {
                sentence.addChild(new Token(part));
            }
        }

        return sentence;
    }
}