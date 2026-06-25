package org.example.composite.model.impl;

import org.example.composite.model.ComponentType;
import org.example.composite.model.TextComponent;

import java.util.Collections;
import java.util.List;

public class SymbolLeaf implements TextComponent {

    private final char value;
    private final ComponentType type;

    public SymbolLeaf(char value, ComponentType type) {
        this.value = value;
        this.type = type;
    }

    public char getValue() {
        return value;
    }

    @Override
    public void addChild(TextComponent component) {
        throw new UnsupportedOperationException("SymbolLeaf is a leaf node");
    }

    @Override
    public List<TextComponent> getChildren() {
        return Collections.emptyList();
    }

    @Override
    public ComponentType getType() {
        return type;
    }

    @Override
    public String reconstruct() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        SymbolLeaf that = (SymbolLeaf) obj;
        return type == that.type && value == that.value;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + Character.hashCode(value);
        return result;
    }

    @Override
    public String toString() {
        return "SymbolLeaf{" +
                "type=" + type +
                ", value='" + value + '\'' +
                '}';
    }
}