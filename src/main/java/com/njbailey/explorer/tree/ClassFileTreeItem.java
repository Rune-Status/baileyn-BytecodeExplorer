package com.njbailey.explorer.tree;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeView;
import org.objectweb.asm.tree.ClassNode;

import java.lang.reflect.Modifier;

public class ClassFileTreeItem extends SimpleTreeItem<ClassNode> {
    public ClassFileTreeItem(TreeView<String> treeView, ClassNode classFile) {
        super(treeView, classFile);
        setValue(classFile.getName());

        if(Modifier.isAbstract(classFile.getAccess())) {
            setGraphic(new Label("[A]"));
        } else if(Modifier.isInterface(classFile.getAccess())) {
            setGraphic(new Label("[I]"));
        } else {
            setGraphic(new Label("[C]"));
        }
    }

    @Override
    protected void populateContextMenu(ContextMenu contextMenu) {
        contextMenu.getItems().addAll(createRenameMenu());
    }

    @Override
    protected void internalCommitEdit(String value) {
        getData().setName(value);
    }
}
