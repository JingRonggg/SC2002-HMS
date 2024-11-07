package src.view;

import src.model.Appointment;
import java.util.HashMap;
import java.util.Map;

public class AppointmentPrinter {
    public static void printAppointmentDetails(HashMap<String, Appointment> appointments) {
        System.out.println("------------------------------------------");

        for (Map.Entry<String, Appointment> entry : appointments.entrySet()) {
            String appointmentID = entry.getKey();
            Appointment appointment = entry.getValue();

            System.out.println("Appointment ID: " + appointmentID);
            System.out.println("Details: ");
            System.out.println("PatientID: " + appointment.getPatientID());
            System.out.println("Date: " + appointment.getAppointmentDate());
            System.out.println("Start Time: " + appointment.getAppointmentStartTime());
            System.out.println("End Time: " + appointment.getAppointmentEndTime());
            System.out.println("------------------------------------------");
        }
    }
}