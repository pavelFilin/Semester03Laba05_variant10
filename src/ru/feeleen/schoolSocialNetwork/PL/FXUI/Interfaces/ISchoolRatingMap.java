package ru.feeleen.schoolSocialNetwork.PL.FXUI.Interfaces;

import javafx.collections.ObservableList;

import java.util.Map;

public interface ISchoolRatingMap {
    ObservableList<Map.Entry<String, Integer>>  getMap();

    void refresh();
}
