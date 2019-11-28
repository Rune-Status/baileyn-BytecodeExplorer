package com.njbailey.explorer.tree;

import com.njbailey.bytelib.ClassFile;
import javafx.scene.control.Label;

import java.lang.reflect.Modifier;

public class ClassFileTreeItem extends SimpleTreeItem<ClassFile> {
    public ClassFileTreeItem(ClassFile classFile) {
        super(classFile);
        setValue(classFile.getName());

        if(Modifier.isAbstract(classFile.getAccess())) {
            setGraphic(new Label("[A]"));
        } else if(Modifier.isInterface(classFile.getAccess())) {
            setGraphic(new Label("[I]"));
        } else {
            setGraphic(new Label("[C]"));
        }
    }
}
