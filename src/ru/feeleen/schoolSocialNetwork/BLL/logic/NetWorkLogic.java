package ru.feeleen.schoolSocialNetwork.BLL.logic;

import ru.feeleen.schoolSocialNetwork.BLL.abstruct.INetWorkLogic;
import ru.feeleen.schoolSocialNetwork.DAL.abstruct.INetWorkDAL;
import ru.feeleen.schoolSocialNetwork.enitities.PersonDTO;
import ru.feeleen.schoolSocialNetwork.enitities.SchoolDTO;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class NetWorkLogic implements INetWorkLogic {
    INetWorkDAL dal;
    private int count;

    public NetWorkLogic(INetWorkDAL DAL) {
        if (DAL == null) {
            throw new IllegalArgumentException("DAL as parameter is null");
        }
        dal = DAL;
    }

    @Override
    public PersonDTO getPerson(UUID id) {
        return dal.getPerson(id);
    }

    @Override
    public SchoolDTO getSchool(UUID id) {
        return dal.getSchool(id);
    }

    @Override
    public Iterable<PersonDTO> getAllPersons() {
        return dal.getAllPersons();
    }

    @Override
    public Iterable<SchoolDTO> getAllSchools() {
        return dal.getAllSchools();
    }

    @Override
    public boolean update(SchoolDTO school) {
        return dal.update(school);
    }

    @Override
    public boolean update(PersonDTO person) {
        return dal.update(person);
    }

    @Override
    public boolean deletePerson(UUID id) {
        return dal.deletePerson(id);
    }

    @Override
    public boolean deleteSchool(UUID id) {
        return dal.deleteSchool(id);
    }

    @Override
    public boolean createPerson(PersonDTO person) {
        return dal.createPerson(person);
    }

    @Override
    public boolean createSchool(SchoolDTO school) {
        return dal.createSchool(school);
    }

    @Override
    public boolean Save() {
        return dal.Save();
    }

    @Override
    public boolean deleteAllPersonsWithoutSchool() {
        getAllPersons().forEach(personDTO -> {
            if (personDTO.schools != null && !personDTO.schools.isEmpty())
                deletePerson(personDTO.id);
        });
        return true;
    }

    @Override
    public Iterable<PersonDTO> getAllPersonByDateAndSchool(SchoolDTO school, Date date) {
        List<PersonDTO> personDTOS = new LinkedList<>();

        dal.getAllSchools().forEach(schoolDTO -> {
            if (schoolDTO == school) {
                schoolDTO.persons.forEach((personDTO, admissionOrFinishDateDTO) -> {
                    if (admissionOrFinishDateDTO != null && personDTO != null) {
                        if (admissionOrFinishDateDTO.dateOfAdmission.getTime() >= date.getTime() && admissionOrFinishDateDTO.dateOfFinish.getTime() <= date.getTime()) {
                            personDTOS.add(personDTO);
                        }
                    }
                });
            }
        });
        return personDTOS;
    }

    @Override
    public int calculateSchoolRating(SchoolDTO school) {
        count = 0;
        dal.getAllSchools().forEach(schoolDTO -> {
            if (schoolDTO == school) {
                // int count = 0 ;
                schoolDTO.persons.forEach((personDTO, admissionOrFinishDateDTO) -> count++);
            }
        });
        return count;
    }
}
