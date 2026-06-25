package org.example.composite.model.impl;

import org.example.composite.model.ComponentType;
import org.example.composite.model.TextComponent;

import java.util.ArrayList;
import java.util.List;

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
        return children;
    }

    @Override
    public ComponentType getType() {
        return type;
    }

    @Override
    public String reconstruct() {
        if (children.isEmpty()) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        String separator;

        switch (type) {
            case TEXT:
                separator = "\n";
                break;
            case PARAGRAPH:
                separator = "\t";
                break;
            case SENTENCE:
                separator = " ";
                break;
            default:
                separator = "";
                break;
        }

        for (int i = 0; i < children.size(); i++) {
            result.append(children.get(i).reconstruct());
            if (i < children.size() - 1) {
                result.append(separator);
            }
        }
        return result.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        CompositeComponent that = (CompositeComponent) obj;
        if (type != that.type) return false;
        if (children == null) {
            return that.children == null;
        }
        return children.equals(that.children);
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (children != null ? children.hashCode() : 0);
        return result;
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