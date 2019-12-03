package com.njbailey.explorer.controls;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.objectweb.asm.tree.TryCatchBlockNode;

public class TryCatchInfo extends TableView<TryCatchBlockNode> {
    private MethodPanel methodPanel;

    public TryCatchInfo(MethodPanel methodPanel) {
        this.methodPanel = methodPanel;

        setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        initializeComponents();
        populateTryCatchNodes();
    }

    @SuppressWarnings("unchecked")
    private void initializeComponents() {
        TableColumn<TryCatchBlockNode, String> typeColumn = new TableColumn<>("Type");
        TableColumn<TryCatchBlockNode, String> startColumn = new TableColumn<>("Start");
        TableColumn<TryCatchBlockNode, String> endColumn = new TableColumn<>("End");
        TableColumn<TryCatchBlockNode, String> handlerColumn = new TableColumn<>("Handler");

        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        startColumn.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getStart().toString()));
        endColumn.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getEnd().toString()));
        handlerColumn.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getHandler().toString()));

        getColumns().addAll(typeColumn, startColumn, endColumn, handlerColumn);
    }

    private void populateTryCatchNodes() {
        for(TryCatchBlockNode tryCatchBlockNode : methodPanel.getMethodNode().getTryCatchBlocks()) {
            getItems().add(tryCatchBlockNode);
        }
    }
}
