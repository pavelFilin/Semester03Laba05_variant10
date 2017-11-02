package ru.feeleen.schoolSocialNetwork.DAL.abstruct;

import ru.feeleen.schoolSocialNetwork.enitities.PersonDTO;
import ru.feeleen.schoolSocialNetwork.enitities.SchoolDTO;

import java.util.UUID;

public interface INetWorkDAL {
    PersonDTO getPerson(UUID id);
    SchoolDTO getSchool(UUID id);

    Iterable<PersonDTO> getAllPersons();
    Iterable<SchoolDTO> getAllSchools();

    boolean update(SchoolDTO school);
    boolean update(PersonDTO person);

    boolean deletePerson(UUID id);
    boolean deleteSchool(UUID id);

    boolean createPerson(PersonDTO person);
    boolean createSchool(SchoolDTO school);

    boolean Save();
}