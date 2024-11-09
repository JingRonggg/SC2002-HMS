package src.model;

import src.enums.AppointmentStatus;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents an appointment in the hospital system.
 * Contains information about the doctor, patient, timing and status of the appointment.
 */
public class Appointment {
    private String doctorID;
    private String patientID; 
    private String doctorName;
    private LocalDate appointmentDate;
    private LocalTime appointmentStartTime;
    private LocalTime appointmentEndTime;
    private AppointmentStatus status;
    private String consultationNotes;

    /**
     * Constructs a new Appointment with the specified details.
     *
     * @param doctorID The unique identifier of the doctor
     * @param patientID The unique identifier of the patient
     * @param doctorName The name of the doctor
     * @param appointmentDate The date of the appointment
     * @param appointmentStartTime The start time of the appointment
     * @param appointmentEndTime The end time of the appointment
     * @param status The status of the appointment
     * @param consultationNotes Any notes from the consultation
     */
    public Appointment(String doctorID, String patientID, String doctorName,
                       LocalDate appointmentDate, LocalTime appointmentStartTime, LocalTime appointmentEndTime,
                       AppointmentStatus status, String consultationNotes) {
        this.doctorID = doctorID;
        this.patientID = patientID;
        this.doctorName = doctorName;
        this.appointmentDate = appointmentDate;
        setAppointmentStartTime(appointmentStartTime);
        setAppointmentEndTime(appointmentEndTime);
        this.status = status;
        this.consultationNotes = consultationNotes;
    }

    /**
     * Gets the consultation notes for this appointment.
     *
     * @return The consultation notes
     */
    public String getConsultationNotes() {
        return consultationNotes;
    }

    /**
     * Sets the consultation notes for this appointment.
     *
     * @param consultationNotes The consultation notes to set
     */
    public void setConsultationNotes(String consultationNotes) {
        this.consultationNotes = consultationNotes;
    }

    /**
     * Gets the doctor's ID.
     *
     * @return The doctor's unique identifier
     */
    public String getDoctorID() {
        return doctorID;
    }

    /**
     * Sets the doctor's ID.
     *
     * @param doctorID The doctor's unique identifier to set
     */
    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    /**
     * Gets the patient's ID.
     *
     * @return The patient's unique identifier
     */
    public String getPatientID() {
        return patientID;
    }

    /**
     * Sets the patient's ID.
     *
     * @param patientID The patient's unique identifier to set
     */
    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    /**
     * Gets the doctor's name.
     *
     * @return The doctor's name
     */
    public String getDoctorName() {
        return doctorName;
    }

    /**
     * Sets the doctor's name.
     *
     * @param doctorName The doctor's name to set
     */
    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    /**
     * Gets the appointment date.
     *
     * @return The date of the appointment
     */
    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    /**
     * Sets the appointment date.
     *
     * @param appointmentDate The date to set for the appointment
     */
    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    /**
     * Gets the appointment status.
     *
     * @return The current status of the appointment
     */
    public AppointmentStatus getStatus() {
        return status;
    }

    /**
     * Sets the appointment status.
     *
     * @param status The status to set for the appointment
     */
    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    /**
     * Gets the appointment start time.
     *
     * @return The start time of the appointment
     */
    public LocalTime getAppointmentStartTime() {
        return appointmentStartTime;
    }

    /**
     * Sets the appointment start time.
     * Validates that the start time is before the end time if end time is already set.
     *
     * @param appointmentStartTime The start time to set
     * @throws IllegalArgumentException if the start time is after the end time
     */
    public void setAppointmentStartTime(LocalTime appointmentStartTime) {
        if (appointmentEndTime != null && appointmentStartTime.isAfter(appointmentEndTime)) {
            throw new IllegalArgumentException("Start time must be before end time.");
        }
        this.appointmentStartTime = appointmentStartTime;
    }

    /**
     * Gets the appointment end time.
     *
     * @return The end time of the appointment
     */
    public LocalTime getAppointmentEndTime() {
        return appointmentEndTime;
    }

    /**
     * Sets the appointment end time.
     * Validates that the end time is after the start time if start time is already set.
     *
     * @param appointmentEndTime The end time to set
     * @throws IllegalArgumentException if the end time is before the start time
     */
    public void setAppointmentEndTime(LocalTime appointmentEndTime) {
        if (appointmentStartTime != null && appointmentEndTime.isBefore(appointmentStartTime)) {
            throw new IllegalArgumentException("End time must be after start time.");
        }
        this.appointmentEndTime = appointmentEndTime;
    }
}
