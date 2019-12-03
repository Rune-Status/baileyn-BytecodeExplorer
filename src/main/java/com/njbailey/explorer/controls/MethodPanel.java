package com.njbailey.explorer.controls;

import com.njbailey.explorer.list.InstructionList;

import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.objectweb.asm.tree.MethodNode;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import lombok.Getter;

public class MethodPanel extends BorderPane {
    @Getter
    private MethodNode methodNode;

    private InstructionList instructionList;
    private MethodInfo methodInfo;
    private TryCatchInfo tryCatchInfo;

    private ScrollPane methodInfoPane;

    public MethodPanel(MethodNode methodNode) {
        this.methodNode = methodNode;

        setupComponents();
        setDefaultView();
    }

    private void setupComponents() {
        instructionList = new InstructionList(methodNode);
        methodInfo = new MethodInfo(methodNode);

        methodInfoPane = new ScrollPane(methodInfo);

        tryCatchInfo = new TryCatchInfo(this);
    }

    private void setDefaultView() {
        VBox vbox = new VBox();

        TitledPane methodInfoTitledPane = new TitledPane("Method Information", methodInfoPane);
        TitledPane tryCatchInfoTitledPane = new TitledPane("Try-Catch Information", tryCatchInfo);

        methodInfoTitledPane.setExpanded(false);
        tryCatchInfoTitledPane.setExpanded(false);

        vbox.getChildren().addAll(
                methodInfoTitledPane,
                tryCatchInfoTitledPane
        );

        setLeft(vbox);
        setCenter(instructionList);
    }
}