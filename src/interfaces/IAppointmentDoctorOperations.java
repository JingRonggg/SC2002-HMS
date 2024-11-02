package src.interfaces;

import src.model.Appointment;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;

public interface IAppointmentDoctorOperations {
    HashMap<String, Appointment> getDoctorAppointments(String doctorID);
    HashMap<String, Appointment> getAllPendingAppointment(String doctorID);
    HashMap<String, Appointment> getAllConfirmedAppointment(String doctorID);
    boolean isSlotAvailable(String doctorID, LocalDate date, LocalTime startTime, LocalTime endTime);
}