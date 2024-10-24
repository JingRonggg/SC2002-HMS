package src.view;

import src.appointment.Appointment;
import src.controller.DoctorController;
import src.model.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

import static java.lang.Integer.parseInt;

public class DoctorBoundary {
    private final DoctorController doctorController;
    private final String doctorID;

    public DoctorBoundary(DoctorController doctorController, String doctorID) {
        this.doctorController = doctorController;
        this.doctorID = doctorID;
    }

    public void displayDoctorMenu(Scanner scanner) {
        while (true) {
            String patientID = null;
            String date = null;
            MedicalRecord medicalRecord = null;
            System.out.println("Doctor Menu:");
            System.out.println("1. View Patient Medical Records");
            System.out.println("2. Update Patient Medical Records");
            System.out.println("3. View Personal Schedule");
            System.out.println("4. Set Availability for Appointments");
            System.out.println("5. Accept or Decline Appointment Requests");
            System.out.println("6. View Upcoming Appointments");
            System.out.println("7. Record Appointment Outcome");
            System.out.println("8. Logout");
            System.out.print("Enter your choice: ");
            int choice = parseInt(scanner.nextLine());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            switch (choice) {
                case 1:
                    Collection<Patient> patients = doctorController.viewAllPatients();
                    int count = 1;
                    for (Patient patient : patients) {
                        System.out.println("====================================================");
                        System.out.printf("Patient Number %d", count++);
                        System.out.println();
                        System.out.println("Patient Name: " + patient.getName());
                        System.out.println("Patient's hospital ID: " + patient.getHospitalID());
                        System.out.println("Patient's Date of Birth: " + patient.getDateOfBirth());
                        System.out.println("Patient's gender: " + patient.getGender());
                        System.out.println("Patient's blood type: " + patient.getBloodType());
                        System.out.println("====================================================");
                    }
                    System.out.println("Which patient's medical record do you want to view? Enter their hospital ID");
                    patientID = scanner.nextLine();
                    Map<String, MedicalRecord> records = doctorController.viewPatientMedicalRecords(patientID);
                    printMedicalRecordDetails(records);
                    break;
                case 2:
                    System.out.println("Enter the ID of the Medical Record that you would like to update");
                    String medicalRecordID = scanner.nextLine();

                    if (!doctorController.medicalRecordExists(medicalRecordID)) {
                        System.out.println("Medical Record not found. Cannot proceed with the update.");
                        break;
                    }
                    medicalRecord = askingForMedicalRecordDetails();
                    // Update medical records via controller
                    doctorController.updatePatientMedicalRecords(medicalRecordID, medicalRecord.getPastDiagnosis(), medicalRecord.getTreatments(), medicalRecord.getPrescribeMedications());
                    break;
                case 3:
                    System.out.println("Enter the date you want to check your schedule (yyyy-MM-dd): ");
                    date = scanner.nextLine();
                    try {
                        LocalDate localDate = LocalDate.parse(date, formatter);
                        doctorController.viewPersonalSchedule(doctorID, localDate);
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid date format");
                    }
                    break;
                case 4:
                    System.out.println("Enter a date (yyyy-MM-dd): ");
                    date = scanner.nextLine();
                    System.out.println("Enter start time (HH:mm): ");
                    String startTime = scanner.nextLine();
                    System.out.println("Enter end time (HH:mm): ");
                    String endTime = scanner.nextLine();
                    try {
                        LocalDate localDate = LocalDate.parse(date, formatter);
                        LocalTime startTimeParse = LocalTime.parse(startTime);
                        LocalTime endTimeParse = LocalTime.parse(endTime);
                        doctorController.setAvailabilityForAppointments(doctorID, localDate, startTimeParse, endTimeParse);
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid date format");
                    }
                    break;
                case 5:
                    if (doctorController.viewPendingAppointments(doctorID) != null) {
                        System.out.println("Pending Appointments for Doctor ID: " + doctorID);
                        printAppointmentDetails(doctorController.viewPendingAppointments(doctorID));
                        System.out.println("Which Appointment do you want to accept/decline?");
                        String appointmentID = scanner.nextLine();
                        System.out.println("Press 1 to accept, Press 2 to decline");
                        String outcome = scanner.nextLine();
                        if (outcome.equals("1")) {
                            doctorController.appointmentRequestOutcome(appointmentID, "Confirmed");
                        } else if (outcome.equals("2")) {
                            doctorController.appointmentRequestOutcome(appointmentID, "Cancelled");
                        } else {
                            System.out.println("Invalid outcome");
                        }
                    }
                    break;
                case 6:
                    System.out.println("Confirmed Appointments for Doctor ID: " + doctorID);
                    printAppointmentDetails(doctorController.viewUpComingAppointments(doctorID));
                    break;
                case 7:
                    System.out.println("Insert Record Appointment Outcome function");
                    System.out.println("Which appointment do you want to record the outcome of?");
                    String appointmentID = scanner.nextLine();
                    if(doctorController.recordAppointmentOutcome(appointmentID , doctorID)) {
                        Appointment appointment = doctorController.findAppointment(appointmentID);
                        medicalRecord = askingForMedicalRecordDetails();
                        doctorController.createNewMedicalRecord(doctorID, appointment.getPatientID(), medicalRecord.getPastDiagnosis(), medicalRecord.getTreatments(), medicalRecord.getPrescribeMedications());
                    }
                    break;
                case 8:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void printMedicalRecordDetails(Map<String, MedicalRecord> medicalRecords) {
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
                    System.out.printf(" - %s (%s) Status: %s %n", medication.getMedicineName(), medication.getQuantity(), medication.getStatus());
                }
            } else {
                System.out.println("Prescribed Medications: None");
            }

            System.out.println("------------------------------------------");
        }
        System.out.println("==========================================");
    }

