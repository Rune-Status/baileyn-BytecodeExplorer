package com.njbailey.explorer;

import com.njbailey.bytelib.ClassFile;
import com.njbailey.bytelib.Field;
import com.njbailey.bytelib.JavaApplication;
import com.njbailey.bytelib.Method;
import com.njbailey.explorer.controls.InstructionPane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private TabPane methodTabs;

    @FXML
    private TreeView<ItemWrapper> applicationTree;

    private TreeItem<ItemWrapper> rootItem = new TreeItem<>();

    public void loadApplication(ActionEvent actionEvent) {
        FileChooser chooser = new FileChooser();
        final File file = chooser.showOpenDialog(methodTabs.getScene().getWindow());

        if(file != null) {
            JavaApplication javaApplication = new JavaApplication();

            try {
                javaApplication.load(file.toPath());

                updateApplication(javaApplication);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadDirectory(ActionEvent actionEvent) {
        DirectoryChooser chooser = new DirectoryChooser();
        final File file = chooser.showDialog(methodTabs.getScene().getWindow());

        if(file != null) {
            JavaApplication javaApplication = new JavaApplication();

            try {
                javaApplication.load(file.toPath());

                updateApplication(javaApplication);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        JavaApplication javaApplication = new JavaApplication();

        try {
            javaApplication.load(Paths.get(".", "build", "classes", "java", "main"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        applicationTree.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        applicationTree.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getClickCount() == 2) {
                Object selectedValue = applicationTree.getSelectionModel().getSelectedItem().getValue().getValue();

                if(selectedValue instanceof Method) {
                    selectOrAddMethod((Method) selectedValue);
                }
            }
        });
        applicationTree.setRoot(rootItem);
        applicationTree.setShowRoot(false);
        updateApplication(javaApplication);
    }

    private void selectOrAddMethod(Method method) {
        for(Tab tab : methodTabs.getTabs()) {
            if(tab.getContent() instanceof InstructionPane) {
                InstructionPane instructionPane = (InstructionPane) tab.getContent();

                if(instructionPane.getMethod().equals(method)) {
                    methodTabs.getSelectionModel().select(tab);
                    return;
                }
            }
        }

        Tab tab = new Tab(method.getName(), new InstructionPane(method));
        methodTabs.getTabs().add(tab);
        methodTabs.getSelectionModel().select(tab);
    }

    private void updateApplication(JavaApplication application) {
        TreeItem<ItemWrapper> item = new TreeItem<>(new ItemWrapper(application));
        for(ClassFile classFile : application.getClasses()) {
            TreeItem<ItemWrapper> classItem = new TreeItem<>(new ItemWrapper(classFile));

            TreeItem<ItemWrapper> fieldItems = new TreeItem<>(new ItemWrapper("Fields"));
            for(Field field : classFile.getFields()) {
                TreeItem<ItemWrapper> fieldItem = new TreeItem<>(new ItemWrapper(field));
                fieldItems.getChildren().add(fieldItem);
            }

            TreeItem<ItemWrapper> methodItems = new TreeItem<>(new ItemWrapper("Methods"));
            for(Method method : classFile.getMethods()) {
                TreeItem<ItemWrapper> methodItem = new TreeItem<>(new ItemWrapper(method));
                methodItems.getChildren().add(methodItem);
            }

            if(classFile.isExecutable()) {
                classItem.setGraphic(new Label("E"));
            }

            classItem.getChildren().addAll(fieldItems, methodItems);
            item.getChildren().add(classItem);
        }

        rootItem.getChildren().add(item);
    }

    @Getter
    @RequiredArgsConstructor
    static class ItemWrapper {
        private final Object value;

        @Override
        public String toString() {
            if(value instanceof JavaApplication) {
                return ((JavaApplication) value).getName();
            } else if(value instanceof  Method) {
                return ((Method) value).getName();
            } else if(value instanceof Field) {
                return ((Field) value).getName();
            } else if(value instanceof ClassFile) {
                return ((ClassFile) value).getName();
            }

            return value.toString();
        }
    }
}
