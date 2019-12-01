package com.njbailey.explorer;

import com.njbailey.explorer.controls.MethodPanel;
import com.njbailey.explorer.list.InstructionList;
import com.njbailey.explorer.tree.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import org.objectweb.asm.tree.ApplicationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

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
            ApplicationNode applicationNode = new ApplicationNode();

            try {
                applicationNode.load(file.toPath());

                updateApplication(applicationNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadDirectory(ActionEvent actionEvent) {
        DirectoryChooser chooser = new DirectoryChooser();
        final File file = chooser.showDialog(methodTabs.getScene().getWindow());

        if(file != null) {
            ApplicationNode applicationNode = new ApplicationNode();

            try {
                applicationNode.load(file.toPath());

                updateApplication(applicationNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ApplicationNode applicationNode = new ApplicationNode();

        try {
            applicationNode.load(Paths.get(".", "build", "classes", "java", "main"));
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

        applicationTree.getSelectionModel().selectedItemProperty().addListener((e, old, selectedValue) -> {
            if(selectedValue instanceof MethodTreeItem) {
                selectOrAddMethod(((MethodTreeItem) selectedValue).getData());
            }
        });

        applicationTree.setOnEditCommit(e -> {
            SimpleTreeItem<?> treeItem = (SimpleTreeItem<?>) e.getTreeItem();
            treeItem.commitEdit(e.getNewValue());
        });

        applicationTree.setRoot(rootItem);
        applicationTree.setShowRoot(false);
        updateApplication(applicationNode);
    }

    private void selectOrAddMethod(MethodNode method) {
        for(Tab tab : methodTabs.getTabs()) {
            if(tab.getContent() instanceof MethodPanel) {
                MethodPanel methodPanel = (MethodPanel) tab.getContent();

                if(methodPanel.getMethodNode().equals(method)) {
                    methodTabs.getSelectionModel().select(tab);
                    return;
                }
            }
        }

        Tab tab = new Tab(method.getName(), new MethodPanel(method));
        methodTabs.getTabs().add(tab);
        methodTabs.getSelectionModel().select(tab);
    }

    private void updateApplication(ApplicationNode application) {
        JavaApplicationTreeItem applicationTreeItem = new JavaApplicationTreeItem(applicationTree, application);
        for(ClassNode classNode : application.getClasses()) {
            SimpleTreeItem<String> fieldItems = new SimpleTreeItem<>(applicationTree, "Fields");
            classNode.getFields().forEach(field -> fieldItems.getChildren().add(new FieldTreeItem(applicationTree, field)));

            SimpleTreeItem<String> methodItems = new SimpleTreeItem<>(applicationTree, "Methods");
            classNode.getMethods().forEach(method -> methodItems.getChildren().add(new MethodTreeItem(applicationTree, method)));

            classNode.getMethods().forEach(method -> {
                if(method.getAttrs() != null) {
                    if(method.getAttrs().size() > 0) {
                        System.out.println(classNode.name + "." + method.getName());
                    }
                }
            });

            ClassFileTreeItem classFileTreeItem = new ClassFileTreeItem(applicationTree, classNode);
            classFileTreeItem.getChildren().add(fieldItems);
            classFileTreeItem.getChildren().add(methodItems);

            applicationTreeItem.getChildren().add(classFileTreeItem);
        }

        rootItem.getChildren().add(applicationTreeItem);
    }
}
