package src.controller;

import src.appointment.Appointment;
import src.appointment.AppointmentRepository;
import src.appointment.IAppointmentRepository;
import src.repository.AdminRepository;
import src.repository.IAdminRepository;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class DoctorController {
    private final IAppointmentRepository appointmentRepository;
    private final IAdminRepository adminRepository;

    public DoctorController(IAppointmentRepository appointmentRepository, IAdminRepository adminrepository) {
        this.appointmentRepository = appointmentRepository;
        this.adminRepository = adminrepository;
    }

    public void viewPatientMedicalRecords() {}

    public void updatePatientMedicalRecords() {}

    // shows upcoming appointments + available slots
    public void viewPersonalSchedule(String doctorID, LocalDate date) {
        viewUpComingAppointments(doctorID);

        LocalTime startTime = LocalTime.of(8,0);
        LocalTime endTime = LocalTime.of(17,0);
        LocalTime currentTime = startTime;
        ArrayList<Appointment> availableSlots = new ArrayList<>();
        while (currentTime.isBefore(endTime)) {
            LocalTime nextTime = currentTime.plusHours(1);
            //TODO change the isSlotAvailable, currently will display all appointment booked
            if (appointmentRepository.isSlotAvailable(doctorID, date, currentTime, nextTime)) {
                availableSlots.add(new Appointment(doctorID, null, null, date, currentTime, nextTime, "available"));
            }
            currentTime = currentTime.plusMinutes(30);
        }

        availableSlots.sort(Comparator.comparing(Appointment::getAppointmentStartTime));

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

    public void setAvailabilityForAppointments(String doctorID, LocalDate date, LocalTime startTime, LocalTime endTime) {
        String doctorName = adminRepository.getDoctorName(doctorID);
        if (doctorName == null) {
            System.out.println("Doctor not found.");
        } else if (!appointmentRepository.isSlotAvailable(doctorID, date, startTime, endTime)) {
            System.out.println("You have an appointment at this time!");
        } else {
            Appointment appointment = new Appointment(doctorID, null, doctorName, date, startTime, endTime, "Not Free");
            appointmentRepository.saveAppointment(appointment);
        }
    }

    public void appointmentRequestOutcome(String appointmentID, String status) {

        Appointment appointment = appointmentRepository.getSpecificAppointment(appointmentID);
        if (appointment == null) {
            System.out.println("Appointment not found.");
        } else {
            if (Objects.equals(status, "Cancelled") || Objects.equals(status, "Confirmed")) {
                appointment.setStatus(status);
                appointmentRepository.saveAppointment(appointment);
            } else {
                System.out.println("No such status");
            }
        }
    }

    public void viewUpComingAppointments(String doctorID) {
        HashMap<String, Appointment> confirmedAppointments = appointmentRepository.getAllConfirmedAppointment(doctorID);
        System.out.println("Confirmed Appointments for Doctor ID: " + doctorID);
        printDetails(confirmedAppointments);
    }

    public void recordAppointmentOutcome() {}

    public boolean viewPendingAppointments(String doctorID) {
        HashMap<String, Appointment> pendingAppointments = appointmentRepository.getAllPendingAppointment(doctorID);
        System.out.println("Pending Appointments for Doctor ID: " + doctorID);
        if (pendingAppointments != null){
            printDetails(pendingAppointments);
            return true;
        } else {
            System.out.println("No pending appointments found for Doctor ID: " + doctorID);
            return false;
        }
    }

    private void printDetails(HashMap<String, Appointment> Appointments) {
        System.out.println("------------------------------------------");

        // Iterate through the HashMap and print details
        for (Map.Entry<String, Appointment> entry : Appointments.entrySet()) {
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
