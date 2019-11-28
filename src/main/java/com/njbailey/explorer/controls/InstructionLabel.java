package com.njbailey.explorer.controls;

import com.njbailey.bytelib.code.Instruction;
import com.njbailey.bytelib.code.LabelInstruction;
import com.njbailey.explorer.Opcodes;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;

public class InstructionLabel extends HBox {
    private static PseudoClass LABEL_PSEUDO_CLASS = PseudoClass.getPseudoClass("label");
    private static PseudoClass UNKNOWN_PSEDUO_CLASS = PseudoClass.getPseudoClass("unknown");

    private InstructionPaneController parent;
    private Instruction instruction;

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

        Node node = getComponent();
        getStyleClass().add("instruction");
        getChildren().add(node);
    }

    @Override
    public String getUserAgentStylesheet() {
        return getClass().getResource("/InstructionLabel.css").toExternalForm();
    }

    private Node getComponent() {
        if(instruction instanceof LabelInstruction) {
            label.set(true);
            LabelInstruction labelNode = (LabelInstruction) instruction;
            return new EditableLabel(labelNode.getName() + ":");
        } else if (instruction.getOpcode() < 0 || instruction.getOpcode() > 255) {
            unknown.set(true);
            return new Label(instruction.getClass().getSimpleName());
        } else {
            Label label = new Label(instruction.toString());
            Tooltip tooltip = new Tooltip(Opcodes.OPCODE_DESCRIPTIONS[instruction.getOpcode()]);
            Tooltip.install(label, tooltip);
            return label;
        }
    }
}
