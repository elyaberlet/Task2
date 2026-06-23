package org.example.composite.service;

import org.example.composite.exception.TextException;
import org.example.composite.model.TextComponent;

public interface SentenceAnalyzer {
    int findMaxSentencesWithSameWords(TextComponent component) throws TextException;
}
