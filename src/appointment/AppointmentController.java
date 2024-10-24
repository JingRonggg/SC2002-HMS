package src.appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;

public class AppointmentController {
    private final IAppointmentRepository appointmentRepository;

    public AppointmentController(IAppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public void createAppointment(Appointment appointment) {
        appointmentRepository.saveAppointment(appointment);
    }

    public Appointment getSpecificAppointment(String appointmentId) {
        return appointmentRepository.getSpecificAppointment(appointmentId);
    }

//    public HashMap<String, Appointment> getPatientAppointments(String patientID) {
//        return appointmentRepository.getPatientAppointment(patientID);
//    }

    public HashMap<String, Appointment> getDoctorAppointments(String doctorID) {
        return appointmentRepository.getDoctorAppointments(doctorID);
    }

    public boolean checkSlotAvailability(String doctorID, LocalDate date, LocalTime startTime, LocalTime endTime) {
        return appointmentRepository.isSlotAvailable(doctorID, date, startTime, endTime);
    }
}
