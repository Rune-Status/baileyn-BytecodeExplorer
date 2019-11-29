package com.njbailey.explorer.tree;

import org.objectweb.asm.tree.ApplicationNode;

public class JavaApplicationTreeItem extends SimpleTreeItem<ApplicationNode> {
    public JavaApplicationTreeItem(ApplicationNode application) {
        super(application);
        setValue(application.getName());
    }
}
