package com.njbailey.explorer.tree;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.TreeView;
import org.objectweb.asm.tree.MethodNode;

public class MethodTreeItem extends SimpleTreeItem<MethodNode> {
    public MethodTreeItem(TreeView<String> treeView, MethodNode method) {
        super(treeView, method);
        setValue(method.getName());
    }

    @Override
    public void internalCommitEdit(String value) {
        getData().setName(value);
    }

    @Override
    protected void populateContextMenu(ContextMenu contextMenu) {
        contextMenu.getItems().addAll(createRenameMenu());
    }
}
