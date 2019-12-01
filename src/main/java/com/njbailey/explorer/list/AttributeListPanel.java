package com.njbailey.explorer.list;

import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import org.objectweb.asm.Attribute;

import java.util.List;

public class AttributeListPanel extends VBox {
    private ListView<Attribute> attributeList = new ListView<>();

    public AttributeListPanel(List<Attribute> attributes) {
        if(attributes != null) {
            attributeList.getItems().addAll(attributes);
        }

        getChildren().add(attributeList);
    }

    public boolean isEmpty() {
        return attributeList.getItems().isEmpty();
    }
}
