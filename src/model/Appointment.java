package src.model;

import src.enums.AppointmentStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {
    private String doctorID;
    private String patientID;
    private String doctorName;
    private LocalDate appointmentDate;
    private LocalTime appointmentStartTime;
    private LocalTime appointmentEndTime;
    private AppointmentStatus status;

    public Appointment(String doctorID, String patientID, String doctorName,
                       LocalDate appointmentDate, LocalTime appointmentStartTime, LocalTime appointmentEndTime,
                       AppointmentStatus status) {
        this.doctorID = doctorID;
        this.patientID = patientID;
        this.doctorName = doctorName;
        this.appointmentDate = appointmentDate;
        setAppointmentStartTime(appointmentStartTime);
        setAppointmentEndTime(appointmentEndTime);
        this.status = status;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public LocalTime getAppointmentStartTime() {
        return appointmentStartTime;
    }

    public void setAppointmentStartTime(LocalTime appointmentStartTime) {
        if (appointmentEndTime != null && appointmentStartTime.isAfter(appointmentEndTime)) {
            throw new IllegalArgumentException("Start time must be before end time.");
        }
        this.appointmentStartTime = appointmentStartTime;
    }

    public LocalTime getAppointmentEndTime() {
        return appointmentEndTime;
    }

    public void setAppointmentEndTime(LocalTime appointmentEndTime) {
        if (appointmentStartTime != null && appointmentEndTime.isBefore(appointmentStartTime)) {
            throw new IllegalArgumentException("End time must be after start time.");
        }
        this.appointmentEndTime = appointmentEndTime;
    }
}
