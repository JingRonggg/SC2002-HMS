package src.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

import src.controller.AdminController;
import src.controller.MedicineController;
import src.model.*;

public class PharmacistBoundary {

    private final MedicineController medicineController;


    public PharmacistBoundary(MedicineController medicineController) {
        this.medicineController = medicineController;
    }
    public void displayPharmacistMenu(Scanner scanner) {
        while (true) {
            try{
                System.out.println("Pharmacist Menu:");
                System.out.println("1. View Appointment Outcome Record");
                System.out.println("2. Update Prescription Status");
                System.out.println("3. View Medication Inventory");
                System.out.println("4. Submit Replenishment Request");
                System.out.println("5. Logout");
                System.out.print("Enter your choice: ");
                int choice = parseInt(scanner.nextLine());
    
                switch (choice) {
                    case 1:
                        try{
                            HashMap<String, Appointment> pendingMedicationAppointments= medicineController.getAllPendingMedicationStatusAppointment();
                            System.out.println("Here is the list of appointments with pending medication status:");
                            if (pendingMedicationAppointments.isEmpty()) {
                                System.out.println("No appointments with pending medication status");
                            }
                            for (Map.Entry<String, Appointment> entry : pendingMedicationAppointments.entrySet()) {
                                String appointmentID = entry.getKey();
                                Appointment appointment = entry.getValue();
    
                                String doctorID = appointment.getDoctorID();
                                String patientID = appointment.getPatientID();
    
                                System.out.println("----------------------------------------------------------------------------");
                                System.out.println("Appointment ID: " + appointmentID);
                                System.out.println("Date: " + appointment.getAppointmentDate());
                                System.out.println("Appointment Time: " + appointment.getAppointmentStartTime());
                                System.out.println("Doctor: " + appointment.getDoctorName());
                                System.out.println("Status: " + appointment.getStatus());
                                MedicalRecordPrinter.printMedicalRecordDetails(medicineController.getAllPendingMedicalRecord(patientID, doctorID));
                                System.out.println("----------------------------------------------------------------------------");
                            }
                        } catch (Exception e) {
                            System.out.println("No appointments found");
                        }
                        break;
                    case 2:
                        try{
                            if (!medicineController.getAllUndispensedMedicalRecord().isEmpty()) {
                                MedicalRecordPrinter.printMedicalRecordDetails(medicineController.getAllUndispensedMedicalRecord());
                                System.out.println("Dispense for which medical record? Provide the medical record ID");
                                String medicalRecordID = scanner.nextLine();
                                medicineController.dispenseMedicine(medicalRecordID);
                            } else {
                                System.out.println("===============No medical record found===============");
                            }
                        } catch (Exception e) {
                            System.out.println("No pending undispensed medications for appointments");
                        }
                        break;
                    case 3:
                        try{
                            medicineController.displayAllMedicines();
                        } catch (Exception e) {
                            System.out.println("No medicines found");
                        }
                        break;
                    case 4:
                        try{
                            medicineController.reqMedicine();
                        } catch (Exception e) {
                            System.out.println("Request failed");
                        }
                        break;
                    case 5:
                        System.out.println("Logging out...");
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number for your choice.");
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }
}
