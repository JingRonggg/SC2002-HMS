package src.view;

import src.model.Patient;

/**
 * A utility class for printing patient information to the console.
 */
public class PatientInfoPrinter {
    /**
     * Prints the detailed information of a patient to the console.
     * If the patient is null, prints an error message instead.
     *
     * @param patient The patient whose information should be printed.
     *               Can be null, in which case an error message is displayed.
     */
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
