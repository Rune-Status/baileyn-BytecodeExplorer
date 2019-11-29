package com.njbailey.explorer.controls;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import org.objectweb.asm.tree.MethodNode;

import java.lang.reflect.Modifier;

public class MethodInfo extends GridPane {
    private MethodNode method;

    public MethodInfo(MethodNode method) {
        this.method = method;

        Label nameLabel = new Label("Name:");
        EditableLabel nameEditableLabel = new EditableLabel(method.getName());
        GridPane.setConstraints(nameLabel, 0, 0, 2, 1);
        GridPane.setConstraints(nameEditableLabel, 2, 0, 2, 1);

        CheckBox privateCheckbox = new CheckBox("Private");
        privateCheckbox.setSelected(Modifier.isPrivate(method.getAccess()));

        CheckBox publicCheckbox = new CheckBox("Public");
        publicCheckbox.setSelected(Modifier.isPublic(method.getAccess()));

        CheckBox synchronizedCheckbox = new CheckBox("Synchronized");
        synchronizedCheckbox.setSelected(Modifier.isSynchronized(method.getAccess()));

        CheckBox finalCheckbox = new CheckBox("Final");
        finalCheckbox.setSelected(Modifier.isFinal(method.getAccess()));

        GridPane.setConstraints(privateCheckbox, 0, 1);
        GridPane.setConstraints(publicCheckbox, 1, 1);
        GridPane.setConstraints(synchronizedCheckbox, 2, 1);
        GridPane.setConstraints(finalCheckbox, 3, 1);

        getChildren().addAll(nameLabel, nameEditableLabel, privateCheckbox, publicCheckbox, synchronizedCheckbox, finalCheckbox);
    }
}
