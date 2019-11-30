package com.njbailey.explorer.tree;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.*;
import lombok.Getter;
import lombok.Setter;

public class SimpleTreeItem<T> extends TreeItem<String> {
    private ContextMenu contextMenu = new ContextMenu();
    private TreeView<String> treeView;

    @Getter
    @Setter
    private T data;

    public SimpleTreeItem(TreeView<String> treeView, T data) {
        this.treeView = treeView;
        this.data = data;

        setValue(data.toString());
        populateContextMenu(contextMenu);
    }

    public final ContextMenu getContextMenu() {
        return contextMenu;
    }

    public final void commitEdit(String value) {
        internalCommitEdit(value);
        getTreeView().setEditable(false);
    }

    protected void internalCommitEdit(String value) {}
    protected void populateContextMenu(ContextMenu contextMenu) {}

    protected TreeView<String> getTreeView() {
        return treeView;
    }

    protected void startEditing() {
        getTreeView().setEditable(true);
        getTreeView().edit(this);
    }

    protected MenuItem createRenameMenu() {
        return createRenameMenu("Rename...");
    }

    protected MenuItem createRenameMenu(String name) {
        MenuItem menuItem = new MenuItem(name);
        menuItem.setOnAction(e -> startEditing());

        return menuItem;
    }
}
