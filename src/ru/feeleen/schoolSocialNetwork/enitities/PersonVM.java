package ru.feeleen.schoolSocialNetwork.enitities;

import java.util.GregorianCalendar;
import java.util.UUID;

public class PersonVM {
    public UUID id;

    private String firstName;
    private String secondName;
    private String middleName;

    private String school;

    private GregorianCalendar attendDate;
    private GregorianCalendar endDate;

    public PersonVM(String firstName, String secondName, String middleName) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.middleName = middleName;
        id = UUID.randomUUID();
    }

    public PersonVM(String firstName, String secondName, String middleName, GregorianCalendar attendDate, GregorianCalendar endDate) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.middleName = middleName;
        this.attendDate = attendDate;
        this.endDate = endDate;
        id = UUID.randomUUID();
    }

    public PersonVM(PersonDTO personDTO) {
        this.firstName = personDTO.firstName;
        this.secondName = personDTO.secondName;
        this.middleName = personDTO.middleName;
        this.attendDate = personDTO.attendDate;
        this.endDate = personDTO.endDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName == null) {
            throw new NullPointerException();
        }

        if (firstName.isEmpty()) {
            throw new IllegalArgumentException();
        }

        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        if (secondName == null) {
            throw new NullPointerException();
        }

        if (secondName.isEmpty()) {
            throw new IllegalArgumentException();
        }

        this.secondName = secondName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        if (middleName == null) {
            throw new NullPointerException();
        }

        if (middleName.isEmpty()) {
            throw new IllegalArgumentException();
        }

        this.middleName = middleName;
    }

    public PersonDTO toPersonDTO() {
        PersonDTO result = new PersonDTO();
        result.id = this.id;
        result.firstName = this.firstName;
        result.secondName = this.secondName;
        result.middleName = this.middleName;
        result.school = this.school;
        result.attendDate = this.attendDate;
        result.endDate = this.endDate;
        return new PersonDTO();
    }
}
