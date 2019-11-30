package com.njbailey.explorer.list;

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

public class InstructionListCell extends ListCell<AbstractInsnNode> implements Runnable {
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final Map<TryCatchBlockNode, Color> COLOR_MAP = new HashMap<>();
    private InsnList instructions;
    private List<TryCatchBlockNode> tryCatchBlocks;

    public InstructionListCell(MethodNode methodNode) {
        this.instructions = methodNode.getInstructions();
        this.tryCatchBlocks = methodNode.getTryCatchBlocks();
    }

    @Override
    protected void updateItem(AbstractInsnNode item, boolean empty) {
        super.updateItem(item, empty);

        if(empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            item.updated = this;
            if(item instanceof LabelNode) {
                setText(item.toString() + ":");
            } else {
                setText(item.toString());
                getStyleClass().add("indent");
            }

            for(TryCatchBlockNode tryCatchBlock : tryCatchBlocks) {
                if(tryCatchBlock.start == item) {
                    setText("try { // " + item.toString());
                    break;
                } else if(tryCatchBlock.handler == item) {
                    setText("} catch(" + tryCatchBlock.type + ") { //" + item.toString());
                }
            }

            setGraphic(null);
        }
    }

    private static Color generateRandomColor() {
        float red = RANDOM.nextFloat();
        float green = RANDOM.nextFloat();
        float blue = RANDOM.nextFloat();

        return new Color(red, green, blue, 1.0);
    }

    @Override
    public void run() {
        updateItem(getItem(), isEmpty());
    }
}
