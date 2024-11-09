package src.interfaces;

import src.model.Appointment;

import java.util.HashMap;

/**
 * Interface defining operations for querying appointments in the system.
 */
public interface IAppointmentQueryOperations {
    /**
     * Retrieves all appointments in the system.
     * @return HashMap containing all appointments with appointment IDs as keys
     */
    HashMap<String, Appointment> getAllAppointment();

    /**
     * Retrieves all completed appointments in the system.
     * @return HashMap containing completed appointments with appointment IDs as keys
     */
    HashMap<String, Appointment> getAllCompletedAppointment();

    /**
     * Retrieves all appointments that have pending medications.
     * @return HashMap containing appointments with pending medications, using appointment IDs as keys
     */
    HashMap<String, Appointment> getPendingMedicationAppointments();
}