package src.interfaces;

import src.model.Appointment;

public interface IAppointmentBasicOperations {
    void saveAppointment(Appointment appointment);
    void addAppointment(String appointmentID, Appointment appointment);
    Appointment getSpecificAppointment(String appointmentID);
    void updateAppointment(String appointmentID, Appointment updatedAppointment);
    void deleteAppointment(String appointmentID);
}