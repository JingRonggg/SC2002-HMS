package src.interfaces;

import src.model.Appointment;

/**
 * Interface defining basic CRUD operations for appointments
 */
public interface IAppointmentBasicOperations {
    /**
     * Saves an appointment to the system
     * @param appointment The appointment to be saved
     */
    void saveAppointment(Appointment appointment);

    /**
     * Adds a new appointment with the specified ID
     * @param appointmentID The unique identifier for the appointment
     * @param appointment The appointment to be added
     */
    void addAppointment(String appointmentID, Appointment appointment);

    /**
     * Retrieves a specific appointment by its ID
     * @param appointmentID The unique identifier of the appointment to retrieve
     * @return The appointment matching the given ID
     */
    Appointment getSpecificAppointment(String appointmentID);

    /**
     * Updates an existing appointment with new information
     * @param appointmentID The unique identifier of the appointment to update
     * @param updatedAppointment The new appointment information
     */
    void updateAppointment(String appointmentID, Appointment updatedAppointment);

    /**
     * Deletes an appointment from the system
     * @param appointmentID The unique identifier of the appointment to delete
     */
    void deleteAppointment(String appointmentID);
}