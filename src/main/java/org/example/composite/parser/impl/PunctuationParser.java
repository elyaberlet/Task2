package org.example.composite.parser.impl;

import org.example.composite.model.ComponentType;
import org.example.composite.model.TextComponent;
import org.example.composite.model.impl.CompositeComponent;
import org.example.composite.parser.AbstractParser;

public class PunctuationParser implements AbstractParser {
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

        CompositeComponent punctuation = new CompositeComponent(ComponentType.PUNCTUATION);
        component.addChild(punctuation);

        if (nextParser != null) {
            nextParser.parse(punctuation, text);
        }
    }
}