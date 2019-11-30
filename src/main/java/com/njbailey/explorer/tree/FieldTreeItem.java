package com.njbailey.explorer.tree;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.TreeView;
import org.objectweb.asm.tree.FieldNode;

public class FieldTreeItem extends SimpleTreeItem<FieldNode> {
    public FieldTreeItem(TreeView<String> treeView, FieldNode field) {
        super(treeView, field);
        setValue(field.getName());
    }

    @Override
    protected void populateContextMenu(ContextMenu contextMenu) {
        contextMenu.getItems().addAll(createRenameMenu());
    }

    @Override
    protected void internalCommitEdit(String value) {
        getData().setName(value);
    }
}
