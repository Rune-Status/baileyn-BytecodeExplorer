package com.njbailey.explorer.controls;

import com.njbailey.explorer.list.AnnotationListPanel;
import com.njbailey.explorer.list.AttributeListPanel;
import com.njbailey.explorer.list.ExceptionListPanel;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.MethodNode;

import java.lang.reflect.Modifier;
import java.util.Arrays;

public class MethodInfo extends VBox {
    private MethodNode method;

    public MethodInfo(MethodNode method) {
        this.method = method;
        setPadding(new Insets(15.0));
        setSpacing(10.0);

        Node basicInfo = createBasicInfo();
        Node exceptionList = createThrownExceptionInfo();
        Node visibleAnnotationList = createVisibleAnnotationInfo();
        Node invisibleAnnotationList = createInvisibleAnnotationInfo();
        Node attributeList = createAttributeInfo();

        getChildren().addAll(
                basicInfo,
                exceptionList,
                visibleAnnotationList,
                invisibleAnnotationList,
                attributeList
        );
    }

    private Node createBasicInfo() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(15.0);
        gridPane.setVgap(15.0);

        Label nameLabel = new Label("Name:");
        Label nameValueLabel = new Label(method.getName());

        if(method.isDeprecated()) {
            nameValueLabel.getStyleClass().add("deprecated-text");
        }

        GridPane.setConstraints(nameLabel, 0, 0);
        GridPane.setConstraints(nameValueLabel, 1, 0);

        String descriptor = method.getDesc();
        Type returnType = Type.getReturnType(descriptor);
        Type[] argumentTypes = Type.getArgumentTypes(descriptor);

        Label descriptorLabel = new Label("Descriptor:");
        EditableLabel descriptorValueLabel = new EditableLabel(descriptor);
        Label descriptorErrorLabel = new Label();
        descriptorErrorLabel.getStyleClass().add("error-text");

        GridPane.setConstraints(descriptorLabel, 0, 1);
        GridPane.setConstraints(descriptorValueLabel, 1, 1);
        GridPane.setConstraints(descriptorErrorLabel, 2, 1);

        Label returnLabel = new Label("Return Type:");
        Label returnValueLabel = new Label(returnType.getClassName());
        GridPane.setConstraints(returnLabel, 1, 2);
        GridPane.setConstraints(returnValueLabel, 2, 2);

        Label argumentTypesLabel = new Label("Argument Types:");
        Label argumentTypesValueLabel = new Label();

        if(argumentTypes.length == 0) {
            argumentTypesValueLabel.setText("None");
        } else {
            argumentTypesValueLabel.setText(Arrays.toString(getTypeNames(argumentTypes)));
        }

        descriptorValueLabel.setOnEditted(() -> {
            try {
                String newDescriptor = descriptorValueLabel.getText();
                Type newReturnType = Type.getReturnType(newDescriptor);
                Type[] newArgumentTypes = Type.getArgumentTypes(newDescriptor);

                returnValueLabel.setText(newReturnType.getClassName());

                if (argumentTypes.length == 0) {
                    argumentTypesValueLabel.setText("None");
                } else {
                    argumentTypesValueLabel.setText(Arrays.toString(getTypeNames(newArgumentTypes)));
                }

                descriptorErrorLabel.setText("");

                // TODO: Implement descriptor updating.
                // method.setDescriptor(newDescriptor);
            } catch(Exception ignored) {
                descriptorErrorLabel.setText("Invalid descriptor format.");
            }
        });

        GridPane.setConstraints(argumentTypesLabel, 1, 3);
        GridPane.setConstraints(argumentTypesValueLabel, 2, 3);

        Label modifierLabel = new Label("Modifiers:");
        Node modifierGrid = createModifierInfo();

        GridPane.setConstraints(modifierLabel, 0, 4);
        GridPane.setConstraints(modifierGrid, 1, 5);
        GridPane.setColumnSpan(modifierGrid, 2);

