package ru.feeleen.schoolSocialNetwork.enitities;

import java.util.HashMap;
import java.util.UUID;

public class SchoolDTO {
    public UUID id;

    public String name;

    public HashMap<PersonDTO, AdmissionOrFinishDateDTO> persons;
}
