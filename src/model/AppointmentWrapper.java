package src.model;

public class AppointmentWrapper {
    private String appointmentID;
    private Appointment appointment;

    public AppointmentWrapper(String appointmentID, Appointment appointment) {
        this.appointmentID = appointmentID;
        this.appointment = appointment;
    }
    public String getAppointmentID() {
        return appointmentID;
    }
    public void setAppointmentID(String appointmentID) {
        this.appointmentID = appointmentID;
    }
    public Appointment getAppointment() {
        return appointment;
    }
    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }
}
