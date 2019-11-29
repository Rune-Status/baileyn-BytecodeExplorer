package com.njbailey.explorer.tree;

import org.objectweb.asm.tree.FieldNode;

public class FieldTreeItem extends SimpleTreeItem<FieldNode> {
    public FieldTreeItem(FieldNode field) {
        super(field);
        setValue(field.getName());
    }
}
