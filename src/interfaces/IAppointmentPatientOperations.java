package src.interfaces;

import src.model.Appointment;

import java.util.HashMap;

/**
 * Interface defining operations related to patient appointments
 */
public interface IAppointmentPatientOperations {
    /**
     * Gets all appointments for a specific patient
     * @param patientID The unique identifier of the patient
     * @return HashMap containing appointments mapped by their IDs
     */
    HashMap<String, Appointment> getAllPatientAppointment(String patientID);

    /**
     * Gets all scheduled (upcoming) appointments for a specific patient
     * @param patientID The unique identifier of the patient
     * @return HashMap containing scheduled appointments mapped by their IDs
     */
    HashMap<String, Appointment> getScheduledPatientAppointment(String patientID);

    /**
     * Gets all completed appointments for a specific patient
     * @param patientID The unique identifier of the patient
     * @return HashMap containing completed appointments mapped by their IDs
     */
    HashMap<String, Appointment> getCompletedPatientAppointment(String patientID);
}