package ru.feeleen.schoolSocialNetwork.PL.FXUI.Interfaces;

import ru.feeleen.schoolSocialNetwork.enitities.PersonVM;

public interface IPersonsCollection {
    void add(PersonVM person);

    void update(PersonVM person);

    void delete(PersonVM person);
}
