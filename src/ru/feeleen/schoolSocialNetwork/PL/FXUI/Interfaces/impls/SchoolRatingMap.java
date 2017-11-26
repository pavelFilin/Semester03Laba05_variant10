package ru.feeleen.schoolSocialNetwork.PL.FXUI.Interfaces.impls;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import ru.feeleen.schoolSocialNetwork.BLL.abstruct.INetWorkLogic;
import ru.feeleen.schoolSocialNetwork.PL.FXUI.Interfaces.ISchoolRatingMap;

import java.util.Map;
import java.util.TreeMap;

public class SchoolRatingMap implements ISchoolRatingMap {
    INetWorkLogic bll;

    private ObservableList<Map.Entry<String, Integer>> schoolRatringMap;

    public SchoolRatingMap(INetWorkLogic bll) {
        this.bll = bll;
        schoolRatringMap = FXCollections.observableArrayList(bll.getCalculateSchoolRating().entrySet());
    }

    @Override
    public ObservableList<Map.Entry<String, Integer>> getMap() {
        return schoolRatringMap;
    }

    @Override
    public void refresh() {
        schoolRatringMap = FXCollections.observableArrayList(bll.getCalculateSchoolRating().entrySet());
    }
}
