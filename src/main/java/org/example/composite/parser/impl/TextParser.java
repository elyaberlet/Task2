package org.example.composite.parser.impl;

import org.example.composite.model.ComponentType;
import org.example.composite.model.impl.CompositeComponent;
import org.example.composite.model.TextComponent;
import org.example.composite.parser.AbstractParser;

public class TextParser implements AbstractParser {
    private AbstractParser nextParser;

    @Override
    public void setNext(AbstractParser next) {
        this.nextParser = next;
    }

    @Override
    public TextComponent parse(String input) {
        CompositeComponent text = new CompositeComponent(ComponentType.TEXT);

        if (input == null || input.isBlank()) {
            return text;
        }

        String normalized = input.replaceAll(WHITESPACE_PATTERN, " ");
        String[] paragraphs = normalized.split(PARAGRAPH_SEPARATOR);

        for (String paragraph : paragraphs) {
            if (!paragraph.isBlank()) {
                if (nextParser != null) {
                    TextComponent paragraphComponent = nextParser.parse(paragraph);
                    text.addChild(paragraphComponent);
                }
            }
        }

        return text;
    }
}