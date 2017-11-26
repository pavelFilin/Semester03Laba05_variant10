package ru.feeleen.schoolSocialNetwork.DAL.abstruct;

import ru.feeleen.schoolSocialNetwork.enitities.PersonDTO;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public interface INetWorkDAL {
    PersonDTO getPerson(UUID id);

    Iterable<PersonDTO> getAllPersons();

    boolean update(PersonDTO person);

    boolean deletePerson(UUID id);

    boolean create(PersonDTO person);

    boolean save() throws IOException;

    Map<String,Integer> getSchoolsWithRating();
}
