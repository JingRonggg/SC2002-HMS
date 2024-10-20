package src.appointment;

import src.model.MedicalRecord;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {
    private String appointmentID;
    private String doctorID;
    private String patientID;
    private String doctorName;
    private LocalDate appointmentDate;
    private LocalTime appointmentStartTime;
    private LocalTime appointmentEndTime;
    private String status;
//    TODO add medical record into appointment
//    private MedicalRecord medicalRecord;
    public Appointment(String doctorID, String patientID, String doctorName,
                       LocalDate appointmentDate, LocalTime appointmentStartTime, LocalTime appointmentEndTime,
                       String status) {
        this.doctorID = doctorID;
        this.patientID = patientID;
        this.doctorName = doctorName;
        this.appointmentDate = appointmentDate;
        setAppointmentStartTime(appointmentStartTime);
        setAppointmentEndTime(appointmentEndTime);
        this.status = status;
//        this.medicalRecord = medicalRecord;
    }

    public String getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(String appointmentID) {
        this.appointmentID = appointmentID;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
