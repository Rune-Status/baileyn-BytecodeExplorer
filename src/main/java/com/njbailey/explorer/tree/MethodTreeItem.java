package com.njbailey.explorer.tree;

import com.njbailey.bytelib.Method;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

public class MethodTreeItem extends SimpleTreeItem<Method> {
    public MethodTreeItem(Method method) {
        super(method);
        setValue(method.getName());
    }

    @Override
    public ContextMenu getContextMenu() {
        MenuItem menu = new MenuItem("Example");
        return new ContextMenu(menu);
    }
}
