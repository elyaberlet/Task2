package org.example.composite.service;

import org.example.composite.exception.TextException;
import org.example.composite.model.ComponentType;
import org.example.composite.model.TextComponent;
import org.example.composite.model.impl.CompositeComponent;
import org.example.composite.parser.impl.*;
import org.example.composite.service.impl.SentenceSorterImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SentenceSorterTest {

    private SentenceSorterImpl sorter;
    private SentenceParser sentenceParser;

    @BeforeEach
    void setUp() {
        sorter = new SentenceSorterImpl();
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
    void testSortSentencesByLetter_ShouldSortByLetterCount() throws TextException {
        TextComponent rootText = new CompositeComponent(ComponentType.TEXT);
        CompositeComponent paragraph = new CompositeComponent(ComponentType.PARAGRAPH);
        rootText.addChild(paragraph);

        sentenceParser.parse(paragraph, "How are you?");
        sentenceParser.parse(paragraph, "I am fine.");
        sentenceParser.parse(paragraph, "How is he?");

        List<String> result = sorter.sortSentencesByLetter(rootText, 'a');

        assertAll(
                () -> assertEquals(3, result.size()),
                () -> assertEquals("How is he?", result.get(0)),
                () -> assertEquals("How are you?", result.get(1)),
                () -> assertEquals("I am fine.", result.get(2))
        );
    }

    @Test
    void testSortSentencesByLetter_EmptyText_ShouldReturnEmptyList() throws TextException {
        TextComponent rootText = new CompositeComponent(ComponentType.TEXT);

        List<String> result = sorter.sortSentencesByLetter(rootText, 'a');

        assertTrue(result.isEmpty());
    }

    @Test
    void testSortSentencesByLetter_OneSentence_ShouldReturnOne() throws TextException {
        TextComponent rootText = new CompositeComponent(ComponentType.TEXT);
        CompositeComponent paragraph = new CompositeComponent(ComponentType.PARAGRAPH);
        rootText.addChild(paragraph);

        sentenceParser.parse(paragraph, "Hello world.");

        List<String> result = sorter.sortSentencesByLetter(rootText, 'l');

        assertAll(
                () -> assertEquals(1, result.size()),
                () -> assertEquals("Hello world.", result.get(0))
        );
    }

    @Test
    void testSortSentencesByLetter_WithDifferentLetters_ShouldSortCorrectly() throws TextException {
        TextComponent rootText = new CompositeComponent(ComponentType.TEXT);
        CompositeComponent paragraph = new CompositeComponent(ComponentType.PARAGRAPH);
        rootText.addChild(paragraph);

        sentenceParser.parse(paragraph, "How are you?");
        sentenceParser.parse(paragraph, "I am fine.");
        sentenceParser.parse(paragraph, "How is he?");

        List<String> result = sorter.sortSentencesByLetter(rootText, 'h');

        assertAll(
                () -> assertEquals(3, result.size()),
                () -> assertEquals("I am fine.", result.get(0)),
                () -> assertEquals("How are you?", result.get(1)),
                () -> assertEquals("How is he?", result.get(2))
        );
    }
}