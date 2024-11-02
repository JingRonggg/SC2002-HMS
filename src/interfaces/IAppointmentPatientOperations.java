package src.interfaces;

import src.model.Appointment;
import java.util.HashMap;

public interface IAppointmentPatientOperations {
    HashMap<String, Appointment> getAllPatientAppointment(String patientID);
    HashMap<String, Appointment> getScheduledPatientAppointment(String patientID);
    HashMap<String, Appointment> getCompletedPatientAppointment(String patientID);
}