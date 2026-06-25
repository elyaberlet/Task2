package org.example.composite.service.impl;

import org.example.composite.exception.TextException;
import org.example.composite.model.ComponentType;
import org.example.composite.model.TextComponent;
import org.example.composite.service.SentenceAnalyzer;

import java.util.*;

public class SentenceAnalyzerImpl implements SentenceAnalyzer {

    @Override
    public int findMaxSentencesWithSameWords(TextComponent component) throws TextException {
        List<Set<String>> sentenceWords = new ArrayList<>();
        collectSentenceWords(component, sentenceWords);

        Map<String, Integer> wordCount = new HashMap<>();

        for (Set<String> words : sentenceWords) {
            for (String word : words) {
                wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
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
        if (component.getType() == ComponentType.WORD) {
            String word = component.reconstruct();
            words.add(word.toLowerCase());
        }

        for (TextComponent child : component.getChildren()) {
            extractWords(child, words);
        }
    }
}