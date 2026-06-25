package org.example.composite.parser.impl;

import org.example.composite.model.ComponentType;
import org.example.composite.model.TextComponent;
import org.example.composite.model.impl.CompositeComponent;
import org.example.composite.parser.AbstractParser;

public class TextParser implements AbstractParser {
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

        String normalized = text.replaceAll(WHITESPACE_PATTERN, " ");
        String[] paragraphs = normalized.split(PARAGRAPH_SEPARATOR);

        for (String paragraph : paragraphs) {
            if (paragraph.isBlank()) continue;

            CompositeComponent paragraphComponent = new CompositeComponent(ComponentType.PARAGRAPH);
            component.addChild(paragraphComponent);

            if (nextParser != null) {
                nextParser.parse(paragraphComponent, paragraph);
            }
        }
    }
}