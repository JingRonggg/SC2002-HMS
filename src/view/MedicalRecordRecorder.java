package src.view;

import src.model.PrescribeMedicationsStatus;
import src.model.MedicalRecord;
import src.model.PastDiagnosis;
import src.model.PrescribeMedications;
import src.model.Treatments;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

/**
 * A class responsible for recording medical record details through user input.
 */
public class MedicalRecordRecorder {
    /**
     * Prompts the user to enter details for a new medical record including diagnosis,
     * treatment, and prescribed medications.
     *
     * @return A new MedicalRecord object containing the entered information
     *         with null values for patient-specific fields
     */
    public static MedicalRecord askingForMedicalRecordDetails (String appointmentID){
        // Get new diagnosis details
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.println("New Diagnosis Name: ");
        String newDiagnosisName = scanner.nextLine();
        
        LocalDate newDiagnosisDate = null;
        while (newDiagnosisDate == null) {
            try {
                System.out.println("New Diagnosis Date (yyyy-MM-dd): ");
                newDiagnosisDate = LocalDate.parse(scanner.nextLine(), formatter);
            } catch (Exception e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd format.");
            }
        }
        
        PastDiagnosis newDiagnosis = new PastDiagnosis(newDiagnosisName, newDiagnosisDate);
    
        // Get new treatment details
        System.out.println("New Treatment Name: ");
        String newTreatmentName = scanner.nextLine();
        
        LocalDate newTreatmentDate = null;
        while (newTreatmentDate == null) {
            try {
                System.out.println("New Treatment Date (yyyy-MM-dd): ");
                newTreatmentDate = LocalDate.parse(scanner.nextLine(), formatter);
            } catch (Exception e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd format.");
            }
        }
        
        System.out.println("New Treatment Details: ");
        String newTreatmentDetails = scanner.nextLine();
        Treatments newTreatment = new Treatments(newTreatmentName, newTreatmentDate, newTreatmentDetails);
    
        // Get new prescribed medications
        List<PrescribeMedications> newPrescribeMedications = new ArrayList<>();
        String addMore;
        do {
            System.out.println("Enter Medication Name: ");
            String medicationName = scanner.nextLine();
            
            int dosage = 0;
            boolean validDosage = false;
            while (!validDosage) {
                try {
                    System.out.println("Enter Dosage: ");
                    dosage = parseInt(scanner.nextLine());
                    validDosage = true;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid dosage. Please enter a valid number.");
                }
            }
            
            newPrescribeMedications.add(new PrescribeMedications(medicationName, dosage, PrescribeMedicationsStatus.PENDING));
    
            System.out.println("Do you want to add another medication? (yes/no)");
            addMore = scanner.nextLine();
        } while (addMore.equalsIgnoreCase("yes"));
        return new MedicalRecord(null, null, null, newDiagnosis, newTreatment, newPrescribeMedications,appointmentID);
    }
}
