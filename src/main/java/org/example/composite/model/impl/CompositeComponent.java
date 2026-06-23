package org.example.composite.model.impl;

import org.example.composite.model.ComponentType;
import org.example.composite.model.TextComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CompositeComponent implements TextComponent {
    private final ComponentType type;
    private final List<TextComponent> children = new ArrayList<>();

    public CompositeComponent(ComponentType type) {
        this.type = type;
    }

    @Override
    public void addChild(TextComponent component) {
        children.add(component);
    }

    @Override
    public List<TextComponent> getChildren() {
        return new ArrayList<>(children);
    }

    @Override
    public ComponentType getType() {
        return type;
    }

    @Override
    public void setChild(int index, TextComponent component) {
        if (index < 0 || index >= children.size())
            throw new IndexOutOfBoundsException();
        children.set(index, component);
    }

    @Override
    public String reconstruct() {
        StringBuilder result = new StringBuilder();
        String separator = getSeparator();

        for (int i = 0; i < children.size(); i++) {
            result.append(children.get(i).reconstruct());
            if (i < children.size() - 1) {
                result.append(separator);
            }
        }
        return result.toString();
    }

    private String getSeparator() {
        switch (type) {
            case TEXT:
                return "\n";
            case PARAGRAPH:
                return "\t";
            case SENTENCE:
                return " ";
            default:
                return "";
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        CompositeComponent that = (CompositeComponent) obj;
        return type == that.type && children.equals(that.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, children);
    }

    @Override
    public String toString() {
        return "CompositeComponent{" +
                "type=" + type +
                ", childrenCount=" + children.size() +
                ", text='" + reconstruct().replace("\n", "\\n").replace("\t", "\\t") + '\'' +
                '}';
    }
}