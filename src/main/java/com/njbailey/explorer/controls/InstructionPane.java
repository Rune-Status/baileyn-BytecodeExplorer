package com.njbailey.explorer.controls;

import com.njbailey.bytelib.Method;
import com.njbailey.bytelib.OpcodeInfo;
import com.njbailey.bytelib.code.FrameInstruction;
import com.njbailey.bytelib.code.Instruction;
import com.njbailey.bytelib.code.LabelInstruction;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;

import java.util.Optional;

public class InstructionPane extends ScrollPane {
    @Getter
    private Method method;

    private VBox lineNumbers = new VBox();
    private VBox instructions = new VBox();

    public InstructionPane(Method method) {
        setFitToWidth(true);
        setFitToHeight(true);

        lineNumbers.getStyleClass().add("line-numbers");
        instructions.getStyleClass().add("instructions");

        HBox contents = new HBox();
        contents.getChildren().addAll(lineNumbers, instructions);
        setContent(contents);

        setMethod(method);
    }

    private void setMethod(Method method) {
        this.method = method;

        for(Instruction instruction : method.getInstructions()) {
            Node node;
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

        HBox hbox = new HBox();
        Label lineNumberLabel = new Label();
        lineNumberLabel.getStyleClass().add("line-number-label");

        Optional<Integer> lineNumber = method.getLineNumbers().get(instruction);

        lineNumber.ifPresent(integer -> lineNumberLabel.setText(integer.toString()));

        lineNumbers.getChildren().add(lineNumberLabel);
        instructions.getChildren().add(editableLabel);
    }

    private void addFrameInstruction(FrameInstruction instruction) {
        Label label = new Label(instruction.toString());
        label.setStyle("-fx-background-color: #FFC0C0");

        lineNumbers.getChildren().add(new Label());
        instructions.getChildren().add(label);
    }

    private void addInstruction(Instruction instruction) {
        Label label = new Label(instruction.toString());
        label.getStyleClass().add("indent");
        label.setTooltip(new Tooltip(OpcodeInfo.OPCODE_DESCRIPTIONS[instruction.getOpcode()]));

        lineNumbers.getChildren().add(new Label());
        instructions.getChildren().add(label);
    }
}
