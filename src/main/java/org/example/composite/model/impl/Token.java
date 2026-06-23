package org.example.composite.model.impl;

import org.example.composite.model.ComponentType;
import org.example.composite.model.TextComponent;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Token implements TextComponent {
    private final String value;

    public Token(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public void addChild(TextComponent component) {
        throw new UnsupportedOperationException("Token is a leaf node");
    }

    @Override
    public List<TextComponent> getChildren() {
        return Collections.emptyList();
    }

    @Override
    public String reconstruct() {
        return value;
    }

    @Override
    public ComponentType getType() {
        return ComponentType.TOKEN;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Token that = (Token) obj;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Token {value='");
        sb.append(value);
        sb.append("'}");
        return sb.toString();
    }
}