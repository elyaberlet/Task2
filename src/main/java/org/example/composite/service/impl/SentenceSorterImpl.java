package org.example.composite.service.impl;

import org.example.composite.exception.TextException;
import org.example.composite.model.ComponentType;
import org.example.composite.model.TextComponent;
import org.example.composite.model.impl.Token;
import org.example.composite.service.SentenceSorter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SentenceSorterImpl implements SentenceSorter {

    @Override
    public List<String> sortSentencesByLetter(TextComponent text, char letter) throws TextException {
        if (text.getType() != ComponentType.TEXT) {
            throw new TextException("Expected TEXT component");
        }

        List<TextComponent> sentences = collectSentences(text);

        sentences.sort((s1, s2) -> {
            int count1 = countLetter(s1, letter);
            int count2 = countLetter(s2, letter);
            return Integer.compare(count1, count2);
        });

        return sentences.stream()
                .map(TextComponent::reconstruct)
                .collect(Collectors.toList());
    }

    private List<TextComponent> collectSentences(TextComponent component) {
        List<TextComponent> sentences = new ArrayList<>();

        if (component.getType() == ComponentType.SENTENCE) {
            sentences.add(component);
        }

        for (TextComponent child : component.getChildren()) {
            sentences.addAll(collectSentences(child));
        }

        return sentences;
    }

    private int countLetter(TextComponent component, char letter) {
        char lowerLetter = Character.toLowerCase(letter);
        int count = 0;

        if (component instanceof Token) {
            for (char c : component.reconstruct().toCharArray()) {
                if (Character.toLowerCase(c) == lowerLetter) {
                    count++;
                }
            }
        }

        for (TextComponent child : component.getChildren()) {
            count += countLetter(child, letter);
        }

        return count;
    }
}
