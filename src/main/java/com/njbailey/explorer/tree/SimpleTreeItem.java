package com.njbailey.explorer.tree;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.TreeItem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimpleTreeItem<T> extends TreeItem<String> {
    private T data;

    public SimpleTreeItem(T data) {
        this.data = data;
        setValue(data.toString());
    }

    public ContextMenu getContextMenu() {
        return null;
    }
}
