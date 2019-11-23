package com.njbailey.explorer.controls;

import com.njbailey.bytelib.JavaApplication;
import com.njbailey.bytelib.Method;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class InstructionPane extends VBox {
    private InstructionPaneController controller;

    public InstructionPane() {
        controller = new InstructionPaneController();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/InstructionPane.fxml"));
        loader.setRoot(this);
        loader.setController(controller);

        try {
            loader.load();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getUserAgentStylesheet() {
        return getClass().getResource("/InstructionPane.css").toExternalForm();
    }

    public void setApplication(Method method) {
        controller.updateInstructions(method);
    }
}
