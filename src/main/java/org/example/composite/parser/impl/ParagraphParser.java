package org.example.composite.parser.impl;

import org.example.composite.model.ComponentType;
import org.example.composite.model.impl.CompositeComponent;
import org.example.composite.model.TextComponent;
import org.example.composite.parser.AbstractParser;

public class ParagraphParser implements AbstractParser {
    private AbstractParser nextParser;

    @Override
    public void setNext(AbstractParser next) {
        this.nextParser = next;
    }

    @Override
    public TextComponent parse(String input) {
        CompositeComponent paragraph = new CompositeComponent(ComponentType.PARAGRAPH);

        if (input == null || input.isBlank()) {
            return paragraph;
        }

        String[] sentences = input.trim().split(SENTENCE_SEPARATOR);

        for (String sentence : sentences) {
            if (!sentence.trim().isEmpty() && nextParser != null) {
                paragraph.addChild(nextParser.parse(sentence.trim()));
            }
        }

        return paragraph;
    }
}