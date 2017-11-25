package ru.feeleen.schoolSocialNetwork.PL.FXUI.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.feeleen.schoolSocialNetwork.enitities.PersonVM;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class AddDialogController {
    @FXML
    TextField txtLastName;
    @FXML
    TextField txtFirstName;
    @FXML
    TextField txtMiddleName;
    @FXML
    TextField txtSchool;

    @FXML
    Button btnOK;
    @FXML
    Button btnCancel;

    @FXML
    DatePicker datePickerAttend;
    @FXML
    DatePicker datePickerEnd;

    private PersonVM person;

    private int dateAttend = 0;
    private int dateEnd = 0;

    public PersonVM getPerson() {
        return person;
    }

    public void actionClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }

    public void actionSave(ActionEvent actionEvent) {
        String fName = txtFirstName.getText().trim();
        String sName = txtLastName.getText().trim();
        String mName = txtMiddleName.getText().trim();
        String school = txtSchool.getText().trim();
        if (!fName.isEmpty() && !sName.isEmpty() && !mName.isEmpty()) {
            if (!school.isEmpty() && dateAttend < dateEnd && dateAttend !=0 && dateEnd!=0) {
                GregorianCalendar FROM = new GregorianCalendar();
                FROM.set(Calendar.YEAR, dateAttend);
                GregorianCalendar TO = new GregorianCalendar();
                TO.set(Calendar.YEAR, dateEnd);

                person = new PersonVM(fName, sName, mName, school, FROM, TO);

                actionClose(actionEvent);
            } else if(!school.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("WARNING Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Badly school information");
                alert.showAndWait();
            } else {
                person = new PersonVM(fName, sName, mName, null, null, null);
                actionClose(actionEvent);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING Dialog");
            alert.setHeaderText(null);
            alert.setContentText("You have a problem with text fields or date pickers. LOOK on your fields and try again");
            alert.showAndWait();
        }
    }


    public void setDate(ActionEvent actionEvent) {
        DatePicker datePicker = (DatePicker) actionEvent.getSource();
        switch (datePicker.getId()) {
            case "datePickerAttend": {
                dateAttend = datePicker.getValue().getYear();
            }
            break;
            case "datePickerEnd": {
                dateEnd = datePicker.getValue().getYear();
            }
            break;
            default:
                throw new IllegalArgumentException("Error date picker");
        }
    }

    public void setPerson(PersonVM person) {
        this.person = person;

    }
}
