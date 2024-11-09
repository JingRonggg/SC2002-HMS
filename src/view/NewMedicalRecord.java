package src.view;

import src.controller.DoctorController;
import src.model.Appointment;
import src.model.MedicalRecord;

/**
 * A class that handles creation of new medical records in the system
 */
public class NewMedicalRecord {
    /**
     * Creates a new medical record for a patient after their appointment
     * 
     * @param doctorController The controller handling doctor-related operations
     * @param doctorID The unique identifier of the doctor creating the record
     * @param appointmentID The unique identifier of the appointment
     * @param consultationNotes Notes from the consultation with the patient
     */
    public static void newMedicalRecord(DoctorController doctorController, String doctorID, String appointmentID, String consultationNotes) {
        try{
            if(doctorController.recordAppointmentOutcome(appointmentID , doctorID, consultationNotes)) {
                Appointment appointment = doctorController.findAppointment(appointmentID);
                MedicalRecord medicalRecord = MedicalRecordRecorder.askingForMedicalRecordDetails();
                doctorController.createNewMedicalRecord(doctorID, appointment.getPatientID(), 
                    medicalRecord.getPastDiagnosis(), medicalRecord.getTreatments(), 
                    medicalRecord.getPrescribeMedications());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
