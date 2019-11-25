package com.njbailey.explorer.controls;

import com.njbailey.bytelib.Method;
import com.njbailey.bytelib.code.Instruction;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class InstructionPaneController {
    @FXML
    private VBox instructions;

    private Method method;

    public void updateInstructions(Method method) {
        this.method = method;

        for(Instruction instruction : method.getInstructions()) {
            instructions.getChildren().add(new InstructionLabel(this, instruction));
        }
    }
}
