package org.example.composite;

import org.example.composite.exception.TextException;
import org.example.composite.model.ComponentType;
import org.example.composite.model.impl.CompositeComponent;
import org.example.composite.model.TextComponent;
import org.example.composite.model.impl.Token;
import org.example.composite.service.impl.SentenceSorterImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SentenceSorterTest {

    private SentenceSorterImpl sorter;

    @BeforeEach
    void setUp() {
        sorter = new SentenceSorterImpl();
    }

    @Test
    void testSortSentencesByLetter_Simple() throws TextException {
        TextComponent text = createText(
                sentence("How", " ", "are", " ", "you", "?"),
                sentence("I", " ", "am", " ", "fine", "."),
                sentence("Hello", " ", "world", "!")
        );

        List<String> result = sorter.sortSentencesByLetter(text, 'a');

        assertEquals(3, result.size());
        assertEquals("Hello   world !", result.get(0));
        assertEquals("How   are   you ?", result.get(1));
        assertEquals("I   am   fine .", result.get(2));
    }

    @Test
    void testSortSentencesByLetter_EmptyText() throws TextException {
        CompositeComponent text = new CompositeComponent(ComponentType.TEXT);

        List<String> result = sorter.sortSentencesByLetter(text, 'a');

        assertTrue(result.isEmpty());
    }

    @Test
    void testSortSentencesByLetter_NotText_ThrowsException() {
        TextComponent sentence = new CompositeComponent(ComponentType.SENTENCE);

        assertThrows(TextException.class, () -> {
            sorter.sortSentencesByLetter(sentence, 'a');
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