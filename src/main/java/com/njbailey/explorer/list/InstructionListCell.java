package com.njbailey.explorer.list;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TryCatchBlockNode;

public class InstructionListCell extends ListCell<AbstractInsnNodeWrapper> implements Runnable {
    private static final SecureRandom RANDOM = new SecureRandom();
    private InsnList instructions;
    private List<TryCatchBlockNode> tryCatchBlocks;

    public InstructionListCell(MethodNode methodNode) {
        this.instructions = methodNode.getInstructions();
        this.tryCatchBlocks = methodNode.getTryCatchBlocks();
    }

    @Override
    protected void updateItem(AbstractInsnNodeWrapper item, boolean empty) {
        super.updateItem(item, empty);

        if(empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            AbstractInsnNode insnNode = item.getInsnNode();
            insnNode.updated = this;
            if(insnNode instanceof LabelNode) {
                setText(insnNode.toString() + ":");
            } else {
                setText(insnNode.toString());
                getStyleClass().add("indent");
            }

            setGraphic(null);

            item.highlightedProperty().addListener((a, old, newValue) -> {
                if(newValue) {
                    setGraphic(new Label("T"));
                } else {
                    setGraphic(null);
                }
            });
        }
    }

    @Override
    public void run() {
        updateItem(getItem(), isEmpty());
    }
}
