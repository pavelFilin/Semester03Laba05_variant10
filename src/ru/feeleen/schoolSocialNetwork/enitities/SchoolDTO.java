package ru.feeleen.schoolSocialNetwork.enitities;

import java.util.GregorianCalendar;
import java.util.UUID;

public class SchoolDTO {
    public SchoolDTO(UUID id, String name, GregorianCalendar attendDate, GregorianCalendar endDate) {
        this.id = id;
        this.name = name;
        this.attendDate = attendDate;
        this.endDate = endDate;
    }

    public SchoolDTO() {

    }

    public UUID id;
    public String name;
    public GregorianCalendar attendDate;
    public GregorianCalendar endDate;
}
