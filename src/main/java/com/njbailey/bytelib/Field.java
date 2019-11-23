package com.njbailey.bytelib;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.FieldNode;

public class Field extends FieldNode {
    public ClassFile parent;

    public Field(ClassFile parent, int access, String name, String desc, String signature, Object value) {
        super(Opcodes.ASM5, access, name, desc, signature, value);
        this.parent = parent;
    }
}
