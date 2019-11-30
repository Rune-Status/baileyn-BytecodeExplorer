package com.njbailey.explorer.tree;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeView;
import javafx.stage.FileChooser;
import org.objectweb.asm.tree.ApplicationNode;

import java.io.File;
import java.io.IOException;

public class JavaApplicationTreeItem extends SimpleTreeItem<ApplicationNode> {
    public JavaApplicationTreeItem(TreeView<String> treeView, ApplicationNode application) {
        super(treeView, application);
        setValue(application.getName());
    }

    @Override
    protected void populateContextMenu(ContextMenu contextMenu) {
        MenuItem saveMenu = new MenuItem("Save...");

        saveMenu.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showSaveDialog(null);

            if(file != null) {
                try {
                    getData().save(file.toPath());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        contextMenu.getItems().addAll(saveMenu);
    }
}
