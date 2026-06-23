package org.example.composite.service.impl;

import org.example.composite.exception.TextException;
import org.example.composite.model.ComponentType;
import org.example.composite.model.TextComponent;
import org.example.composite.model.impl.Token;
import org.example.composite.service.SentenceAnalyzer;

import java.util.*;

public class SentenceAnalyzerImpl implements SentenceAnalyzer {

    @Override
    public int findMaxSentencesWithSameWords(TextComponent component) throws TextException {
        if (component.getType() != ComponentType.TEXT) {
            throw new TextException("Expected TEXT component");
        }

        List<Set<String>> sentenceWords = new ArrayList<>();
        collectSentenceWords(component, sentenceWords);

        Map<String, Integer> wordCount = new HashMap<>();

        for (Set<String> words : sentenceWords) {
            for (String word : words) {
                wordCount.put(word, wordCount.getOrDefault(word,0) + 1);
            }
        }

        int maxCount = 0;
        for (int count : wordCount.values()) {
            if (count > maxCount) {
                maxCount = count;
            }
        }

        return maxCount;
    }

    private void collectSentenceWords(TextComponent component, List<Set<String>> result) {
        if (component.getType() == ComponentType.SENTENCE) {
            Set<String> words = new HashSet<>();
        extractWords(component, words);
        if (!words.isEmpty()) {
            result.add(words);
        }
        }

        for (TextComponent child : component.getChildren()) {
            collectSentenceWords(child, result);
        }
    }

    private void extractWords(TextComponent component, Set<String> words) {
        if (component instanceof Token) {
            String value = component.reconstruct();
            if (value.matches("[a-zA-Zа-яА-ЯёЁ]+")) {
                words.add(value.toLowerCase());
            }
        }

        for (TextComponent child : component.getChildren()) {
            extractWords(child, words);
        }
    }
}
