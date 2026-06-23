package org.example.composite.service;

import org.example.composite.exception.TextException;
import org.example.composite.model.TextComponent;

import java.util.List;

public interface SentenceSorter {
    List<String> sortSentencesByLetter(TextComponent text, char letter) throws TextException;
}
