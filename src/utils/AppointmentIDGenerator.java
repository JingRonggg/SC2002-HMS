package src.utils;

public class AppointmentIDGenerator {
    private static int appointmentID = 0;

    public static synchronized String nextAppointmentID() {
        return String.valueOf(appointmentID++);
    }
}
