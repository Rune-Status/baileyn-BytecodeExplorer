package com.njbailey.explorer.tree;

import com.njbailey.explorer.controls.MethodInfo;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import org.objectweb.asm.tree.MethodNode;

public class MethodTreeItem extends SimpleTreeItem<MethodNode> {
    public MethodTreeItem(MethodNode method) {
        super(method);
        setValue(method.getName());
    }

    @Override
    public ContextMenu getContextMenu() {
        MenuItem menu = new MenuItem("GetInfo");
        menu.setOnAction(e -> {
            MethodInfo methodInfo = new MethodInfo(getData());
            Scene scene = new Scene(methodInfo, 400, 600);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        });

        return new ContextMenu(menu);
    }
}
