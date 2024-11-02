package src.view;

import src.model.Patient;

import java.util.Collection;

public class PatientPrinter {
    public static void patientPrinter(Collection<Patient> patient) {
        int count = 1;
        for (Patient patients : patient) {
            System.out.println("====================================================");
            System.out.printf("Patient Number %d", count++);
            System.out.println();
            System.out.println("Patient Name: " + patients.getName());
            System.out.println("Patient's hospital ID: " + patients.getHospitalID());
            System.out.println("Patient's Date of Birth: " + patients.getDateOfBirth());
            System.out.println("Patient's gender: " + patients.getGender());
            System.out.println("Patient's blood type: " + patients.getBloodType());
            System.out.println("====================================================");
        }
    }
    }
