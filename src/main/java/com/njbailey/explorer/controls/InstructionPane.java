package com.njbailey.explorer.controls;

import com.njbailey.bytelib.Method;
import com.njbailey.bytelib.OpcodeInfo;
import com.njbailey.bytelib.code.FrameInstruction;
import com.njbailey.bytelib.code.Instruction;
import com.njbailey.bytelib.code.LabelInstruction;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import lombok.Getter;

public class InstructionPane extends ScrollPane {
    @Getter
    private Method method;

    private VBox instructions = new VBox();

    public InstructionPane(Method method) {
        setFitToWidth(true);
        setFitToHeight(true);
        setContent(instructions);

        setMethod(method);
    }

    private void setMethod(Method method) {
        this.method = method;

        for(Instruction instruction : method.getInstructions()) {
            if(instruction instanceof LabelInstruction) {
                addLabelInstruction((LabelInstruction) instruction);
            } else if(instruction instanceof FrameInstruction) {
                addFrameInstruction((FrameInstruction) instruction);
            } else {
                addInstruction(instruction);
            }
        }
    }

    private void addLabelInstruction(LabelInstruction instruction) {
        EditableLabel editableLabel = new EditableLabel(instruction.getName());

        editableLabel.setOnEditted(() -> {
            System.out.println("Finished editting label.");
        });

        this.instructions.getChildren().add(editableLabel);
    }

    private void addFrameInstruction(FrameInstruction instruction) {
        System.out.println("Add FrameInstruction");
        Label label = new Label(instruction.toString());
        label.setStyle("-fx-background-color: #FFC0C0");
        this.instructions.getChildren().add(label);
    }

    private void addInstruction(Instruction instruction) {
        Label label = new Label(instruction.toString());
        label.setTooltip(new Tooltip(OpcodeInfo.OPCODE_DESCRIPTIONS[instruction.getOpcode()]));
        this.instructions.getChildren().add(label);
    }
}
