package ru.feeleen.schoolSocialNetwork.PL.FXUI.Interfaces.impls;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.feeleen.schoolSocialNetwork.BLL.abstruct.INetWorkLogic;
import ru.feeleen.schoolSocialNetwork.PL.FXUI.Interfaces.IPersonsCollection;
import ru.feeleen.schoolSocialNetwork.enitities.PersonDTO;
import ru.feeleen.schoolSocialNetwork.enitities.PersonVM;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

public class PersonsCollection implements IPersonsCollection {
    INetWorkLogic bll;

    private ObservableList<PersonVM> personList;

    public PersonsCollection(INetWorkLogic bll) {
        this.bll = bll;
        personList = FXCollections.observableArrayList(new LinkedList(getVMCollection((Collection) bll.getAllPersons())));
    }

    @Override
    public void add(PersonVM person) {
        personList.add(person);
        bll.createPerson(person.toPersonDTO());
    }

    @Override
    public void update(PersonVM person) {
        bll.update(person.toPersonDTO());
    }

    @Override
    public void delete(PersonVM person) {
        personList.remove(person);
        bll.deletePerson(person.getId());
    }

    private Collection<PersonVM> getVMCollection(Collection<PersonDTO> collection) {
        Collection<PersonVM> result = new ArrayList<>(collection.size());
        for (PersonDTO item : collection) {
            PersonVM temp = new PersonVM(item);
            result.add(temp);
        }
        return result;
    }

    public ObservableList<PersonVM> getPersonList() {
        return personList;
    }

}
