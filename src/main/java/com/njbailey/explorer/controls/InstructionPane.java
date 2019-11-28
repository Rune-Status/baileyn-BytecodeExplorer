package com.njbailey.explorer.controls;

import com.njbailey.bytelib.Method;
import com.njbailey.bytelib.OpcodeInfo;
import com.njbailey.bytelib.code.FrameInstruction;
import com.njbailey.bytelib.code.Instruction;
import com.njbailey.bytelib.code.LabelInstruction;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import lombok.Getter;

import java.util.Optional;

public class InstructionPane extends ScrollPane {
    @Getter
    private Method method;

    private VBox instructions = new VBox();

    public InstructionPane(Method method) {
        setFitToWidth(true);
        setFitToHeight(true);

        instructions.getStyleClass().add("instructions");

        setContent(instructions);

        setMethod(method);
    }

    private void setMethod(Method method) {
        this.method = method;

        boolean salt = true;
        for(Instruction instruction : method.getInstructions()) {
            Node instructionNode;

            if(instruction instanceof LabelInstruction) {
                instructionNode = getLabelInstruction((LabelInstruction) instruction);
            } else if(instruction instanceof FrameInstruction) {
                instructionNode = getFrameInstruction((FrameInstruction) instruction);
            } else {
                instructionNode = getInstruction(instruction);
            }

            instructionNode.getStyleClass().add("instruction");
            if(salt) {
                instructionNode.getStyleClass().add("salt");
            } else {
                instructionNode.getStyleClass().add("pepper");
            }

            if(instructionNode instanceof Control) {
                ((Control) instructionNode).prefWidthProperty().bind(instructions.widthProperty());
            }
            instructions.getChildren().add(instructionNode);
            salt = !salt;
        }
    }

    private Node getLabelInstruction(LabelInstruction instruction) {
        EditableLabel editableLabel = new EditableLabel(instruction.getName());

        editableLabel.setOnEditted(() -> {
            System.out.println("Finished editting label.");
        });

        return editableLabel;
    }

    private Node getFrameInstruction(FrameInstruction instruction) {
        Label label = new Label(instruction.toString());
        label.getStyleClass().add("instruction-error");

        return label;
    }

    private Node getInstruction(Instruction instruction) {
        Label label = new Label(instruction.toString());
        label.getStyleClass().add("indent");
        label.setTooltip(new Tooltip(OpcodeInfo.OPCODE_DESCRIPTIONS[instruction.getOpcode()]));

        return label;
    }
}
