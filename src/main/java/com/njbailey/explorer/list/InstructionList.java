package com.njbailey.explorer.list;

import com.njbailey.bytelib.Method;
import com.njbailey.bytelib.code.Instruction;
import javafx.scene.control.ListView;
import lombok.Getter;

public class InstructionList extends ListView<Instruction> {
    @Getter
    private Method method;

    public InstructionList(Method method) {
        this();
        setMethod(method);
    }

    public InstructionList() {
        setCellFactory(ignored -> new InstructionListCell());
    }

    public void setMethod(Method method) {
        this.method = method;

        for(Instruction instruction : method.getInstructions()) {
            getItems().add(instruction);
        }
    }
}
