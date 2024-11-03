package src.controller;

import src.interfaces.IMedicalRecordRepository;
import src.repository.MedicalRecordRepository;
import src.model.MedicalRecord;
import src.utils.AppointmentLoader;
import src.utils.MedicalRecordLoader;
import java.util.HashMap;

public class MedicalRecordController {
    private static IMedicalRecordRepository medicalRecordRepository;

    public MedicalRecordController(IMedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
        loadMedicalRecords();
    }

    private void loadMedicalRecords() {
        String medicalRecordFile = "./data/MedicalRecord_List.csv";
        MedicalRecordLoader medicalRecordLoader= new MedicalRecordLoader(medicalRecordFile);
        medicalRecordLoader.loadMedicalRecords();
    }

    // Adds a medical record to the repository with a specific ID
    public static void addMedicalRecord(String medicalRecordID, MedicalRecord medicalRecord) {
        // Directly using the repository to store the medical record
        medicalRecordRepository.addMedicalRecord(medicalRecordID, medicalRecord);
    }
    

}
