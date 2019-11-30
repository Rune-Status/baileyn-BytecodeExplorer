package com.njbailey.explorer.tree;

import javafx.scene.control.Label;
import org.objectweb.asm.tree.ClassNode;

import java.lang.reflect.Modifier;

public class ClassFileTreeItem extends SimpleTreeItem<ClassNode> {
    public ClassFileTreeItem(ClassNode classFile) {
        super(classFile);
        setValue(classFile.getName());
        setEditable(true);

        if(Modifier.isAbstract(classFile.getAccess())) {
            setGraphic(new Label("[A]"));
        } else if(Modifier.isInterface(classFile.getAccess())) {
            setGraphic(new Label("[I]"));
        } else {
            setGraphic(new Label("[C]"));
        }
    }

    @Override
    public void commitEdit(String value) {
        getData().setName(value);
    }
}
