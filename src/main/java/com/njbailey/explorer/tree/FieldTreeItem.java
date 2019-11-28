package com.njbailey.explorer.tree;

import com.njbailey.bytelib.Field;

public class FieldTreeItem extends SimpleTreeItem<Field> {
    public FieldTreeItem(Field field) {
        super(field);
        setValue(field.getName());
    }
}
