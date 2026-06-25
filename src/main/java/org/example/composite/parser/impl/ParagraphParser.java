package org.example.composite.parser.impl;

import org.example.composite.model.ComponentType;
import org.example.composite.model.TextComponent;
import org.example.composite.model.impl.CompositeComponent;
import org.example.composite.parser.AbstractParser;

public class ParagraphParser implements AbstractParser {
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

        String[] sentences = text.strip().split(SENTENCE_SEPARATOR);

        for (String sentence : sentences) {
            if (sentence.isBlank()) continue;

            CompositeComponent sentenceComponent = new CompositeComponent(ComponentType.SENTENCE);
            component.addChild(sentenceComponent);

            if (nextParser != null) {
                nextParser.parse(sentenceComponent, sentence.trim());
            }
        }
    }
}