package src.view;

import src.controller.DoctorController;
import src.model.Appointment;
import src.model.MedicalRecord;


public class NewMedicalRecord {
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
