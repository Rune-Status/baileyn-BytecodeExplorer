package com.njbailey.explorer.list;

import javafx.scene.control.ListCell;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.LabelNode;

public class InstructionListCell extends ListCell<AbstractInsnNode> {
    @Override
    protected void updateItem(AbstractInsnNode item, boolean empty) {
        super.updateItem(item, empty);

        if(empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            if(item instanceof LabelNode) {
                setText(item.toString() + ":");
            } else {
                setText(item.toString());
                getStyleClass().add("indent");
            }

            setGraphic(null);
        }
    }
}
