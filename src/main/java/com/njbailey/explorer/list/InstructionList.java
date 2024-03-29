package com.njbailey.explorer.list;

import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import lombok.Getter;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodNode;

public class InstructionList extends ListView<AbstractInsnNode> {
    @Getter
    private MethodNode method;

    public InstructionList(MethodNode method) {
        this();
        setMethod(method);
    }

    public InstructionList() {
        setCellFactory(ignored -> new InstructionListCell(method));
        setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER) {
                AbstractInsnNode selectedNode = getSelectionModel().getSelectedItem();

                if(selectedNode instanceof JumpInsnNode) {
                    JumpInsnNode jumpInsnNode = (JumpInsnNode) selectedNode;
                    getSelectionModel().select(method.getInstructions().getNextExecutableInstruction(jumpInsnNode.label));
                }
            }
        });
    }

    public void setMethod(MethodNode method) {
        this.method = method;

        getItems().clear();
        for(AbstractInsnNode instruction : method.getInstructions()) {
            if(instruction instanceof LabelNode || instruction.getOpcode() >= 0) {
                getItems().add(instruction);
            }
        }
    }
}
