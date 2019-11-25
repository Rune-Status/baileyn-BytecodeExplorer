package com.njbailey.explorer.controls;

import com.njbailey.bytelib.code.Instruction;
import com.njbailey.bytelib.code.LabelInstruction;
import com.njbailey.explorer.Opcodes;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.css.PseudoClass;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;

public class InstructionLabel extends StackPane {
    private static PseudoClass LABEL_PSEUDO_CLASS = PseudoClass.getPseudoClass("label");
    private static PseudoClass UNKNOWN_PSEDUO_CLASS = PseudoClass.getPseudoClass("unknown");

    private InstructionPaneController parent;
    private Instruction instruction;

    private Label lblText = new Label();

    private BooleanProperty label = new BooleanPropertyBase(false) {
        public void invalidated() {
            pseudoClassStateChanged(LABEL_PSEUDO_CLASS, get());
        }

        @Override
        public Object getBean() {
            return InstructionLabel.this;
        }

        @Override
        public String getName() {
            return "label";
        }
    };

    private BooleanProperty unknown = new BooleanPropertyBase(false) {
        public void invalidated() {
            pseudoClassStateChanged(UNKNOWN_PSEDUO_CLASS, get());
        }

        @Override
        public Object getBean() {
            return InstructionLabel.this;
        }

        @Override
        public String getName() {
            return "unknown";
        }
    };

    public InstructionLabel(InstructionPaneController parent, Instruction instruction) {
        this.parent = parent;
        this.instruction = instruction;

        updateLabel();

        getChildren().add(lblText);
        getStyleClass().add("instruction");
    }

    @Override
    public String getUserAgentStylesheet() {
        return getClass().getResource("/InstructionLabel.css").toExternalForm();
    }

    private void updateLabel() {
        if(instruction instanceof LabelInstruction) {
            LabelInstruction labelNode = (LabelInstruction) instruction;
            lblText.setText(labelNode.getName() + ":");
            label.set(true);

            // Only label instructions can be double clicked for now.
            setOnMouseClicked(e -> {
                LabelInstruction labelInstruction = (LabelInstruction) instruction;
                getChildren().clear();

                TextField textField = new TextField(labelInstruction.getName());
                getChildren().add(textField);

                textField.setOnAction(ev -> {
                    getChildren().clear();
                    labelInstruction.setName(textField.getText());
                    lblText.setText(labelInstruction.getName() + ":");
                    getChildren().add(lblText);
                });
            });
        } else if (instruction.getOpcode() < 0 || instruction.getOpcode() > 255) {
            lblText.setText(instruction.getClass().getSimpleName());
            unknown.set(true);
        } else {
            lblText.setText(instruction.toString());
            Tooltip tooltip = new Tooltip(Opcodes.OPCODE_DESCRIPTIONS[instruction.getOpcode()]);
            Tooltip.install(this, tooltip);
        }

        StackPane.setAlignment(lblText, Pos.CENTER_LEFT);
    }
}
