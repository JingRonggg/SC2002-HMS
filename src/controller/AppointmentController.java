package src.controller;

import src.model.Appointment;
import src.repository.AppointmentRepository;
import src.interfaces.IAppointmentRepository;
import src.utils.AppointmentLoader;

public class AppointmentController {
    private static IAppointmentRepository appointmentRepository;

    // Constructor
    public AppointmentController(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
        loadAppointments();
    }

    private void loadAppointments() {
        AppointmentLoader appointmentLoader = new AppointmentLoader();
        appointmentLoader.loadAppointments();
    }

    // Method to add an appointment
    public static void addAppointment(String appointmentID, Appointment appointment) {
        try {
            // Add the appointment to the repository
            appointmentRepository.addAppointment(appointmentID, appointment);
            System.out.println("Appointment added successfully with ID: " + appointmentID);
        } catch (Exception e) {
            System.out.println("An error occurred while adding the appointment: " + e.getMessage());
        }
    }
}
