package src.repository;

import src.model.Appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
// table is connected through FK of patientID and doctorID
public interface IAppointmentRepository {
    //create
    void saveAppointment(Appointment appointment);
    void addAppointment(String appointmentID, Appointment appointment);
    //read
    public Appointment getSpecificAppointment(String appointmentID);
    HashMap<String, Appointment> getAllPatientAppointment(String patientID);
    HashMap<String, Appointment> getScheduledPatientAppointment(String patientID);
    HashMap<String, Appointment> getDoctorAppointments(String doctorID);
    HashMap<String, Appointment> getCompletedPatientAppointment(String patientID);
    HashMap<String, Appointment> getPendingMedicationAppointments();
    boolean isSlotAvailable(String doctorID, LocalDate date, LocalTime startTime, LocalTime endTime);
    public HashMap<String, Appointment> getAllPendingAppointment(String doctorID);
    public HashMap<String, Appointment> getAllConfirmedAppointment(String doctorID);
    public HashMap<String, Appointment> getAllAppointment();
    HashMap<String, Appointment> getAllCompletedAppointment();

    //update
    void updateAppointment(String appointmentID, Appointment updatedAppointment);

    //delete
    void deleteAppointment(String appointmentID);

    void saveAllToCsv();
}
