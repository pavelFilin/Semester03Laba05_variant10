package ru.feeleen.schoolSocialNetwork.enitities;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.UUID;

public class PersonVM {
    private UUID id;

    private String firstName;
    private String secondName;
    private String middleName;

    private GregorianCalendar attendDate;
    private GregorianCalendar endDate;

    private String school;

    public PersonVM(String firstName, String secondName, String middleName) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.middleName = middleName;
        id = UUID.randomUUID();
    }

    public PersonVM(String firstName, String secondName, String middleName, String school, GregorianCalendar attendDate, GregorianCalendar endDate) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.middleName = middleName;
        this.school = school;
        this.attendDate = attendDate;
        this.endDate = endDate;
        id = UUID.randomUUID();
    }

    public PersonVM(PersonDTO personDTO) {
        this.id = personDTO.id;
        this.firstName = personDTO.firstName;
        this.secondName = personDTO.secondName;
        this.middleName = personDTO.middleName;
        this.school = personDTO.school;
        this.attendDate = personDTO.attendDate;
        this.endDate = personDTO.endDate;
    }

    public UUID getId() {
        return id;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
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

    public GregorianCalendar getAttendDate() {
        return attendDate;
    }

    public void setAttendDate(GregorianCalendar attendDate) {
        this.attendDate = attendDate;
    }

    public GregorianCalendar getEndDate() {
        return endDate;
    }

    public void setEndDate(GregorianCalendar endDate) {
        this.endDate = endDate;
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
        return result;
    }
}
