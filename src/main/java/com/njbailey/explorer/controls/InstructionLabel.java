package com.njbailey.explorer.controls;

import com.njbailey.bytelib.code.Instruction;
import com.njbailey.bytelib.code.LabelInstruction;
import com.njbailey.explorer.Opcodes;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.css.PseudoClass;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class InstructionLabel extends HBox {
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
            // TODO: All of this feels a little hacky.
            setOnMouseClicked(e -> {
                LabelInstruction labelInstruction = (LabelInstruction) instruction;
                getChildren().clear();

                TextField textField = new TextField(labelInstruction.getName());
                Platform.runLater(textField::requestFocus);
                getChildren().add(textField);

                textField.setOnAction(ev -> {
                    String text = textField.getText();
                    if(text.equalsIgnoreCase(labelInstruction.getName()) || labelInstruction.getParent().isLabelAvailable(text)) {
                        InstructionLabel.this.getChildren().clear();
                        labelInstruction.setName(text);
                        lblText.setText(text + ":");
                        InstructionLabel.this.getChildren().add(lblText);
                    } else {
                        if(getChildren().size() == 1) {
                            Label errorLabel = new Label("Label name already exists.");
                            errorLabel.getStyleClass().add("error-label");
                            InstructionLabel.this.getChildren().add(errorLabel);
                        }
                    }
                });

                textField.focusedProperty().addListener((b, oldValue, newValue) -> {
                    if(!newValue) {
                        InstructionLabel.this.getChildren().clear();

                        String text = textField.getText();
                        if(text.equalsIgnoreCase(labelInstruction.getName()) || labelInstruction.getParent().isLabelAvailable(text)) {
                            labelInstruction.setName(text);
                            lblText.setText(text + ":");
                        }

                        InstructionLabel.this.getChildren().add(lblText);
                    }
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
