package com.njbailey.explorer.list;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.List;

public class AnnotationListPanel extends VBox {
    private ListView<AnnotationNode> annotationListView = new ListView<>();

    public AnnotationListPanel(MethodNode method) {
        Node toolBar = createToolBar();

        annotationListView.setCellFactory(ignored -> new AnnotationListCell(annotationListView));

        if(method.getVisibleAnnotations() != null) {
            annotationListView.getItems().addAll(method.getVisibleAnnotations());
        }

        getChildren().addAll(annotationListView);
    }

    public boolean isEmpty() {
        return annotationListView.getItems().isEmpty();
    }

    private Node createToolBar() {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER_RIGHT);

        Button addButton = new Button("Add New Exception");
        addButton.setOnAction(e -> {
            int index = annotationListView.getItems().size();
            annotationListView.getItems().add(null);
            annotationListView.scrollTo(index);
            annotationListView.layout();
            annotationListView.edit(index);
        });

        hbox.getChildren().addAll(addButton);
        return hbox;
    }
}
