package src.view;

import src.model.AppointmentStatus;
import src.model.Appointment;
import src.model.MedicalRecord;
import src.model.PrescribeMedications;
import src.repository.MedicalRecordRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A utility class for printing appointment-related information.
 */
public class AppointmentPrinter {
    /**
     * Prints detailed information for all appointments in the given HashMap.
     * 
     * @param appointments HashMap containing appointment IDs mapped to Appointment objects
     */
    private static MedicalRecordRepository medicalRecordRepository;

    public static void setMedicalRecordRepository(MedicalRecordRepository repository) {
        medicalRecordRepository = repository;
    }

    public static void printAppointmentDetails(HashMap<String, Appointment> appointments) {
        System.out.println("=======================================================");

        for (Map.Entry<String, Appointment> entry : appointments.entrySet()) {
            String appointmentID = entry.getKey();
            Appointment appointment = entry.getValue();

            System.out.println("Appointment ID: " + appointmentID);
            System.out.println("Details: ");
            System.out.println("DoctorID: " + appointment.getDoctorID());
            System.out.println("PatientID: " + appointment.getPatientID());
            System.out.println("Date: " + appointment.getAppointmentDate());
            System.out.println("Start Time: " + appointment.getAppointmentStartTime());
            System.out.println("End Time: " + appointment.getAppointmentEndTime());
            System.out.println("Appointment status: " + appointment.getStatus());
            if (appointment.getStatus() == AppointmentStatus.COMPLETED && !appointment.getPatientID().equals("null")){
                HashMap<String, MedicalRecord> medicalRecordData = medicalRecordRepository.getMedicalRecordByAppointmentID(appointmentID);
                for (Map.Entry<String, MedicalRecord> medicalRecordEntry : medicalRecordData.entrySet()) {
                    String medicalRecordID = medicalRecordEntry.getKey();
                    MedicalRecord medicalRecord = medicalRecordRepository.getMedicalRecordByID(medicalRecordID);
                    System.out.println("--------Appointment Outcome Record--------");
                    System.out.println("Type of Service: " + medicalRecord.getTreatments().getTreatmentName());
                    // Print Prescribed Medications
                    List<PrescribeMedications> medications = medicalRecord.getPrescribeMedications();
                    if (!medications.isEmpty()) {
                        System.out.println("Prescribed Medications:");
                        for (PrescribeMedications medication : medications) {
                            System.out.printf(" - %s | Status: %s %n", medication.getMedicineName(), medication.getStatus());
                        }
                    } else {
                        System.out.println("Prescribed Medications: None");
                    }
                    System.out.println("Consultation notes: " + appointment.getConsultationNotes());
                }
            }
            System.out.println("=======================================================");
        }
    }

    /**
     * Prints available appointment slots for a specific doctor on a given date.
     * 
     * @param availableSlots ArrayList of available Appointment slots
     * @param doctorID ID of the doctor
     * @param date Date to check for available slots
     */
    public static void printAvailableSlots(ArrayList<Appointment> availableSlots, String doctorID, LocalDate date){
        // Print available slots
        if (availableSlots.isEmpty()) {
            System.out.println("No available slots for " + doctorID + " on " + date + ".");
        } else {
            System.out.println("Available slots for " + doctorID + " on " + date + ":");
            System.out.println("-----------------------------------------------------------------------------");

            for (Appointment slot : availableSlots) {
                System.out.printf("From %s to %s%n", slot.getAppointmentStartTime(), slot.getAppointmentEndTime());
            }
        }
    }

    /**
     * Prints scheduled appointments sorted by their status.
     * Appointments are grouped by status and displayed with relevant details.
     * 
     * @param appointments HashMap containing appointment IDs mapped to Appointment objects
     */
    public static void printScheduledAppointments(HashMap<String, Appointment> appointments) {
        // Convert map entries to list for sorting
        List<Map.Entry<String, Appointment>> sortedAppointments = new ArrayList<>(appointments.entrySet());

        // Sort by appointment status
        sortedAppointments.sort((a1, a2) ->
                a1.getValue().getStatus().compareTo(a2.getValue().getStatus()));

        // Track previous status
        String previousStatus = null;

        // Print sorted appointments
        for (Map.Entry<String, Appointment> entry : sortedAppointments) {
            String appointmentID = entry.getKey();
            Appointment appointment = entry.getValue();

            // Only print status header if it's different from previous
            if (previousStatus == null || !previousStatus.equals(appointment.getStatus().toString())) {
                System.out.println("----------------------------------------------------------------------------");
                System.out.println(appointment.getStatus() + " Appointments: ");
                System.out.println("----------------------------------------------------------------------------");
                previousStatus = appointment.getStatus().toString();
            }
            System.out.println("Appointment ID: " + appointmentID);
            System.out.println("Date: " + appointment.getAppointmentDate());
            System.out.println("Appointment Time: " + appointment.getAppointmentStartTime());
            System.out.println("Doctor: " + appointment.getDoctorName());
            System.out.println("Status: " + appointment.getStatus());
            System.out.println("Consultation notes: " + appointment.getConsultationNotes());
            System.out.println("----------------------------------------------------------------------------");
        }
    }
}