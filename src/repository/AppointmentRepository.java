package src.repository;

import src.interfaces.IAppointmentRepository;
import src.model.Appointment;
import src.enums.AppointmentStatus;
import src.model.AppointmentWrapper;
import src.utils.AppointmentCsvExporter;
import src.utils.AppointmentIDGenerator;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;

public class AppointmentRepository implements IAppointmentRepository {
    static HashMap<String, Appointment> appointments = new HashMap<>();

    @FunctionalInterface
    private interface AppointmentFilter {
        boolean test(Appointment appointment);
    }

    private void handleException(String operation, Exception e) {
        System.out.println("An error occurred while " + operation + " the appointment: " + e.getMessage());
    }


    private HashMap<String, Appointment> filterAppointments(AppointmentFilter filter) {
        HashMap<String, Appointment> filteredAppointments = new HashMap<>();
        try {
            appointments.forEach((appointmentID, appointment) -> {
                if (filter.test(appointment)) {
                    filteredAppointments.put(appointmentID, appointment);
                }
            });
        } catch (Exception e) {
            handleException("getting", e);
        }
        return filteredAppointments;
    }

    @Override
    public void addAppointment(String appointmentID, Appointment appointment) {
        try {
            appointments.put(appointmentID, appointment);
        } catch (Exception e) {
            System.out.println("An error occurred while adding the appointment: " + e.getMessage());
        }
    }

    @Override
    public void saveAppointment(Appointment appointment) {
        try {
            String appointmentID = AppointmentIDGenerator.nextAppointmentID();
            appointments.put(appointmentID, appointment);
        } catch (Exception e) {
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

    @Override
    public HashMap<String, Appointment> getAllPatientAppointment(String patientID) {
        return filterAppointments(appointment -> appointment.getPatientID().equals(patientID));
    }

    @Override
    public HashMap<String, Appointment> getScheduledPatientAppointment(String patientID) {
        return filterAppointments(appointment ->
                appointment.getPatientID().equals(patientID) &&
                        appointment.getStatus().equals(AppointmentStatus.CONFIRMED));
    }

    @Override
    public HashMap<String, Appointment> getCompletedPatientAppointment(String patientID) {
        return filterAppointments(appointment ->
                appointment.getPatientID().equals(patientID) &&
                        appointment.getStatus().equals(AppointmentStatus.COMPLETED));
    }

    @Override
    public HashMap<String, Appointment> getDoctorAppointments(String doctorID) {
        return filterAppointments(appointment -> appointment.getDoctorID().equals(doctorID));
    }

    @Override
    public HashMap<String, Appointment> getAllPendingAppointment(String doctorID) {
        return filterAppointments(appointment ->
                appointment.getDoctorID().equals(doctorID) &&
                        appointment.getStatus().equals(AppointmentStatus.PENDING));
    }

    @Override
    public HashMap<String, Appointment> getAllConfirmedAppointment(String doctorID) {
        return filterAppointments(appointment ->
                appointment.getDoctorID().equals(doctorID) &&
                        appointment.getStatus().equals(AppointmentStatus.CONFIRMED));
    }

    @Override
    public HashMap<String, Appointment> getAllCompletedAppointment() {
        return filterAppointments(appointment ->
                appointment.getStatus().equals(AppointmentStatus.COMPLETED));
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

    @Override
    public HashMap<String, Appointment> getPendingMedicationAppointments() {
        HashMap<String, Appointment> patientPendingMedicationAppointments = new HashMap<>();
        try {
            for (String appointmentID : appointments.keySet()) {
                Appointment appointment = appointments.get(appointmentID);
                if (appointment.getStatus().equals(AppointmentStatus.COMPLETED)) {
                    patientPendingMedicationAppointments.put(appointmentID, appointment);
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred while getting the appointment: " + e.getMessage());
        }
        return patientPendingMedicationAppointments;
    }

    @Override
    public boolean isSlotAvailable(String doctorID, LocalDate date, LocalTime startTime, LocalTime endTime) {
        try {
            for (Appointment appointment : appointments.values()) {
                if (appointment.getDoctorID().equals(doctorID) && appointment.getAppointmentDate().equals(date)) {
                    if (!appointment.getStatus().equals(AppointmentStatus.CANCELLED) && !appointment.getStatus().equals(AppointmentStatus.PENDING)) {
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
    public void saveAllToCsv() {
        try {
            for (String appointmentID : appointments.keySet()) {
                Appointment appointment = appointments.get(appointmentID);
                AppointmentWrapper appointmentWithID = new AppointmentWrapper(appointmentID, appointment);
                AppointmentCsvExporter.exportAppointmentsToCsv(appointmentWithID);
            }
        } catch (Exception e) {
            System.out.println("An error occurred while saving appointments to CSV: " + e.getMessage());
        }
    }
}
