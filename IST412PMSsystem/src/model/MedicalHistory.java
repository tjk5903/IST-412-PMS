package model;

import java.util.List;

public class MedicalHistory {
    private Patient patient;
    private List<String> records;

    public MedicalHistory(Patient patient, List<String> records) {
        this.patient = patient;
        this.records = records;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public List<String> getRecords() {
        return records;
    }

    public void setRecords(List<String> records) {
        this.records = records;
    }
}
