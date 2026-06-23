package org.example.composite.util;

import org.example.composite.model.TextComponent;

public class TextStatisticsUtil {

    public static int countCharacters(TextComponent component) {
        return component.reconstruct().length();
    }

    public static int countLetters(TextComponent component) {
        int count = 0;
        for (char c : component.reconstruct().toCharArray()) {
            if (Character.isLetter(c)) {
                count++;
            }
        }
        return count;
    }
}
