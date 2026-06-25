package org.example.composite.service;

import org.example.composite.exception.TextException;
import org.example.composite.model.ComponentType;
import org.example.composite.model.TextComponent;
import org.example.composite.model.impl.CompositeComponent;
import org.example.composite.parser.impl.*;
import org.example.composite.service.impl.SentenceModifierImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SentenceModifierTest {

    private SentenceModifierImpl modifier;
    private SentenceParser sentenceParser;

    @BeforeEach
    void setUp() {
        modifier = new SentenceModifierImpl();
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
    void testSwapFirstLastLexeme_ShouldSwapFirstAndLast() throws TextException {
        TextComponent rootText = new CompositeComponent(ComponentType.TEXT);
        CompositeComponent paragraph = new CompositeComponent(ComponentType.PARAGRAPH);
        rootText.addChild(paragraph);

        sentenceParser.parse(paragraph, "How are you?");

        TextComponent result = modifier.swapFirstLastLexeme(rootText);
        String reconstructed = result.reconstruct();

        assertEquals("you? are How", reconstructed);
    }

    @Test
    void testSwapFirstLastLexeme_MultipleSentences_ShouldSwapInEach() throws TextException {
        TextComponent rootText = new CompositeComponent(ComponentType.TEXT);
        CompositeComponent paragraph = new CompositeComponent(ComponentType.PARAGRAPH);
        rootText.addChild(paragraph);

        sentenceParser.parse(paragraph, "How are you?");
        sentenceParser.parse(paragraph, "I am fine.");

        TextComponent result = modifier.swapFirstLastLexeme(rootText);
        String reconstructed = result.reconstruct();

        assertEquals("you? are How\tfine. am I", reconstructed);
    }

    @Test
    void testSwapFirstLastLexeme_SingleWord_ShouldNotChange() throws TextException {
        TextComponent rootText = new CompositeComponent(ComponentType.TEXT);
        CompositeComponent paragraph = new CompositeComponent(ComponentType.PARAGRAPH);
        rootText.addChild(paragraph);

        sentenceParser.parse(paragraph, "Hello.");

        TextComponent result = modifier.swapFirstLastLexeme(rootText);
        String reconstructed = result.reconstruct();

        assertEquals("Hello.", reconstructed);
    }

    @Test
    void testSwapFirstLastLexeme_EmptyText_ShouldReturnEmpty() throws TextException {
        TextComponent rootText = new CompositeComponent(ComponentType.TEXT);

        TextComponent result = modifier.swapFirstLastLexeme(rootText);
        String reconstructed = result.reconstruct();

        assertEquals("", reconstructed);
    }

    @Test
    void testSwapFirstLastLexeme_WithPunctuation_ShouldSwapCorrectly() throws TextException {
        TextComponent rootText = new CompositeComponent(ComponentType.TEXT);
        CompositeComponent paragraph = new CompositeComponent(ComponentType.PARAGRAPH);
        rootText.addChild(paragraph);

        sentenceParser.parse(paragraph, "How are you?");

        TextComponent result = modifier.swapFirstLastLexeme(rootText);
        String reconstructed = result.reconstruct();

        assertEquals("you? are How", reconstructed);
    }
}