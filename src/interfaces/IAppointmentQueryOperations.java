package src.interfaces;

import src.model.Appointment;

import java.util.HashMap;

public interface IAppointmentQueryOperations {
    HashMap<String, Appointment> getAllAppointment();
    HashMap<String, Appointment> getAllCompletedAppointment();
    HashMap<String, Appointment> getPendingMedicationAppointments();
}