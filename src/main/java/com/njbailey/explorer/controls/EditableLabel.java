package com.njbailey.explorer.controls;

import javafx.beans.property.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import lombok.Setter;

public class EditableLabel extends VBox {
    private StringProperty text = new SimpleStringProperty();
    private Label label = new Label();
    private TextField textField = new TextField();
    private String previousText = "";
    private boolean savedFromAction = false;

    @Setter
    private Runnable onEditted = null;

    public EditableLabel(String text) {
        this();
        setText(text);
    }

    public EditableLabel() {
        label.textProperty().bindBidirectional(text);
        textField.textProperty().bindBidirectional(text);

        getChildren().add(label);

        label.setOnMouseClicked(e -> {
            if(e.getClickCount() == 2) {
                previousText = getText();
                getChildren().clear();
                getChildren().add(textField);
                textField.requestFocus();
            }
        });

        textField.setOnAction(e -> {
            savedFromAction = true;
            finishEditting();
        });

        textField.focusedProperty().addListener((e, oldValue, newValue) -> {
            if(!newValue && !savedFromAction) {
                finishEditting();
            }

            savedFromAction = false;
        });
    }

    private void editted() {
        if(onEditted != null) {
            onEditted.run();
        }
    }

    public String getText() {
        return text.get();
    }

    public void setText(String text) {
        this.text.set(text);
    }

    public StringProperty textProperty() {
        return text;
    }

    private void finishEditting() {
        getChildren().clear();
        getChildren().add(label);

        if(!previousText.equals(getText())) {
            editted();
        }
    }
}
