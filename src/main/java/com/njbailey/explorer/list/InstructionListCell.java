package com.njbailey.explorer.list;

import com.njbailey.bytelib.code.Instruction;
import com.njbailey.bytelib.code.LabelInstruction;
import javafx.scene.control.ListCell;

public class InstructionListCell extends ListCell<Instruction> {
    @Override
    protected void updateItem(Instruction item, boolean empty) {
        super.updateItem(item, empty);

        if(empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            if(item instanceof LabelInstruction) {
                setText(((LabelInstruction) item).getName() + ":");
            } else {
                setText(item.toString());
                getStyleClass().add("indent");
            }

            setGraphic(null);
        }
    }
}
