package src.repository;

import src.model.AppointmentStatus;
import src.interfaces.IAppointmentRepository;
import src.model.Appointment;
import src.model.AppointmentWrapper;
import src.utils.AppointmentCsvExporter;
import src.utils.AppointmentIDGenerator;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;

/**
 * Repository class for managing appointments in the system.
 * Implements the IAppointmentRepository interface to provide CRUD operations and other appointment-related functionalities.
 */
public class AppointmentRepository implements IAppointmentRepository {
    /** HashMap to store appointments with appointmentID as key and Appointment object as value */
    static HashMap<String, Appointment> appointments = new HashMap<>();

    /**
     * Functional interface for filtering appointments based on custom conditions
     */
    @FunctionalInterface
    private interface AppointmentFilter {
        boolean test(Appointment appointment);
    }

    /**
     * Handles exceptions that occur during appointment operations
     * @param operation The operation during which the exception occurred
     * @param e The exception that was thrown
     */
    private void handleException(String operation, Exception e) {
        System.out.println("An error occurred while " + operation + " the appointment: " + e.getMessage());
    }

    /**
     * Filters appointments based on the provided filter condition
     * @param filter The filter condition to apply
     * @return HashMap containing filtered appointments
     */
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

    /**
     * Adds an appointment with a specific ID
     * @param appointmentID The ID for the appointment
     * @param appointment The appointment to add
     */
    @Override
    public void addAppointment(String appointmentID, Appointment appointment) {
        try {
            appointments.put(appointmentID, appointment);
        } catch (Exception e) {
            System.out.println("An error occurred while adding the appointment: " + e.getMessage());
        }
    }

    /**
     * Saves an appointment with an automatically generated ID
     * @param appointment The appointment to save
     */
    @Override
    public void saveAppointment(Appointment appointment) {
        try {
            String appointmentID = AppointmentIDGenerator.nextAppointmentID();
            appointments.put(appointmentID, appointment);
        } catch (Exception e) {
            System.out.println("An error occurred while saving appointment: " + e.getMessage());
        }
    }

    /**
     * Retrieves a specific appointment by ID
     * @param appointmentID The ID of the appointment to retrieve
     * @return The appointment if found, null otherwise
     */
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

    /**
     * Gets all appointments for a specific patient
     * @param patientID The ID of the patient
     * @return HashMap containing all appointments for the patient
     */
    @Override
    public HashMap<String, Appointment> getAllPatientAppointment(String patientID) {
        return filterAppointments(appointment -> appointment.getPatientID().equals(patientID));
    }

    /**
     * Gets all scheduled (non-completed) appointments for a specific patient
     * @param patientID The ID of the patient
     * @return HashMap containing scheduled appointments for the patient
     */
    @Override
    public HashMap<String, Appointment> getScheduledPatientAppointment(String patientID) {
        return filterAppointments(appointment ->
                appointment.getPatientID() != null && appointment.getPatientID().equals(patientID) &&
                        !appointment.getStatus().equals(AppointmentStatus.COMPLETED) &&
                        !appointment.getStatus().equals(AppointmentStatus.CANCELLED));
    }

    /**
     * Gets all completed appointments for a specific patient
     * @param patientID The ID of the patient
     * @return HashMap containing completed appointments for the patient
     */
    @Override
    public HashMap<String, Appointment> getCompletedPatientAppointment(String patientID) {
        return filterAppointments(appointment ->
                appointment.getPatientID().equals(patientID) &&
                        appointment.getStatus().equals(AppointmentStatus.COMPLETED));
    }

    /**
     * Gets all appointments for a specific doctor
     * @param doctorID The ID of the doctor
     * @return HashMap containing all appointments for the doctor
     */
    @Override
    public HashMap<String, Appointment> getDoctorAppointments(String doctorID) {
        return filterAppointments(appointment -> appointment.getDoctorID().equals(doctorID));
    }

    /**
     * Gets all pending appointments for a specific doctor
     * @param doctorID The ID of the doctor
     * @return HashMap containing pending appointments for the doctor
     */
    @Override
    public HashMap<String, Appointment> getAllPendingAppointment(String doctorID) {
        return filterAppointments(appointment ->
                appointment.getDoctorID().equals(doctorID) &&
                        appointment.getStatus().equals(AppointmentStatus.PENDING));
    }

    /**
     * Gets all confirmed appointments for a specific doctor
     * @param doctorID The ID of the doctor
     * @return HashMap containing confirmed appointments for the doctor
     */
    @Override
    public HashMap<String, Appointment> getAllConfirmedAppointment(String doctorID) {
        return filterAppointments(appointment ->
                appointment.getDoctorID().equals(doctorID) &&
                        appointment.getStatus().equals(AppointmentStatus.CONFIRMED));
    }

    /**
     * Gets all completed appointments in the system
     * @return HashMap containing all completed appointments
     */
    @Override
    public HashMap<String, Appointment> getAllCompletedAppointment() {
        return filterAppointments(appointment ->
                appointment.getStatus().equals(AppointmentStatus.COMPLETED));
    }

    /**
     * Gets all appointments in the system
     * @return HashMap containing all appointments
     */
    @Override
    public HashMap<String, Appointment> getAllAppointment() {
        HashMap<String, Appointment> allAppointments = new HashMap<>();
        try {
            for (String appointmentID : appointments.keySet()) {
                Appointment appointment = appointments.get(appointmentID);
                if (!appointment.getStatus().equals(AppointmentStatus.NOT_AVAILABLE) && !appointment.getPatientID().equals("null")){
                    allAppointments.put(appointmentID, appointment);
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred while getting the appointment: " + e.getMessage());
        }
        return allAppointments;
    }

    /**
     * Gets all appointments that have pending medications
     * @return HashMap containing appointments with pending medications
     */
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

    /**
     * Checks if a time slot is available for a doctor
     * @param doctorID The ID of the doctor
     * @param date The date to check
     * @param startTime The start time of the slot
     * @param endTime The end time of the slot
     * @return true if the slot is available, false otherwise
     */
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

    /**
     * Updates an existing appointment
     * @param appointmentID The ID of the appointment to update
     * @param updatedAppointment The updated appointment information
     */
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

    /**
     * Deletes an appointment from the system
     * @param appointmentID The ID of the appointment to delete
     */
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

    /**
     * Saves all appointments to a CSV file
     */
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
