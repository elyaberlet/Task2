package org.example.composite.model;

import java.util.List;

public interface TextComponent {
    void addChild(TextComponent component);
    List<TextComponent> getChildren();
    String reconstruct();
    ComponentType getType();
}
