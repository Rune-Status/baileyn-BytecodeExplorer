package com.njbailey.bytelib;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.MethodNode;

public class Method extends MethodNode {
    public ClassFile parent;

    public Method(ClassFile parent, int access, String name, String desc, String signature, String[] exceptions) {
        super(Opcodes.ASM5, access, name, desc, signature, exceptions);
        this.parent = parent;
    }
}
