package src.interfaces;

import src.model.Appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;

/**
 * Interface defining operations related to doctor appointments
 */
public interface IAppointmentDoctorOperations {
    /**
     * Gets all appointments for a specific doctor
     * @param doctorID The unique identifier of the doctor
     * @return HashMap containing appointments mapped by their IDs
     */
    HashMap<String, Appointment> getDoctorAppointments(String doctorID);

    /**
     * Gets all pending appointments for a specific doctor
     * @param doctorID The unique identifier of the doctor
     * @return HashMap containing pending appointments mapped by their IDs
     */
    HashMap<String, Appointment> getAllPendingAppointment(String doctorID);

    /**
     * Gets all confirmed appointments for a specific doctor
     * @param doctorID The unique identifier of the doctor
     * @return HashMap containing confirmed appointments mapped by their IDs
     */
    HashMap<String, Appointment> getAllConfirmedAppointment(String doctorID);

    /**
     * Checks if a specific time slot is available for a doctor
     * @param doctorID The unique identifier of the doctor
     * @param date The date to check
     * @param startTime The start time of the slot
     * @param endTime The end time of the slot
     * @return true if the slot is available, false otherwise
     */
    boolean isSlotAvailable(String doctorID, LocalDate date, LocalTime startTime, LocalTime endTime);
}