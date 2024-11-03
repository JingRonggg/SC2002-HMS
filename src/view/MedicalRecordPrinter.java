package src.view;

import src.model.MedicalRecord;
import src.model.PastDiagnosis;
import src.model.PrescribeMedications;
import src.model.Treatments;

import java.util.List;
import java.util.Map;

public class MedicalRecordPrinter {
    public static void printMedicalRecordDetails(Map<String, MedicalRecord> medicalRecords) {
        System.out.println("==========================================");
        System.out.println("               Medical Records            ");
        System.out.println("==========================================");

        for (Map.Entry<String, MedicalRecord> entry : medicalRecords.entrySet()) {
            String medicalRecordID = entry.getKey();
            MedicalRecord medicalRecord = entry.getValue();

            System.out.println("Medical Record ID: " + medicalRecordID);
            System.out.println("Patient ID: " + medicalRecord.getPatientID());
            System.out.println("Doctor ID: " + medicalRecord.getDoctorID());

            // Print Past Diagnosis
            PastDiagnosis diagnosis = medicalRecord.getPastDiagnosis();
            if (diagnosis != null) {
                System.out.println("Past Diagnosis: " + diagnosis.getConditionName() + " on " + diagnosis.getDiagnosisDate());
                System.out.println("Details: " + diagnosis.getConditionName());
            } else {
                System.out.println("Past Diagnosis: None");
            }

            // Print Treatments
            Treatments treatment = medicalRecord.getTreatments();
            if (treatment != null) {
                System.out.println("Treatment: " + treatment.getTreatmentName() + " on " + treatment.getTreatmentDate());
                System.out.println("Details: " + treatment.getTreatmentDetails());
            } else {
                System.out.println("Treatment: None");
            }

            // Print Prescribed Medications
            List<PrescribeMedications> medications = medicalRecord.getPrescribeMedications();
            if (!medications.isEmpty()) {
                System.out.println("Prescribed Medications:");
                for (PrescribeMedications medication : medications) {
                    System.out.printf(" - %s (%s)| Status: %s %n", medication.getMedicineName(), medication.getQuantity(), medication.getStatus());
                }
            } else {
                System.out.println("Prescribed Medications: None");
            }

            System.out.println("------------------------------------------");
        }
        System.out.println("==========================================");
    }
}