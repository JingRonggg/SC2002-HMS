package src.model;

/**
 * A wrapper class for Appointment objects that includes an appointment ID.
 */
public class AppointmentWrapper {
    /** The unique identifier for the appointment */
    private String appointmentID;
    /** The appointment object being wrapped */
    private Appointment appointment;

    /**
     * Constructs a new AppointmentWrapper with the specified ID and appointment.
     * @param appointmentID The unique identifier for the appointment
     * @param appointment The appointment object to wrap
     */
    public AppointmentWrapper(String appointmentID, Appointment appointment) {
        this.appointmentID = appointmentID;
        this.appointment = appointment;
    }

    /**
     * Gets the appointment ID.
     * @return The appointment's unique identifier
     */
    public String getAppointmentID() {
        return appointmentID;
    }

    /**
     * Sets the appointment ID.
     * @param appointmentID The new appointment ID to set
     */
    public void setAppointmentID(String appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**
     * Gets the wrapped appointment object.
     * @return The appointment object
     */
    public Appointment getAppointment() {
        return appointment;
    }

    /**
     * Sets the wrapped appointment object.
     * @param appointment The new appointment object to wrap
     */
    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }
}
