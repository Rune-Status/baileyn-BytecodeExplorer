package com.njbailey.explorer.tree;

import javafx.scene.control.TreeCell;

public class TreeCellImpl extends TreeCell<String> {
    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if(empty) {
            setText(null);
            setGraphic(null);
        } else {
            setText(getItem() == null ? "" : getItem());
            setGraphic(getTreeItem().getGraphic());
            setContextMenu(((SimpleTreeItem) getTreeItem()).getContextMenu());
        }
    }
}
