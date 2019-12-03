package com.njbailey.explorer.list;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.Getter;
import lombok.Setter;
import org.objectweb.asm.tree.AbstractInsnNode;

@Getter
@Setter
public class AbstractInsnNodeWrapper {
    private AbstractInsnNode insnNode;
    private BooleanProperty highlighted = new SimpleBooleanProperty(false);

    public AbstractInsnNodeWrapper(AbstractInsnNode insnNode) {
        this.insnNode = insnNode;
    }

    public boolean isHighlighted() {
        return highlighted.get();
    }

    public void setHighlighted(boolean highlighted) {
        this.highlighted.set(highlighted);
    }

    public BooleanProperty highlightedProperty() {
        return highlighted;
    }
}
