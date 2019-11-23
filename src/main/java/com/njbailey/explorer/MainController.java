package com.njbailey.explorer;

import com.njbailey.bytelib.ClassFile;
import com.njbailey.bytelib.JavaApplication;
import com.njbailey.bytelib.Method;
import com.njbailey.explorer.controls.InstructionPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MainController {
    @FXML
    private InstructionPane instructionPane;


    public void loadApplication(ActionEvent actionEvent) {
        FileChooser chooser = new FileChooser();

        final File file = chooser.showOpenDialog(instructionPane.getScene().getWindow());

        if(file != null) {
            JavaApplication javaApplication = new JavaApplication();

            try {
                javaApplication.load(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }

            List<ClassFile> classes = javaApplication.getClasses();
            ClassFile first = classes.get(1);
            System.out.println("Class File: " + first.name);
            Method firstMethod = (Method) first.methods.get(0);
            System.out.println("Method: " + firstMethod.name);
            instructionPane.setApplication(firstMethod);
        }
    }
}
