package com.njbailey.explorer.controls;

import com.njbailey.bytelib.code.Instruction;
import com.njbailey.bytelib.code.LabelInstruction;
import com.njbailey.explorer.Opcodes;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

public class InstructionLabel extends Label {
    private InstructionPaneController parent;
    private Instruction insnNode;

    public InstructionLabel(InstructionPaneController parent, Instruction instruction) {
        this.parent = parent;
        this.insnNode = instruction;

        if(instruction instanceof LabelInstruction) {
            LabelInstruction labelNode = (LabelInstruction) instruction;
            setText(labelNode.getName() + ":");
            setStyle("-fx-background-color: lightblue;");
        } else {
            if (instruction.getOpcode() < 0 || instruction.getOpcode() > 255) {
                setText(instruction.getClass().getSimpleName());
                setStyle("-fx-background-color: red;");
            } else {
                setText(instruction.toString());
                setStyle("-fx-padding: 0 0 0 20px");
                Tooltip tooltip = new Tooltip(Opcodes.OPCODE_DESCRIPTIONS[instruction.getOpcode()]);
                Tooltip.install(this, tooltip);
            }
        }
    }
}
