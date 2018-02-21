package ru.feeleen.schoolSocialNetwork.enitities;

import java.util.GregorianCalendar;
import java.util.UUID;

public class SchoolVM {
    private UUID id;
    private String name;
    private GregorianCalendar attendDate;
    private GregorianCalendar endDate;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public SchoolDTO toSchoolDTO() {
        return new SchoolDTO(id, name, attendDate, endDate);
    }
}
