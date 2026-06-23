package org.example.composite.service;

import org.example.composite.exception.TextException;
import org.example.composite.model.TextComponent;

public interface SentenceModifier {
    TextComponent swapFirstLastLexeme(TextComponent text) throws TextException;
}
