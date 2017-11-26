package ru.feeleen.schoolSocialNetwork.PL.FXUI.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import ru.feeleen.schoolSocialNetwork.BLL.abstruct.INetWorkLogic;
import ru.feeleen.schoolSocialNetwork.BLL.logic.NetWorkLogic;
import ru.feeleen.schoolSocialNetwork.DAL.file.FileDAL;
import ru.feeleen.schoolSocialNetwork.DAL.file.MariaDBDAL;
import ru.feeleen.schoolSocialNetwork.PL.FXUI.AlertManager;
import ru.feeleen.schoolSocialNetwork.PL.FXUI.Interfaces.ISchoolRatingMap;
import ru.feeleen.schoolSocialNetwork.PL.FXUI.Interfaces.impls.PersonsCollection;
import ru.feeleen.schoolSocialNetwork.PL.FXUI.Interfaces.impls.SchoolRatingMap;
import ru.feeleen.schoolSocialNetwork.enitities.PersonDTO;
import ru.feeleen.schoolSocialNetwork.enitities.PersonVM;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MainPageController implements Initializable {
    @FXML
    TableView personsTable;
    @FXML
    TableView ratingTable;

    @FXML
    TableColumn schoolName;
    @FXML
    TableColumn rating;

    @FXML
    TableColumn firstNameColumn;
    @FXML
    TableColumn secondNameColumn;
    @FXML
    TableColumn middleNameColumn;
    @FXML
    TableColumn schoolColumn;
    @FXML
    TableColumn attendDateColumn;

    @FXML
    TableColumn endDateColumn;

    @FXML
    TextField txtSelectedYear;
    @FXML
    TextField txtSelectedSchool;

    @FXML
    CheckBox checkBoxSelected;


    private INetWorkLogic bll;

    PersonsCollection personsCollection;

    private Parent fxmlEdit;
    private FXMLLoader fxmlLoader = new FXMLLoader();
    private AddDialogController addDialogController;
    private Stage editDialogStage;

    ISchoolRatingMap schoolRatingMap;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gettingDateFromDatebase();

        initializeAddForm();

        fillSchoolRatingTable();

        fillPersonsTable();
    }

    private void gettingDateFromDatebase() {
        try {
            //bll = new NetWorkLogic(new FileDAL());
            bll = new NetWorkLogic(new MariaDBDAL());
            personsCollection = new PersonsCollection(bll);
            schoolRatingMap = new SchoolRatingMap(bll);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeAddForm() {
        try {
            fxmlLoader.setLocation(getClass().getResource("../fxml/add.fxml"));
            fxmlEdit = fxmlLoader.load();
            addDialogController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fillPersonsTable() {
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<PersonVM, String>("firstName"));
        secondNameColumn.setCellValueFactory(new PropertyValueFactory<PersonVM, String>("secondName"));
        middleNameColumn.setCellValueFactory(new PropertyValueFactory<PersonVM, String>("middleName"));
        schoolColumn.setCellValueFactory(new PropertyValueFactory<PersonVM, String>("school"));
        setParametrsCellFactory(attendDateColumn);
        setParametrsCellFactory(endDateColumn);

        attendDateColumn.setCellValueFactory(new PropertyValueFactory<PersonVM, GregorianCalendar>("attendDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<PersonVM, GregorianCalendar>("endDate"));

        personsTable.setItems(personsCollection.getPersonList());
    }

    public void fillSchoolRatingTable() {
        schoolName.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Map.Entry<String, Integer>, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<String, Integer>, String> param) {
                        return new SimpleStringProperty(String.valueOf(param.getValue().getKey()));
                    }
                });

        rating.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Map.Entry<String, Integer>, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<String, Integer>, String> param) {
                        return new SimpleStringProperty(String.valueOf(param.getValue().getValue()));
                    }
                });
        ratingTable.setItems(schoolRatingMap.getMap());
    }

    private void setParametrsCellFactory(TableColumn column) {
        column.setCellFactory(col -> new TableCell<PersonVM, Calendar>() {
            @Override
            public void updateItem(Calendar item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setText(null);
                } else {
                    setText(Integer.toString(item.get(Calendar.YEAR)));
                }
            }
        });
    }

    private Collection<PersonVM> getVMCollection(Collection<PersonDTO> collection) {
        Collection<PersonVM> result = new ArrayList<>(collection.size());
        for (PersonDTO item : collection) {
            PersonVM temp = new PersonVM(item);
            result.add(temp);
        }
        return result;
    }

    public void save() {
        try {
            bll.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionButtonPressed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();

        if (!(source instanceof Button)) {
            return;
        }

        Button clickedButton = (Button) source;

        PersonVM selectedPerson = (PersonVM) personsTable.getSelectionModel().getSelectedItem();

        Window parentWindow = ((Node) actionEvent.getSource()).getScene().getWindow();

        switch (clickedButton.getId()) {
            case "addPersonButton": {
                addDialogController.setPerson(new PersonVM("", "", ""));
                showDialog(parentWindow);
                personsCollection.add(addDialogController.getPerson());
                schoolRatingMap.refresh();
                ratingTable.setItems(schoolRatingMap.getMap());
            }
            break;

            case "editButton": {
                if (selectedPerson == null) {
                    AlertManager.printBasicWarringAlert("didn't chose any person");
                    return;
                }
                addDialogController.setPerson(selectedPerson);
                showDialog(parentWindow);
                personsCollection.update(addDialogController.getPerson());
                personsTable.refresh();
                schoolRatingMap.refresh();
                ratingTable.setItems(schoolRatingMap.getMap());
            }
            break;

            case "deleteButton": {
                personsCollection.delete(selectedPerson);
            }
            break;

            case "deleteWithoutSchoolButton": {
                bll.deleteAllPersonsWithoutSchool();
                personsCollection.refreshCollection();
                personsTable.setItems(personsCollection.getPersonList());
                schoolRatingMap.refresh();
                ratingTable.setItems(schoolRatingMap.getMap());
            }

            case "saveButton": {
                save();
            }
        }
    }

    private void showDialog(Window parentWindow) {
        if (editDialogStage == null) {
            editDialogStage = new Stage();
            editDialogStage.setTitle("Edit table");
            editDialogStage.setMinHeight(150);
            editDialogStage.setMinWidth(300);
            editDialogStage.setResizable(false);
            editDialogStage.setScene(new Scene(fxmlEdit));
            editDialogStage.initModality(Modality.WINDOW_MODAL);
            editDialogStage.initOwner(parentWindow);
        }

        editDialogStage.showAndWait();

    }

    public void actionSelected(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();

        if (!(source instanceof CheckBox)) {
            return;
        }

        CheckBox checkBox = (CheckBox) source;

        if (!checkBox.isSelected()) {
            personsTable.setItems(personsCollection.getPersonList());
        } else {
            try {
                Collection<PersonDTO> collection = (Collection) bll.getAllPersonByDateAndSchool(txtSelectedSchool.getText(), Integer.parseInt(txtSelectedYear.getText()));
                Collection<PersonVM> result = new ArrayList<>(collection.size());
                for (PersonDTO item : collection) {
                    PersonVM temp = new PersonVM(item);
                    result.add(temp);
                }

                if (!result.isEmpty()) {
                    personsTable.setItems(FXCollections.observableArrayList(result));
                } else {
                    AlertManager.printBasicWarringAlert("Don't found any person");
                    checkBox.setSelected(false);
                }
            } catch (NumberFormatException e) {
                {
                    AlertManager.printBasicWarringAlert("Bad year");
                    checkBox.setSelected(false);
                }
                //e.printStackTrace();
            }
        }
        personsTable.refresh();
    }
}
