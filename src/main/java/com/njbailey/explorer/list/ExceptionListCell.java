package com.njbailey.explorer.list;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.converter.DefaultStringConverter;

public class ExceptionListCell extends TextFieldListCell<String> {
    private ListView<String> exceptionList;

    public ExceptionListCell(ListView<String> exceptionList) {
        // TODO: Is it considered hacky to pass the ListView here? I don't know how else to
        //       access it to remove items...
        this.exceptionList = exceptionList;

        setConverter(new DefaultStringConverter());
    }

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if(empty || item == null) {
            setText("");
            setGraphic(null);
        } else {
            setEditable(true);

            setText(item);

            Button removeButton = new Button("X");
            removeButton.setOnAction(e -> {
                exceptionList.getItems().remove(item);
            });

            setGraphic(removeButton);
        }
    }
}
