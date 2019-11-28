package com.njbailey.explorer.tree;

import com.njbailey.bytelib.ClassFile;

public class ClassFileTreeItem extends SimpleTreeItem<ClassFile> {
    public ClassFileTreeItem(ClassFile classFile) {
        super(classFile);
        setValue(classFile.getName());
    }
}
