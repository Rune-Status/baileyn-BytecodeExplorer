package com.njbailey.explorer.controls;

import com.njbailey.explorer.list.InstructionList;

import javafx.geometry.Orientation;
import javafx.scene.control.*;
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
        SplitPane infoSplitPane = new SplitPane();
        infoSplitPane.setOrientation(Orientation.VERTICAL);

        infoSplitPane.getItems().addAll(methodInfoPane, tryCatchInfo);

        setLeft(infoSplitPane);
        setCenter(instructionList);
    }
}