package org.example.composite.service;

import org.example.composite.exception.TextException;
import org.example.composite.model.ComponentType;
import org.example.composite.model.TextComponent;
import org.example.composite.model.impl.CompositeComponent;
import org.example.composite.parser.impl.*;
import org.example.composite.service.impl.SentenceAnalyzerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SentenceAnalyzerTest {

    private SentenceAnalyzerImpl analyzer;
    private SentenceParser sentenceParser;

    @BeforeEach
    void setUp() {
        analyzer = new SentenceAnalyzerImpl();
        SymbolParser symbolParser = new SymbolParser();
        PunctuationParser punctuationParser = new PunctuationParser();
        punctuationParser.setNext(symbolParser);
        WordParser wordParser = new WordParser();
        wordParser.setNext(punctuationParser);
        LexemeParser lexemeParser = new LexemeParser();
        lexemeParser.setNext(wordParser);
        sentenceParser = new SentenceParser();
        sentenceParser.setNext(lexemeParser);
    }

    @Test
    void testFindMaxSentencesWithSameWords_ShouldReturnMaxCount() throws TextException {
        TextComponent rootText = new CompositeComponent(ComponentType.TEXT);
        CompositeComponent paragraph = new CompositeComponent(ComponentType.PARAGRAPH);
        rootText.addChild(paragraph);

        sentenceParser.parse(paragraph, "How are you?");
        sentenceParser.parse(paragraph, "I am fine.");
        sentenceParser.parse(paragraph, "How is he?");

        int result = analyzer.findMaxSentencesWithSameWords(rootText);

        assertEquals(2, result);
    }

    @Test
    void testFindMaxSentencesWithSameWords_AllDifferent_ShouldReturnOne() throws TextException {
        TextComponent rootText = new CompositeComponent(ComponentType.TEXT);
        CompositeComponent paragraph = new CompositeComponent(ComponentType.PARAGRAPH);
        rootText.addChild(paragraph);

        sentenceParser.parse(paragraph, "One two.");
        sentenceParser.parse(paragraph, "Three four.");
        sentenceParser.parse(paragraph, "Five six.");

        int result = analyzer.findMaxSentencesWithSameWords(rootText);

        assertEquals(1, result);
    }

    @Test
    void testFindMaxSentencesWithSameWords_EmptyText_ShouldReturnZero() throws TextException {
        TextComponent rootText = new CompositeComponent(ComponentType.TEXT);

        int result = analyzer.findMaxSentencesWithSameWords(rootText);

        assertEquals(0, result);
    }
}