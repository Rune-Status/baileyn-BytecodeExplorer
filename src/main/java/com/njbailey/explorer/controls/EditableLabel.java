package com.njbailey.explorer.controls;

import com.njbailey.bytelib.code.Instruction;
import com.njbailey.bytelib.code.LabelInstruction;
import com.njbailey.explorer.Opcodes;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.css.PseudoClass;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

public class EditableLabel extends StackPane {
    private StringProperty text = new SimpleStringProperty();
    private Label label = new Label();
    private TextField textField = new TextField();

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
                getChildren().clear();
                getChildren().add(textField);
                textField.requestFocus();
            }
        });

        textField.setOnAction(e -> {
            finishEditting();
        });

        textField.focusedProperty().addListener((e, oldValue, newValue) -> {
            if(!newValue) {
                finishEditting();
            }
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
        editted();
    }
}
