package com.njbailey.explorer.controls;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import org.objectweb.asm.tree.MethodNode;

import java.lang.reflect.Modifier;

public class MethodInfo extends VBox {
    private MethodNode method;

    public MethodInfo(MethodNode method) {
        this.method = method;
        setPadding(new Insets(15.0));
        setSpacing(10.0);

        HBox hbox = new HBox();
        hbox.setSpacing(15.0);

        Label nameLabel = new Label("Name:");
        Label nameEditableLabel = new Label(method.getName());
        hbox.getChildren().addAll(nameLabel, nameEditableLabel);

        Node modifierInfo = createModifierInfo();
        getChildren().addAll(hbox, modifierInfo);
    }

    private Node createModifierInfo() {
        TitledPane titledPane = new TitledPane();
        titledPane.setText("Modifiers");

        FlowPane flowPane = new FlowPane();
        flowPane.setHgap(15.0);
        flowPane.setVgap(15.0);

        CheckBox privateCheckbox = new CheckBox("Private");
        privateCheckbox.setSelected(Modifier.isPrivate(method.getAccess()));

        CheckBox publicCheckbox = new CheckBox("Public");
        publicCheckbox.setSelected(Modifier.isPublic(method.getAccess()));

        CheckBox synchronizedCheckbox = new CheckBox("Synchronized");
        synchronizedCheckbox.setSelected(Modifier.isSynchronized(method.getAccess()));

        CheckBox finalCheckbox = new CheckBox("Final");
        finalCheckbox.setSelected(Modifier.isFinal(method.getAccess()));

        CheckBox staticCheckbox = new CheckBox("Static");
        staticCheckbox.setSelected(Modifier.isStatic(method.getAccess()));

        flowPane.getChildren().addAll(privateCheckbox, publicCheckbox, synchronizedCheckbox, finalCheckbox, staticCheckbox);
        
        titledPane.setContent(flowPane);

        return titledPane;
    }
}
