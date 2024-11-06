package src.view;

import src.model.Patient;

public class PatientInfoPrinter {
    public static void printPatientInfo(Patient patient) {
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
}
