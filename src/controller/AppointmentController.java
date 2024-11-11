package src.controller;

import src.interfaces.IAppointmentRepository;
import src.model.Appointment;
import src.repository.AppointmentRepository;
import src.utils.AppointmentLoader;

/**
 * Controller class that manages appointment operations including loading, adding and managing appointments.
 * This class serves as the main interface between the application and appointment data.
 */
public class AppointmentController {
    /** Repository interface for managing appointment data storage and retrieval */
    private static IAppointmentRepository appointmentRepository;

    /**
     * Constructs a new AppointmentController with the specified repository.
     * Initializes the controller and loads any existing appointments from storage.
     *
     * @param appointmentRepository The repository implementation for managing appointment data
     * @throws IllegalArgumentException if appointmentRepository is null
     */
    public AppointmentController(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
        loadAppointments();
    }

    /**
     * Loads existing appointments from persistent storage into the repository.
     * Creates a new AppointmentLoader instance to handle the loading process.
     * This method is called during controller initialization.
     */
    private void loadAppointments() {
        AppointmentLoader appointmentLoader = new AppointmentLoader();
        appointmentLoader.loadAppointments();
    }

    /**
     * Adds a new appointment to the system.
     *
     * @param appointmentID The unique identifier for the appointment
     * @param appointment The appointment object containing appointment details
     */
    public static void addAppointment(String appointmentID, Appointment appointment) {
        try {
            // Add the appointment to the repository
            appointmentRepository.addAppointment(appointmentID, appointment);
            // System.out.println("Appointment added successfully with ID: " + appointmentID);
        } catch (Exception e) {
            System.out.println("An error occurred while adding the appointment: " + e.getMessage());
        }
    }
}