    private MedicalRecord askingForMedicalRecordDetails (){
        // Get new diagnosis details
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.println("New Diagnosis Name: ");
        String newDiagnosisName = scanner.nextLine();
        System.out.println("New Diagnosis Date (yyyy-MM-dd): ");
        LocalDate newDiagnosisDate = LocalDate.parse(scanner.nextLine(), formatter);
        System.out.println("New Diagnosis Details: ");
        String newDiagnosisDetails = scanner.nextLine();
        PastDiagnosis newDiagnosis = new PastDiagnosis(newDiagnosisName, newDiagnosisDate, newDiagnosisDetails);

        // Get new treatment details
        System.out.println("New Treatment Name: ");
        String newTreatmentName = scanner.nextLine();
        System.out.println("New Treatment Date (yyyy-MM-dd): ");
        LocalDate newTreatmentDate = LocalDate.parse(scanner.nextLine(), formatter);
        System.out.println("New Treatment Details: ");
        String newTreatmentDetails = scanner.nextLine();
        Treatments newTreatment = new Treatments(newTreatmentName, newTreatmentDate, newTreatmentDetails);

        // Get new prescribed medications
        List<PrescribeMedications> newPrescribeMedications = new ArrayList<>();
        String addMore;
        do {
            System.out.println("Enter Medication Name: ");
            String medicationName = scanner.nextLine();
            System.out.println("Enter Dosage: ");
            int dosage = parseInt(scanner.nextLine());
            newPrescribeMedications.add(new PrescribeMedications(medicationName, dosage, "not dispensed"));

            System.out.println("Do you want to add another medication? (yes/no)");
            addMore = scanner.nextLine();
        } while (addMore.equalsIgnoreCase("yes"));

        return new MedicalRecord(null, null, null, newDiagnosis, newTreatment, newPrescribeMedications);
    }

    private void printAppointmentDetails(HashMap<String, Appointment> appointments) {
        System.out.println("------------------------------------------");

        // Iterate through the HashMap and print details
        for (Map.Entry<String, Appointment> entry : appointments.entrySet()) {
            String appointmentID = entry.getKey();
            Appointment appointment = entry.getValue();

            System.out.println("Appointment ID: " + appointmentID);
            System.out.println("Details: ");
            System.out.println("PatientID: " + appointment.getPatientID());
            System.out.println("Date: " + appointment.getAppointmentDate());
            System.out.println("Start Time: " + appointment.getAppointmentStartTime());
            System.out.println("End Time: " + appointment.getAppointmentEndTime());
            System.out.println("------------------------------------------");
        }
    }
}


