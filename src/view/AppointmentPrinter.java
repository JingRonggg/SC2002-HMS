package src.view;

import src.model.Appointment;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public static void printAvailableSlots(ArrayList<Appointment> availableSlots, String doctorID, LocalDate date){
        // Print available slots
        if (availableSlots.isEmpty()) {
            System.out.println("No available slots for " + doctorID + " on " + date + ".");
        } else {
            System.out.println("Available slots for " + doctorID + " on " + date + ":");
            System.out.println("-----------------------------------------------------------------------------");

            for (Appointment slot : availableSlots) {
                System.out.printf("From %s to %s%n", slot.getAppointmentStartTime(), slot.getAppointmentEndTime());
            }
        }
    }

    public static void printScheduledAppointments(HashMap<String, Appointment> appointments) {
        // Convert map entries to list for sorting
        List<Map.Entry<String, Appointment>> sortedAppointments = new ArrayList<>(appointments.entrySet());

        // Sort by appointment status
        sortedAppointments.sort((a1, a2) ->
                a1.getValue().getStatus().compareTo(a2.getValue().getStatus()));

        // Track previous status
        String previousStatus = null;

        // Print sorted appointments
        for (Map.Entry<String, Appointment> entry : sortedAppointments) {
            String appointmentID = entry.getKey();
            Appointment appointment = entry.getValue();

            // Only print status header if it's different from previous
            if (previousStatus == null || !previousStatus.equals(appointment.getStatus().toString())) {
                System.out.println("----------------------------------------------------------------------------");
                System.out.println(appointment.getStatus() + " Appointments: ");
                System.out.println("----------------------------------------------------------------------------");
                previousStatus = appointment.getStatus().toString();
            }
            System.out.println("Appointment ID: " + appointmentID);
            System.out.println("Date: " + appointment.getAppointmentDate());
            System.out.println("Appointment Time: " + appointment.getAppointmentStartTime());
            System.out.println("Doctor: " + appointment.getDoctorName());
            System.out.println("Status: " + appointment.getStatus());
            System.out.println("----------------------------------------------------------------------------");
        }
    }
}