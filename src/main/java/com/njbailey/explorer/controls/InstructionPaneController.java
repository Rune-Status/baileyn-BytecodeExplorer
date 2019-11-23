package com.njbailey.explorer.controls;

import com.njbailey.bytelib.Method;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LineNumberNode;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class InstructionPaneController {
    @FXML
    private VBox instructions;

    private Method method;

    public Optional<AbstractInsnNode> getInstructionFromLabel(LabelNode labelNode) {
        int idx = method.instructions.indexOf(labelNode);

        if(idx < 0) {
            return Optional.empty();
        }

        return Optional.of(method.instructions.get(idx).getNext());
    }

    public void updateInstructions(Method method) {
        this.method = method;

        InsnList insnList = method.instructions;
        AbstractInsnNode cur = insnList.getFirst();

        while(cur != null) {
            if(!(cur instanceof LineNumberNode)) {
                instructions.getChildren().add(new InstructionLabel(this, cur));
            }

            cur = cur.getNext();
        }
    }
}
