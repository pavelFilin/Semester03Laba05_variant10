package ru.feeleen.schoolSocialNetwork.PL.FXUI.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import ru.feeleen.schoolSocialNetwork.BLL.abstruct.INetWorkLogic;
import ru.feeleen.schoolSocialNetwork.BLL.logic.NetWorkLogic;
import ru.feeleen.schoolSocialNetwork.DAL.file.FileDAL;
import ru.feeleen.schoolSocialNetwork.PL.FXUI.Interfaces.impls.PersonsCollection;
import ru.feeleen.schoolSocialNetwork.enitities.PersonDTO;
import ru.feeleen.schoolSocialNetwork.enitities.PersonVM;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.URL;
import java.util.*;

public class MainPageController implements Initializable {
    @FXML
    TableView personsTable;

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

    private INetWorkLogic bll;

    PersonsCollection allPersons;

    private Parent fxmlEdit;
    private FXMLLoader fxmlLoader = new FXMLLoader();
    private AddDialogController addDialogController;
    private Stage editDialogStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            bll = new NetWorkLogic(new FileDAL());
            allPersons = new PersonsCollection(bll);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            fxmlLoader.setLocation(getClass().getResource("../fxml/add.fxml"));
            fxmlEdit = fxmlLoader.load();
            addDialogController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }


        firstNameColumn.setCellValueFactory(new PropertyValueFactory<PersonVM, String>("firstName"));
        secondNameColumn.setCellValueFactory(new PropertyValueFactory<PersonVM, String>("secondName"));
        middleNameColumn.setCellValueFactory(new PropertyValueFactory<PersonVM, String>("middleName"));
        schoolColumn.setCellValueFactory(new PropertyValueFactory<PersonVM, String>("school"));
        setParametrsCellFactory(attendDateColumn);
        setParametrsCellFactory(endDateColumn);

        attendDateColumn.setCellValueFactory(new PropertyValueFactory<PersonVM, GregorianCalendar>("attendDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<PersonVM, GregorianCalendar>("endDate"));

        personsTable.setItems(allPersons.getPersonList());
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

    public void save(ActionEvent actionEvent) {
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
            case "addPersonButton":
                addDialogController.setPerson(new PersonVM("text", "text", "text"));
                showDialog(parentWindow);
                allPersons.add(addDialogController.getPerson());
                break;

            case "btnEdit":

                break;


            case "btnDelete":

                break;

        }
    }

    private void showDialog(Window parentWindow) {
        if (editDialogStage == null) {
            editDialogStage = new Stage();
            editDialogStage.setTitle("Редактирование записи");
            editDialogStage.setMinHeight(150);
            editDialogStage.setMinWidth(300);
            editDialogStage.setResizable(false);
            editDialogStage.setScene(new Scene(fxmlEdit));
            editDialogStage.initModality(Modality.WINDOW_MODAL);
            editDialogStage.initOwner(parentWindow);
        }

        editDialogStage.showAndWait();

    }
}
