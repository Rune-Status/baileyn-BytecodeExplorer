package com.njbailey.explorer;

import com.njbailey.bytelib.ClassFile;
import com.njbailey.bytelib.JavaApplication;
import com.njbailey.bytelib.Method;
import com.njbailey.explorer.list.InstructionList;
import com.njbailey.explorer.tree.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private TabPane methodTabs;

    @FXML
    private TreeView<String> applicationTree;

    private TreeItem<String> rootItem = new TreeItem<>();

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

        methodTabs.focusedProperty().addListener((e, old, n) -> {
            if(n) {
                methodTabs.getSelectionModel().selectedItemProperty().get().getContent().requestFocus();
            }
        });

        applicationTree.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        applicationTree.setCellFactory(e -> new TreeCellImpl());
        applicationTree.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getClickCount() == 2) {
                Object selectedValue = applicationTree.getSelectionModel().getSelectedItem();

                if(selectedValue instanceof MethodTreeItem) {
                    selectOrAddMethod(((MethodTreeItem) selectedValue).getData());
                }
            }
        });

        applicationTree.setRoot(rootItem);
        applicationTree.setShowRoot(false);
        updateApplication(javaApplication);
    }

    private void selectOrAddMethod(Method method) {
        for(Tab tab : methodTabs.getTabs()) {
            if(tab.getContent() instanceof InstructionList) {
                InstructionList instructionList = (InstructionList) tab.getContent();

                if(instructionList.getMethod().equals(method)) {
                    methodTabs.getSelectionModel().select(tab);
                    return;
                }
            }
        }

        Tab tab = new Tab(method.getName(), new InstructionList(method));
        methodTabs.getTabs().add(tab);
        methodTabs.getSelectionModel().select(tab);
    }

    private void updateApplication(JavaApplication application) {
        JavaApplicationTreeItem applicationTreeItem = new JavaApplicationTreeItem(application);
        for(ClassFile classFile : application.getClasses()) {
            SimpleTreeItem<String> fieldItems = new SimpleTreeItem<>("Fields");
            classFile.getFields().forEach(field -> fieldItems.getChildren().add(new FieldTreeItem(field)));

            SimpleTreeItem<String> methodItems = new SimpleTreeItem<>("Methods");
            classFile.getMethods().forEach(method -> methodItems.getChildren().add(new MethodTreeItem(method)));

            ClassFileTreeItem classFileTreeItem = new ClassFileTreeItem(classFile);
            classFileTreeItem.getChildren().add(fieldItems);
            classFileTreeItem.getChildren().add(methodItems);

            applicationTreeItem.getChildren().add(classFileTreeItem);
        }

        rootItem.getChildren().add(applicationTreeItem);
    }
}
