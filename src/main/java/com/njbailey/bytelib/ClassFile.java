package com.njbailey.bytelib;

import lombok.Getter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.List;
import java.util.Optional;

public class ClassFile extends ClassNode {
    @Getter
    private JavaApplication owner;

    public ClassFile(JavaApplication owner) {
        super(Opcodes.ASM5);

        this.owner = owner;
    }

    @SuppressWarnings("unchecked")
    public List<Method> getMethods() {
        return (List<Method>) methods;
    }

    @SuppressWarnings("unchecked")
    public List<Field> getFields() {
        return (List<Field>) fields;
    }

    public Optional<Method> getMethod(String name, String desc) {
        for(Method method : getMethods()) {
            if(method.name.equals(name) && method.desc.equals(desc)) {
                return Optional.of(method);
            }
        }

        return Optional.empty();
    }

    @Override
    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        Field field = new Field(this, access, name, desc, signature, value);
        this.fields.add(field);
        return field;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        Method method = new Method(this, access, name, desc, signature, exceptions);
        this.methods.add(method);
        return method;
    }
}
