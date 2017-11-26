package ru.feeleen.schoolSocialNetwork.BLL.logic;

import ru.feeleen.schoolSocialNetwork.BLL.abstruct.INetWorkLogic;
import ru.feeleen.schoolSocialNetwork.DAL.abstruct.INetWorkDAL;
import ru.feeleen.schoolSocialNetwork.enitities.PersonDTO;

import java.io.IOException;
import java.util.*;

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
    public Iterable<PersonDTO> getAllPersons() {
        return dal.getAllPersons();
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
    public boolean createPerson(PersonDTO person) {
        return dal.create(person);
    }

    @Override
    public boolean save() throws IOException {
        return dal.save();
    }

    @Override
    public boolean deleteAllPersonsWithoutSchool() {
        Iterable<PersonDTO> personDTOS = dal.getAllPersons();
        List<PersonDTO> personDTOList = new LinkedList<>();
        //TODO
        for (PersonDTO item : personDTOS) {
            if (item.school == null || item.school == "") {
                // dal.deletePerson(item.id);
                personDTOList.add(item);
            }
        }

        for (PersonDTO item : personDTOList) {
            dal.deletePerson(item.id);
        }
        return true;
    }

    @Override
    public Iterable<PersonDTO> getAllPersonByDateAndSchool(String school, int year) {
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.YEAR, year);
        Iterable<PersonDTO> personDTOS = dal.getAllPersons();
        List<PersonDTO> personDTOList = new LinkedList<>();
        for (PersonDTO item :
                personDTOS) {
            if (item.school != null && item.school.equals(school) && item.attendDate != null && item.endDate != null && calendar.after(item.attendDate) && calendar.before(item.endDate)) {
                personDTOList.add(item);
            }
        }
        return personDTOList;
    }

    @Override
    public int getCalculateSchoolRating(String schoolName) {
        int rating = 0;
        Iterable<PersonDTO> personDTOS = dal.getAllPersons();
        for (PersonDTO item : personDTOS) {
            if (item.school.equals(schoolName)) {
                rating++;
            }
        }
        return rating;
    }

    @Override
    public Map<String, Integer> getCalculateSchoolRating() {
        return dal.getSchoolsWithRating();
    }
}
