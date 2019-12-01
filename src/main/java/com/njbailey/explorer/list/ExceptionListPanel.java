package com.njbailey.explorer.list;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class ExceptionListPanel extends VBox {
    private ListView<String> exceptionList = new ListView<>();

    public ExceptionListPanel(List<String> exceptions) {
        Node toolBar = createToolBar();

        exceptionList.setEditable(true);
        exceptionList.setCellFactory(ignored -> new ExceptionListCell(exceptionList));
        exceptionList.getItems().addAll(exceptions);

        getChildren().addAll(exceptionList, toolBar);
    }

    public boolean isEmpty() {
        return exceptionList.getItems().isEmpty();
    }

    private Node createToolBar() {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER_RIGHT);

        Button addButton = new Button("Add New Exception");
        addButton.setOnAction(e -> {
            int index = exceptionList.getItems().size();
            exceptionList.getItems().add("");
            exceptionList.scrollTo(index);
            exceptionList.layout();
            exceptionList.edit(index);
        });

        hbox.getChildren().addAll(addButton);
        return hbox;
    }
}
