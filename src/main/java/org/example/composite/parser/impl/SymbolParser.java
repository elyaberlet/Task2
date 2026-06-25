package org.example.composite.parser.impl;

import org.example.composite.model.ComponentType;
import org.example.composite.model.TextComponent;
import org.example.composite.model.impl.SymbolLeaf;
import org.example.composite.parser.AbstractParser;

public class SymbolParser implements AbstractParser {
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

        for (char c : text.toCharArray()) {
            component.addChild(new SymbolLeaf(c, ComponentType.SYMBOL));
        }
    }
}