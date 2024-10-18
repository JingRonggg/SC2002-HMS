package src.controller;

import src.model.MedicalRecord;
import src.model.Patient;
import src.repository.IMedicalRecordRepository;
import src.repository.IPatientRepository;
import src.repository.MedicalRecordRepository;
import src.repository.PatientRepository;

import java.util.Scanner;

public class PatientController {
    private static IPatientRepository patientRepository;
    private static IMedicalRecordRepository medicalRecordRepository;

    public PatientController() {
        patientRepository = new PatientRepository();
        medicalRecordRepository = new MedicalRecordRepository();
    }

    public void getPatientInformation(String hospitalID) {
        Patient patient = patientRepository.getPatientInfo(hospitalID);
        if (patient != null) {
            System.out.println("Patient Information:");
            System.out.println("Name: " + patient.getName());
            System.out.println("Hospital ID: " + patient.getHospitalID());
            System.out.println("Date of Birth: " + patient.getDateOfBirth());
            System.out.println("Gender: " + patient.getGender());
            System.out.println("Blood Type: " + patient.getBloodType());
            System.out.println("Email Address: " + patient.getEmailAddress());
        } else {
            System.out.println("Could not retrieve patient information.");
        }
    }

    public void updatePatientInformation(String hospitalID) {
        Patient patient = patientRepository.getPatientInfo(hospitalID);
        if (patient != null) {
            System.out.println("New Email: ");
            Scanner scanner = new Scanner(System.in);
            String email = scanner.nextLine();
            if(patientRepository.updatePatientEmail(hospitalID, email)) {
                System.out.println("Patient Information updated successfully.");
            };
        } else {
            System.out.println("Could not update patient information.");
        }
    }

    public void viewMedicalRecord(String hospitalID) {
        Patient patient = patientRepository.getPatientInfo(hospitalID);
        MedicalRecord medicalRecord = medicalRecordRepository.readMedicalRecord(patient.getHospitalID());
        if (medicalRecord != null) {
            System.out.println("Medical Record:");
            System.out.println("Treatments:" + medicalRecord.getTreatments());
            System.out.println("Past Diagnosis:" + medicalRecord.getPastDiagnosis());
            System.out.println("Prescribe Medications:" + medicalRecord.getPrescribeMedications());
        }
    }
}
