package ru.feeleen.schoolSocialNetwork.enitities;

import java.util.*;

public class PersonDTO {
    public UUID id;

    public HashMap<SchoolDTO, AdmissionOrFinishDateDTO> schools;

    public String firstName;
    public String secondName;
    public String middleName;
}
