package ru.feeleen.schoolSocialNetwork.DAL.file;

import ru.feeleen.schoolSocialNetwork.DAL.abstruct.INetWorkDAL;
import ru.feeleen.schoolSocialNetwork.enitities.PersonDTO;

import java.io.*;
import java.util.*;

public class FileDAL implements INetWorkDAL {

    private static Set<PersonDTO> persons;

    private static final String PATH = "";
    private static final String NAME_FILE_PERSONS = "persons.txt";

    public FileDAL() throws IOException {
        persons = new HashSet<>();
        persons = getPersonsDataFromFile(PATH + NAME_FILE_PERSONS);
    }

    private HashSet<PersonDTO> getPersonsDataFromFile(String path) throws IOException {
        HashSet<PersonDTO> personDTOS = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(PATH + NAME_FILE_PERSONS))) {
            String temp;
            while ((temp = reader.readLine()) != null && temp.length() != 0) {
                List<String> schoolString = new LinkedList<>(Arrays.asList(reader.readLine().split("|")));
                PersonDTO personDTO = new PersonDTO();
                personDTO.id = UUID.fromString(schoolString.get(0));
                personDTO.secondName = schoolString.get(1);
                personDTO.firstName = schoolString.get(2);
                personDTO.middleName = schoolString.get(3);
                personDTO.school = schoolString.get(4);
                personDTO.attendDate = new GregorianCalendar();
                personDTO.attendDate.set(Calendar.YEAR, Integer.parseInt(schoolString.get(5)));
                personDTO.endDate = new GregorianCalendar();
                personDTO.endDate.set(Calendar.YEAR, Integer.parseInt(schoolString.get(6)));
            }
        }
        return personDTOS;
    }

    @Override
    public PersonDTO getPerson(UUID id) {
        PersonDTO person = null;
        for (PersonDTO personDTO : persons) {
            if (personDTO.id == id) {
                person = personDTO;
            }
        }

        if (person == null) {
            throw new IllegalArgumentException("Incorrect ID");
        }

        return person;
    }


    @Override
    public Iterable<PersonDTO> getAllPersons() {
        return persons;
    }


    @Override
    public boolean update(PersonDTO person) {
        PersonDTO dataPerson = null;
        for (PersonDTO personDTO : persons) {
            if (personDTO.id == person.id) {
                dataPerson = personDTO;
            }
        }

        if (dataPerson == null) {
            throw new IllegalArgumentException("Incorrect ID");
        }

        dataPerson.id = person.id;
        dataPerson.firstName= person.firstName;
        dataPerson.secondName = person.secondName;
        dataPerson.middleName = person.middleName;
        dataPerson.attendDate = person.attendDate;
        dataPerson.school = person.school;
        dataPerson.endDate = person.endDate;
        return true;
    }

    @Override
    public boolean deletePerson(UUID id) {
        PersonDTO person = null;
        for (PersonDTO personDTO : persons) {
            if (personDTO.id == id) {
                person = personDTO;
            }
        }

        if (person == null) {
            throw new IllegalArgumentException("Incorrect ID");
        }

        persons.remove(person);
        return true;
    }


    @Override
    public boolean create(PersonDTO person) {
        if (person == null) {
            throw new NullPointerException("Incorrect person");
        }
        persons.add(person);
        return true;
    }


    @Override
    public boolean Save() throws IOException {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(PATH+NAME_FILE_PERSONS))) {
            for (PersonDTO person :
                    persons) {
                    writer.write(person.id.toString() + "|" + person.secondName + "|" + person.firstName + "|" + person.middleName
                            + "|" + person.school + "|" + person.attendDate.get(Calendar.YEAR) + "|" + person.endDate.get(Calendar.YEAR));
                    writer.newLine();

            }
        };
        return true;
    }
}
