package org.example.composite.service.impl;

import org.example.composite.exception.TextException;
import org.example.composite.model.ComponentType;
import org.example.composite.model.TextComponent;
import org.example.composite.service.SentenceModifier;

import java.util.List;

public class SentenceModifierImpl implements SentenceModifier {

    @Override
    public TextComponent swapFirstLastLexeme(TextComponent text) throws TextException {
        if (text.getType() != ComponentType.TEXT) {
            throw new TextException("Expected TEXT component");
        }

        swapRecursive(text);
        return text;
    }

    private void swapRecursive(TextComponent component) {
        if (component.getType() == ComponentType.SENTENCE) {
            List<TextComponent> children = component.getChildren();

            if (children.size() > 1) {
                int first = 0;
                int last = children.size() - 1;

                while (first <= last && !isLexeme(children.get(first))) {
                    first++;
                }
                while (last >= first && !isLexeme(children.get(last))) {
                    last--;
                }

                if (first < last) {
                    TextComponent temp = children.get(first);
                    children.set(first, children.get(last));
                    children.set(last, temp);
                }
            }
        }

        for (TextComponent child : component.getChildren()) {
            swapRecursive(child);
        }
    }

    private boolean isLexeme(TextComponent component) {
        String text = component.reconstruct().trim();
        return !text.isEmpty() && text.matches(".*[a-zA-Zа-яА-ЯёЁ].*");
    }
}