package org.example.composite.parser.impl;

import org.example.composite.model.ComponentType;
import org.example.composite.model.TextComponent;
import org.example.composite.model.impl.CompositeComponent;
import org.example.composite.parser.AbstractParser;

public class SentenceParser implements AbstractParser {
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

        CompositeComponent sentence = new CompositeComponent(ComponentType.SENTENCE);
        component.addChild(sentence);

        String[] lexemes = text.strip().split(LEXEME_SEPARATOR);

        for (String lexeme : lexemes) {
            if (lexeme.isEmpty()) continue;

            CompositeComponent lexemeComponent = new CompositeComponent(ComponentType.LEXEME);
            sentence.addChild(lexemeComponent);

            if (nextParser != null) {
                nextParser.parse(lexemeComponent, lexeme);
            }
        }
    }
}