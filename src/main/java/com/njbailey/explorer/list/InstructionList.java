package com.njbailey.explorer.list;

import javafx.scene.control.ListView;
import lombok.Getter;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class InstructionList extends ListView<AbstractInsnNode> {
    @Getter
    private MethodNode method;

    public InstructionList(MethodNode method) {
        this();
        setMethod(method);
    }

    public InstructionList() {
        setCellFactory(ignored -> new InstructionListCell());
    }

    public void setMethod(MethodNode method) {
        this.method = method;

        for(AbstractInsnNode instruction : method.getInstructions()) {
            getItems().add(instruction);
        }
    }
}
