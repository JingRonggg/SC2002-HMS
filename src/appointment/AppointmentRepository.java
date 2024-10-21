package src.appointment;

import src.utils.AppointmentIDGenerator;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;

public class AppointmentRepository implements IAppointmentRepository {
    private static HashMap<String, Appointment> appointments = new HashMap<>();

    @Override
    public void saveAppointment(Appointment appointment) {
        try{
            String appointmentID = AppointmentIDGenerator.nextAppointmentID();
            appointments.put(appointmentID, appointment);
        } catch (Exception e){
            System.out.println("An error occurred while saving appointment: " + e.getMessage());
        }
    }

    @Override
    public Appointment getSpecificAppointment(String appointmentID) {
        try{
            for (String appointmentIDs : appointments.keySet()) {
                if (appointmentIDs.equals(appointmentID)) {
                    return appointments.get(appointmentID);
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred while getting the appointment: " + e.getMessage());
        }
        return null;
    }

    // TODO move to patient
    //get all method for patientID
    @Override
    public HashMap<String, Appointment> getAllPatientAppointment(String patientID) {
        HashMap<String, Appointment> patientAppointments = new HashMap<>();
        try {
            for (String appointmentID : appointments.keySet()) {
                Appointment appointment = appointments.get(appointmentID);
                if (appointment.getPatientID().equals(patientID)) {
                    patientAppointments.put(appointmentID, appointment);
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred while getting the appointment: " + e.getMessage());
        }
        return patientAppointments;
    }

    //TODO move to patient
    //get all schedule appointments for patient

    @Override
    public HashMap<String, Appointment> getScheduledPatientAppointment(String patientID) {
        HashMap<String, Appointment> patientScheduledAppointments = new HashMap<>();
        try {
            for (String appointmentID : appointments.keySet()) {
                Appointment appointment = appointments.get(appointmentID);
                if (appointment.getPatientID().equals(patientID) && appointment.getStatus().equals("Confirmed")) {
                    patientScheduledAppointments.put(appointmentID, appointment);
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred while getting the appointment: " + e.getMessage());
        }
        return patientScheduledAppointments;
    }

    // TODO move to doctor
    @Override
    public HashMap<String, Appointment> getDoctorAppointments(String doctorID) {
        HashMap<String, Appointment> doctorAppointments = new HashMap<>();
        try {
            for (String appointmentID : appointments.keySet()) {
                Appointment appointment = appointments.get(appointmentID);
                if (appointment.getDoctorID().equals(doctorID)) {
                    doctorAppointments.put(appointmentID, appointment);
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred while getting the appointment: " + e.getMessage());
        }
        return doctorAppointments;
    }


    @Override
    public boolean isSlotAvailable(String doctorID, LocalDate date, LocalTime startTime, LocalTime endTime) {
        try {
            for (Appointment appointment : appointments.values()) {
                if (appointment.getDoctorID().equals(doctorID) && appointment.getAppointmentDate().equals(date)) {
                    if (!appointment.getStatus().equals("Cancelled") && !appointment.getStatus().equals("Pending")) {
                        if (startTime.isBefore(appointment.getAppointmentEndTime()) && endTime.isAfter(appointment.getAppointmentStartTime())) {
                            return false;
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred while checking for slot availability: " + e.getMessage());
        }
        return true;
    }

    @Override
    public void updateAppointment(String appointmentID, Appointment updatedAppointment) {
        try {
            if (appointments.containsKey(appointmentID)) {
                appointments.put(appointmentID, updatedAppointment);
            } else {
                System.out.println("Appointment does not exist, Failed to update appointment");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while updating appointment: " + e.getMessage());
        }
    }

    @Override
    public void deleteAppointment(String appointmentID) {
        try {
            if (appointments.containsKey(appointmentID)) {
                appointments.remove(appointmentID);
            } else {
                System.out.println("Appointment does not exist, Failed to delete appointment");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while deleting appointment: " + e.getMessage());
        }
    }

    @Override
    public HashMap<String, Appointment> getAllPendingAppointment(String doctorID) {
        HashMap<String, Appointment> doctorPendingAppointments = new HashMap<>();
        try {
            for (String appointmentID : appointments.keySet()) {
                Appointment appointment = appointments.get(appointmentID);
                if (appointment.getDoctorID().equals(doctorID) && appointment.getStatus().equals("Pending")) {
                    doctorPendingAppointments.put(appointmentID, appointment);
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred while getting the appointment: " + e.getMessage());
        }
        return doctorPendingAppointments;
    }

    @Override
    public HashMap<String, Appointment> getAllConfirmedAppointment(String doctorID) {
        HashMap<String, Appointment> doctorPendingAppointments = new HashMap<>();
        try {
            for (String appointmentID : appointments.keySet()) {
                Appointment appointment = appointments.get(appointmentID);
                if (appointment.getDoctorID().equals(doctorID) && appointment.getStatus().equals("Confirmed")) {
                    doctorPendingAppointments.put(appointmentID, appointment);
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred while getting the appointment: " + e.getMessage());
        }
        return doctorPendingAppointments;
    }

    @Override
    public HashMap<String, Appointment> getAllAppointment() {
        HashMap<String, Appointment> allAppointments = new HashMap<>();
        try {
            for (String appointmentID : appointments.keySet()) {
                Appointment appointment = appointments.get(appointmentID);
                allAppointments.put(appointmentID, appointment);
                }
        } catch (Exception e) {
            System.out.println("An error occurred while getting the appointment: " + e.getMessage());
        }
        return allAppointments;
    }
}
