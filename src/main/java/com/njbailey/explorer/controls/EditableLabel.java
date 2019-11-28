package com.njbailey.explorer.controls;

import com.njbailey.bytelib.code.Instruction;
import com.njbailey.bytelib.code.LabelInstruction;
import com.njbailey.explorer.Opcodes;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.css.PseudoClass;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class EditableLabel extends StackPane {
    private EditableLabelController controller = new EditableLabelController();

    public EditableLabel() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditableLabel.fxml"));
        loader.setRoot(this);
        loader.setController(controller);

        try {
            loader.load();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
}
