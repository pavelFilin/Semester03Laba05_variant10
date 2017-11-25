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
            //FileWriter fd = new FileWriter(PATH + NAME_FILE_PERSONS);
            //fd.close();
            // try (BufferedReader reader = new BufferedReader(new InputStreamReader(null))) {
            String temp;
            while ((temp = reader.readLine()) != null && temp.length() != 0) {
                // List<String> schoolString = new LinkedList<>(Arrays.asList(reader.readLine().split("|")));
                String[] arr = temp.split("&");
                if (arr.length == 4) {
                    List<String> tempListStrings = new LinkedList<>(Arrays.asList(temp.split("&")));
                    PersonDTO personDTO = new PersonDTO();
                    personDTO.id = UUID.fromString(tempListStrings.get(0));
                    personDTO.secondName = tempListStrings.get(1);
                    personDTO.firstName = tempListStrings.get(2);
                    personDTO.middleName = tempListStrings.get(3);
                    personDTOS.add(personDTO);
                } else if (arr.length == 7) {
                    List<String> tempListStrings = new LinkedList<>(Arrays.asList(temp.split("&")));
                    PersonDTO personDTO = new PersonDTO();
                    personDTO.id = UUID.fromString(tempListStrings.get(0));
                    personDTO.secondName = tempListStrings.get(1);
                    personDTO.firstName = tempListStrings.get(2);
                    personDTO.middleName = tempListStrings.get(3);
                    personDTO.school = tempListStrings.get(4);
                    personDTO.attendDate = new GregorianCalendar();
                    personDTO.attendDate.set(Calendar.YEAR, Integer.parseInt(tempListStrings.get(5)));
                    personDTO.endDate = new GregorianCalendar();
                    personDTO.endDate.set(Calendar.YEAR, Integer.parseInt(tempListStrings.get(6)));
                    personDTOS.add(personDTO);
                } else {
                    throw new RuntimeException("Unreadable file");
                }
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
        dataPerson.firstName = person.firstName;
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
    public boolean save() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATH + NAME_FILE_PERSONS, false))) {
            try {
                for (PersonDTO person : persons) {
                    try {
                        writer.write(person.id.toString() + "&" + person.secondName + "&" + person.firstName + "&" + person.middleName
                                + "&" + person.school + "&" + person.attendDate.get(Calendar.YEAR) + "&" + person.endDate.get(Calendar.YEAR));
                        writer.newLine();
                    } catch (Exception e) {
                        writer.write(person.id.toString() + "&" + person.secondName + "&" + person.firstName + "&" + person.middleName
                                + "&" + "&" + "&");
                        writer.newLine();
                    }
                }
            } catch(Exception e){
                System.out.println(e);
                throw new RuntimeException();
            }
        }
        return true;
    }
}

