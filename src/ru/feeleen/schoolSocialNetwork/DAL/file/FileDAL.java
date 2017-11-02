package ru.feeleen.schoolSocialNetwork.DAL.file;

import ru.feeleen.schoolSocialNetwork.DAL.abstruct.INetWorkDAL;
import ru.feeleen.schoolSocialNetwork.enitities.AdmissionOrFinishDateDTO;
import ru.feeleen.schoolSocialNetwork.enitities.PersonDTO;
import ru.feeleen.schoolSocialNetwork.enitities.SchoolDTO;

import java.io.*;
import java.util.*;

public class FileDAL implements INetWorkDAL {
    private static Set<SchoolDTO> schools;
    private static Set<PersonDTO> persons;

    private static final String PATH = "";
    private static final String NAME_FILE_PERSONS = "persons.txt";
    private static final String NAME_FILE_SCHOOLS = "schools.txt";

    public FileDAL() {
        schools = new HashSet<>();
        persons = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(NAME_FILE_PERSONS))) {
            String temp;
            while ((temp = reader.readLine()) != null && temp.length() != 0) {
                LinkedList<String> schoolString = new LinkedList(Arrays.asList(reader.readLine().split("|")));
                SchoolDTO schoolDTO = new SchoolDTO();
                schoolDTO.id = UUID.fromString(schoolString.poll());
                schoolDTO.name = schoolString.poll();
                for (int i = 0; i < schoolString.size(); i++) {
                    String[] tempPerson = schoolString.get(i).split("&");
                    AdmissionOrFinishDateDTO admissionOrFinishDateDTO = new AdmissionOrFinishDateDTO(Date.parse(tempPerson[1]), Date.parse(tempPerson[2]));
                    schoolDTO.persons.put(UUID.fromString(tempPerson[0]), Date.parse(tempPerson[1]))
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public PersonDTO getPerson(UUID id) {
        return null;
    }


    @Override
    public SchoolDTO getSchool(UUID id) {
        return null;
    }

    @Override
    public Iterable<PersonDTO> getAllPersons() {
        return null;
    }

    @Override
    public Iterable<SchoolDTO> getAllSchools() {
        return null;
    }

    @Override
    public boolean update(SchoolDTO school) {
        return false;
    }

    @Override
    public boolean update(PersonDTO person) {
        return false;
    }

    @Override
    public boolean deletePerson(UUID id) {
        return false;
    }

    @Override
    public boolean deleteSchool(UUID id) {
        return false;
    }

    @Override
    public boolean createPerson(PersonDTO person) {
        return false;
    }

    @Override
    public boolean createSchool(SchoolDTO school) {
        return false;
    }

    @Override
    public boolean Save() {
        return false;
    }
}
