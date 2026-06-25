package org.example.composite.parser.impl;

import org.example.composite.model.ComponentType;
import org.example.composite.model.TextComponent;
import org.example.composite.model.impl.CompositeComponent;
import org.example.composite.parser.AbstractParser;

public class LexemeParser implements AbstractParser {
    private AbstractParser nextParser;

    @Override
    public void setNext(AbstractParser next) {
        this.nextParser = next;
    }

    @Override
    public void parse(TextComponent component, String text) {
        if (text == null || text.isBlank()) {
            return;
        }

        String[] lexemes = text.strip().split(LEXEME_SEPARATOR);

        for (String lexeme : lexemes) {
            if (lexeme.isEmpty()) continue;

            CompositeComponent lexemeComponent = new CompositeComponent(ComponentType.LEXEME);
            component.addChild(lexemeComponent);

            String word = lexeme.replaceAll(WORD_PUNCTUATION_REGEX, "");
            String punctuation = lexeme.replaceAll(WORD_CHECK_REGEX, "");

            if (!word.isEmpty()) {
                if (nextParser != null) {
                    nextParser.parse(lexemeComponent, word);
                }
            }

            if (!punctuation.isEmpty()) {
                if (nextParser != null) {
                    nextParser.parse(lexemeComponent, punctuation);
                }
            }
        }
    }
}