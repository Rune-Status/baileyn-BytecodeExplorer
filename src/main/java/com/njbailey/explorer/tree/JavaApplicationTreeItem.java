package com.njbailey.explorer.tree;

import com.njbailey.bytelib.JavaApplication;

public class JavaApplicationTreeItem extends SimpleTreeItem<JavaApplication> {
    public JavaApplicationTreeItem(JavaApplication application) {
        super(application);
        setValue(application.getName());
    }
}
