package com.njbailey.explorer.controls;

import com.njbailey.bytelib.JavaApplication;
import com.njbailey.bytelib.Method;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import lombok.Getter;

import java.io.IOException;

public class InstructionPane extends VBox {
    private InstructionPaneController controller;

    @Getter
    private Method method;

    public InstructionPane(Method method) {
        controller = new InstructionPaneController();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/InstructionPane.fxml"));
        loader.setRoot(this);
        loader.setController(controller);

        try {
            loader.load();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }

        setMethod(method);
    }

    @Override
    public String getUserAgentStylesheet() {
        return getClass().getResource("/InstructionPane.css").toExternalForm();
    }

    public void setMethod(Method method) {
        this.method = method;
        controller.updateInstructions(method);
    }
}
