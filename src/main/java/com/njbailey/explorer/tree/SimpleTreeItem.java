package com.njbailey.explorer.tree;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TreeItem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimpleTreeItem<T> extends TreeItem<String> {
    private T data;
    private boolean editable = false;

    public SimpleTreeItem(T data) {
        this.data = data;
        setValue(data.toString());
    }

    public ContextMenu getContextMenu() {
        return null;
    }

    protected void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean isEditable() {
        return editable;
    }

    public void commitEdit(String value) {

    }
}
