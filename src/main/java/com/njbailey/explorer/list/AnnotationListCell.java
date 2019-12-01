package com.njbailey.explorer.list;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.StringConverter;
import org.objectweb.asm.tree.AnnotationNode;

import java.util.Arrays;

public class AnnotationListCell extends TextFieldListCell<AnnotationNode> {
    private ListView<AnnotationNode> annotationList;

    public AnnotationListCell(ListView<AnnotationNode> annotationList) {
        // TODO: Is it considered hacky to pass the ListView here? I don't know how else to
        //       access it to remove items...
        this.annotationList = annotationList;
    }

    @Override
    public void updateItem(AnnotationNode item, boolean empty) {
        super.updateItem(item, empty);

        if(empty || item == null) {
            setText("");
            setGraphic(null);
        } else {
            StringBuilder data = new StringBuilder(item.desc).append(" ");

            if(item.values != null) {
                if (item.values.size() > 0) {
                    data.append(Arrays.toString(item.values.toArray()));
                }
            }

            setText(data.toString());
            setGraphic(null);
        }
    }
}