        gridPane.getChildren().addAll(
                nameLabel, nameValueLabel,
                descriptorLabel, descriptorValueLabel, descriptorErrorLabel,
                returnLabel, returnValueLabel,
                argumentTypesLabel, argumentTypesValueLabel,
                modifierLabel,
                modifierGrid
        );

        return new TitledPane("Basic Information", gridPane);
    }

    private Node createThrownExceptionInfo() {
        ExceptionListPanel exceptionListPanel = new ExceptionListPanel(method.getExceptions());

        TitledPane titledPane = new TitledPane("Exceptions", exceptionListPanel);

        if(exceptionListPanel.isEmpty()) {
            titledPane.setExpanded(false);
        }

        return titledPane;
    }

    private Node createVisibleAnnotationInfo() {
        AnnotationListPanel annotationListPanel = new AnnotationListPanel(method.getVisibleAnnotations(), true);

        TitledPane titledPane = new TitledPane("Visible Annotations", annotationListPanel);

        if(annotationListPanel.isEmpty()) {
            titledPane.setExpanded(false);
        }

        return titledPane;
    }

    private Node createInvisibleAnnotationInfo() {
        AnnotationListPanel annotationListPanel = new AnnotationListPanel(method.getInvisibleAnnotations(), false);

        TitledPane titledPane = new TitledPane("Invisible Annotations", annotationListPanel);

        if(annotationListPanel.isEmpty()) {
            titledPane.setExpanded(false);
        }

        return titledPane;
    }

    private Node createModifierInfo() {
        GridPane gridPane = new GridPane();

        gridPane.setHgap(15.0);
        gridPane.setVgap(15.0);

        int access = method.getAccess();

        gridPane.addRow(0,
                createCheckBox("Public", Modifier.isPublic(access), "Declared public; may be accessed from outside its package."),
                createCheckBox("Private", Modifier.isPrivate(access), "Declared private; accessible only within the defining class."),
                createCheckBox("Protected", Modifier.isProtected(access), "Declared protected; may be accessed within subclasses."),
                createCheckBox("Static", Modifier.isStatic(access), "Declared static.")
        );

        gridPane.addRow(1,
                createCheckBox("Final", Modifier.isFinal(access), "Declared final; must not be overridden."),
                createCheckBox("Synchronized", Modifier.isSynchronized(access), "Declared synchronized; invocation is wrapped by a monitor use."),
                createCheckBox("Bridge", (access & Opcodes.ACC_BRIDGE) != 0, "A bridge method, generated by the compiler."),
                createCheckBox("Varargs", (access & Opcodes.ACC_VARARGS) != 0, "Declared with variable number of arguments.")
        );

        gridPane.addRow(2,
                createCheckBox("Native", Modifier.isNative(access), "Declared native; implemented in a language other than Java."),
                createCheckBox("Abstract", Modifier.isAbstract(access), "Declared abstract; no implementation is provided."),
                createCheckBox("Strict", Modifier.isStrict(access), "Declared strictfp; floating-point mode is FP-strict."),
                createCheckBox("Synthetic", (access & Opcodes.ACC_SYNTHETIC) != 0, "Declared synthetic; not present in the source code.")
        );

        return gridPane;
    }

    private Node createAttributeInfo() {
        AttributeListPanel attributeListPanel = new AttributeListPanel(method.getAttrs());
        TitledPane titledPane = new TitledPane("Attributes", attributeListPanel);

        if(attributeListPanel.isEmpty()) {
            titledPane.setExpanded(false);
        }

        return titledPane;
    }

    private CheckBox createCheckBox(String text, boolean defaultValue, String description) {
        CheckBox checkBox =  new CheckBox(text);
        checkBox.setSelected(defaultValue);
        checkBox.setTooltip(new Tooltip(description));
        return checkBox;
    }

    private String[] getTypeNames(Type[] types) {
        String[] typeNames = new String[types.length];

        for(int i = 0; i < types.length; i++) {
            typeNames[i] = types[i].getClassName();
        }

        return typeNames;
    }
}
