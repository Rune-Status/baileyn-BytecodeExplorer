package com.njbailey.explorer.controls;

import com.njbailey.explorer.Opcodes;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LineNumberNode;

public class InstructionLabel extends Label {
    private InstructionPaneController parent;
    private AbstractInsnNode insnNode;

    public InstructionLabel(InstructionPaneController parent, AbstractInsnNode insnNode) {
        this.parent = parent;
        this.insnNode = insnNode;

        if(insnNode instanceof LabelNode) {
            LabelNode labelNode = (LabelNode) insnNode;
            setText("Label: " + labelNode.getLabel().toString());
            setStyle("-fx-background-color: lightblue;");
        } else {
            if (insnNode.getOpcode() < 0 || insnNode.getOpcode() > 255) {
                setText(insnNode.getClass().getSimpleName());
                setStyle("-fx-background-color: red;");
            } else {
                setText(Opcodes.OPCODE_NAMES[insnNode.getOpcode()]);
                setStyle("-fx-padding: 0 0 0 20px");
                Tooltip tooltip = new Tooltip(Opcodes.OPCODE_DESCRIPTIONS[insnNode.getOpcode()]);
                Tooltip.install(this, tooltip);
            }
        }
    }
}
