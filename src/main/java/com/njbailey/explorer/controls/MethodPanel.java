package com.njbailey.explorer.controls;

import com.njbailey.explorer.list.InstructionList;

import org.objectweb.asm.tree.MethodNode;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import lombok.Getter;

public class MethodPanel extends BorderPane {
    @Getter
    private MethodNode methodNode;
    
    ToolBar toolBar = new ToolBar();

    private InstructionList instructionList;
    private MethodInfo methodInfo;

    public MethodPanel(MethodNode methodNode) {
        this.methodNode = methodNode;
    
        setupComponents();
        setupToolbar();
        setDefaultView();
    }

    private void setupComponents() {
        instructionList = new InstructionList(methodNode);
        methodInfo = new MethodInfo(methodNode);
    }

    private void setupToolbar() {
        Button btnInsnList = new Button("Instructions");
        btnInsnList.setOnAction(e -> {
            setCenter(instructionList);
        });

        Button btnMethodInfo = new Button("Method Info");
        btnMethodInfo.setOnAction(e -> {
            setCenter(methodInfo);
        });

        toolBar.getItems().addAll(btnMethodInfo, btnInsnList);
        setTop(toolBar);
    }

    private void setDefaultView() {
        Node node = toolBar.getItems().get(0);
        node.fireEvent(new ActionEvent());
    }
}