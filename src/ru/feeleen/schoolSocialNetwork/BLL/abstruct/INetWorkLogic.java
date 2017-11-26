package ru.feeleen.schoolSocialNetwork.BLL.abstruct;

import ru.feeleen.schoolSocialNetwork.enitities.PersonDTO;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

public interface INetWorkLogic {

    PersonDTO getPerson(UUID id);

    Iterable<PersonDTO> getAllPersons();

    boolean update(PersonDTO person);

    boolean deletePerson(UUID id);

    boolean createPerson(PersonDTO person);

    boolean save() throws IOException;

    boolean deleteAllPersonsWithoutSchool();

    Iterable<PersonDTO> getAllPersonByDateAndSchool(String school, int year);

    int getCalculateSchoolRating(String schoolName);

    Map<String, Integer> getCalculateSchoolRating();
}
