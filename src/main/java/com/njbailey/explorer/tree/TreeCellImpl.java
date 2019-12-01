package com.njbailey.explorer.tree;

import javafx.scene.control.cell.TextFieldTreeCell;
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
            setConverter(new DefaultStringConverter());

            if(treeItem instanceof MethodTreeItem) {
                MethodTreeItem methodTreeItem = (MethodTreeItem) treeItem;

                if(methodTreeItem.getData().isDeprecated()) {
                    getStyleClass().add("deprecated-text");
                }
            }
        }
    }
}
