package com.njbailey.explorer.list;

import javafx.scene.control.ListCell;
import org.objectweb.asm.Attribute;

public class AttributeListCell extends ListCell<Attribute> {
    @Override
    protected void updateItem(Attribute item, boolean empty) {
        if(empty || item == null) {
            setText("");
            setGraphic(null);
        } else {
            setText(item.type);
            setGraphic(null);
        }
    }
}
