package com.njbailey.explorer.tree;

import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;

public class TreeCellImpl extends TextFieldTreeCell<String> {
    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        SimpleTreeItem treeItem = (SimpleTreeItem) getTreeItem();

        if(empty) {
            setText(null);
            setGraphic(null);
        } else {
            setText(getItem() == null ? "" : getItem());
            setGraphic(getTreeItem().getGraphic());
            setContextMenu(treeItem.getContextMenu());
            setEditable(treeItem.isEditable());

            setConverter(new DefaultStringConverter());
        }
    }
}
