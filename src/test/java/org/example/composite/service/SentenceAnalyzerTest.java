package org.example.composite.service;

import org.example.composite.exception.TextException;
import org.example.composite.model.ComponentType;
import org.example.composite.model.impl.CompositeComponent;
import org.example.composite.model.TextComponent;
import org.example.composite.model.impl.Token;
import org.example.composite.service.impl.SentenceAnalyzerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SentenceAnalyzerTest {

    private SentenceAnalyzerImpl analyzer;

    @BeforeEach
    void setUp() {
        analyzer = new SentenceAnalyzerImpl();
    }

    @Test
    void testFindMaxSentencesWithSameWords_ReturnsMaxCount() throws TextException {
        TextComponent text = createText(
                sentence("How", " ", "are", " ", "you", "?"),
                sentence("I", " ", "am", " ", "fine", "."),
                sentence("How", " ", "is", " ", "he", "?")
        );

        int result = analyzer.findMaxSentencesWithSameWords(text);

        assertEquals(2, result);
    }

    @Test
    void testFindMaxSentencesWithSameWords_AllDifferent_ReturnsOne() throws TextException {
        TextComponent text = createText(
                sentence("One", " ", "two", "."),
                sentence("Three", " ", "four", "."),
                sentence("Five", " ", "six", ".")
        );

        int result = analyzer.findMaxSentencesWithSameWords(text);

        assertEquals(1, result);
    }

    @Test
    void testFindMaxSentencesWithSameWords_EmptyText_ReturnsZero() throws TextException {
        CompositeComponent text = new CompositeComponent(ComponentType.TEXT);

        int result = analyzer.findMaxSentencesWithSameWords(text);

        assertEquals(0, result);
    }

    @Test
    void testFindMaxSentencesWithSameWords_NotText_ThrowsException() {
        TextComponent sentence = new CompositeComponent(ComponentType.SENTENCE);

        assertThrows(TextException.class, () -> {
            analyzer.findMaxSentencesWithSameWords(sentence);
        });
    }

    private TextComponent createText(CompositeComponent... sentences) {
        CompositeComponent text = new CompositeComponent(ComponentType.TEXT);
        CompositeComponent paragraph = new CompositeComponent(ComponentType.PARAGRAPH);

        for (CompositeComponent sentence : sentences) {
            paragraph.addChild(sentence);
        }
        text.addChild(paragraph);
        return text;
    }

    private CompositeComponent sentence(String... tokens) {
        CompositeComponent sentence = new CompositeComponent(ComponentType.SENTENCE);
        for (String token : tokens) {
            sentence.addChild(new Token(token));
        }
        return sentence;
    }
}