package src.controller;

import src.model.AppointmentStatus;
import src.interfaces.IAdminRepository;
import src.interfaces.IAppointmentRepository;
import src.interfaces.IMedicalRecordRepository;
import src.interfaces.IPatientRepository;
import src.model.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

/**
 * Controller class that handles doctor operations including managing appointments, medical records and patient data.
 * This class serves as the main interface between doctors and the hospital system.
 */
public class DoctorController {
    /** Repository interface for managing appointment data */
    private final IAppointmentRepository appointmentRepository;
    /** Repository interface for managing admin/staff data */
    private final IAdminRepository adminRepository;
    /** Repository interface for managing medical records */
    private final IMedicalRecordRepository medicalRecordRepository;
    /** Repository interface for managing patient data */
    private final IPatientRepository patientRepository;

    /**
     * Constructs a new DoctorController with the specified repositories.
     *
     * @param appointmentRepository Repository for managing appointments
     * @param adminRepository Repository for managing admin data
     * @param medicalRecordRepository Repository for managing medical records
     * @param patientRepository Repository for managing patient data
     */
    public DoctorController(IAppointmentRepository appointmentRepository, IAdminRepository adminRepository, IMedicalRecordRepository medicalRecordRepository, IPatientRepository patientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.adminRepository = adminRepository;
        this.medicalRecordRepository = medicalRecordRepository;
        this.patientRepository = patientRepository;
    }

    /**
     * Handles exceptions by printing an error message.
     *
     * @param operation Description of the operation that failed
     * @param e The exception that occurred
     */
    private void handleException(String operation, Exception e) {
        System.out.println("An error occurred while " + operation + e.getMessage());
    }

    /**
     * Retrieves medical records for a specific doctor and patient.
     *
     * @param doctorID The ID of the doctor
     * @param patientID The ID of the patient
     * @return HashMap containing medical records
     */
    public HashMap<String, MedicalRecord> viewMedicalRecordsUnderDoctor(String doctorID, String patientID) {
        try {
            return medicalRecordRepository.getMedicalRecordsByDoctorAndPatientID(doctorID,patientID);
        } catch (Exception e) {
            handleException("viewing Medical Records", e);
            return new HashMap<>();
        }
    }

    /**
     * Retrieves all patients in the system.
     *
     * @return Collection of all patients
     */
    public Collection<Patient> viewAllPatients() {
        try {
            return patientRepository.getAllPatients();
        } catch (Exception e) {
            handleException("viewing All Patients", e);
            return new ArrayList<>();
        }
    }

    /**
     * Updates an existing medical record with new information.
     *
     * @param medicalRecordID ID of the medical record to update
     * @param newDiagnosis New diagnosis information
     * @param newTreatment New treatment information
     * @param newPrescribeMedications List of new prescribed medications
     */
    public void updatePatientMedicalRecords(String medicalRecordID, PastDiagnosis newDiagnosis, Treatments newTreatment, List<PrescribeMedications> newPrescribeMedications) {
        try {
            MedicalRecord medicalRecord = medicalRecordRepository.getMedicalRecordByID(medicalRecordID);
            if (medicalRecord != null) {
                // Update the medical record with new details
                if (medicalRecordRepository.updateMedicalRecord(medicalRecordID, newDiagnosis, newTreatment, newPrescribeMedications)) {
                    System.out.println("Medical Record Updated");
                } else {
                    System.out.println("Failed to update Medical Record");
                }
            } else {
                System.out.println("Medical Record not found");
            }
        } catch (Exception e) {
            handleException("Updating Medical Record", e);
        }
    }

    /**
     * Checks if a medical record exists.
     *
     * @param medicalRecordID ID of the medical record to check
     * @return true if record exists, false otherwise
     */
    public boolean medicalRecordExists(String medicalRecordID) {
        try {
            return medicalRecordRepository.getMedicalRecordByID(medicalRecordID) != null;
        } catch (Exception e) {
            handleException("Checking Medical Record", e);
            return false;
        }
    }

    /**
     * Views a doctor's schedule for a specific date.
     *
     * @param doctorID ID of the doctor
     * @param date Date to check schedule for
     * @return ArrayList of available appointment slots
     */
    public ArrayList<Appointment> viewPersonalSchedule(String doctorID, LocalDate date) {
        try {
            viewUpComingAppointments(doctorID);

            LocalTime startTime = LocalTime.of(8,0);
            LocalTime endTime = LocalTime.of(17,0);
            LocalTime currentTime = startTime;
            ArrayList<Appointment> availableSlots = new ArrayList<>();
            while (currentTime.isBefore(endTime)) {
                LocalTime nextTime = currentTime.plusMinutes(30);
                if (appointmentRepository.isSlotAvailable(doctorID, date, currentTime, nextTime)) {
                    availableSlots.add(new Appointment(doctorID, null, null, date, currentTime, nextTime, AppointmentStatus.AVAILABLE, null));
                }
                currentTime = currentTime.plusMinutes(30);
            }
            availableSlots.sort(Comparator.comparing(Appointment::getAppointmentStartTime));
            return (availableSlots);
        } catch (Exception e) {
            handleException("Checking Personal Schedule", e);
        }
        return null;
    }

    /**
     * Sets a doctor's availability for appointments.
     *
     * @param doctorID ID of the doctor
     * @param date Date to set availability for
     * @param startTime Start time of availability
     * @param endTime End time of availability
     */
    public void setAvailabilityForAppointments(String doctorID, LocalDate date, LocalTime startTime, LocalTime endTime) {
        try {
            String doctorName = adminRepository.getDoctorName(doctorID);
            if (doctorName == null) {
                System.out.println("Doctor not found.");
            } else if (!appointmentRepository.isSlotAvailable(doctorID, date, startTime, endTime)) {
                System.out.println("You have an appointment at this time!");
            } else {
                Appointment appointment = new Appointment(doctorID, null, doctorName, date, startTime, endTime, AppointmentStatus.NOT_AVAILABLE, null);
                appointmentRepository.saveAppointment(appointment);
            }
        } catch (Exception e) {
            handleException("Setting availability for Appointments", e);
        }
    }

    /**
     * Processes the outcome of an appointment request.
     *
     * @param appointmentID ID of the appointment
     * @param status New status for the appointment
     */
    public void appointmentRequestOutcome(String appointmentID, String status) {
        try {
            Appointment appointment = appointmentRepository.getSpecificAppointment(appointmentID);
            if (appointment == null) {
                System.out.println("Appointment not found.");
            } else {
                if (Objects.equals(status, "Cancelled") || Objects.equals(status, "Confirmed")) {
                    // Check if the slot is available before confirming
                    if (status.equals("Confirmed") && !appointmentRepository.isSlotAvailable(appointment.getDoctorID(), appointment.getAppointmentDate(), appointment.getAppointmentStartTime(), appointment.getAppointmentEndTime())) {
                        System.out.println("Slot is not available. Cancelling the appointment request.");
                        appointment.setStatus(AppointmentStatus.CANCELLED);
                        appointmentRepository.updateAppointment(appointmentID, appointment);
                    } else if (status.equals("Cancelled")) {
                        appointment.setStatus(AppointmentStatus.CANCELLED);
                        appointmentRepository.updateAppointment(appointmentID, appointment);
                    } else {
                        appointment.setStatus(AppointmentStatus.CONFIRMED);
                        appointmentRepository.updateAppointment(appointmentID, appointment);
                    }
                } else {
                    System.out.println("No such status");
                }
            }
        } catch (Exception e) {
            handleException("Processing Appointment Request", e);
        }
    }

    /**
     * Retrieves all upcoming confirmed appointments for a doctor.
     *
     * @param doctorID ID of the doctor
     * @return HashMap of confirmed appointments
     */
    public HashMap<String, Appointment> viewUpComingAppointments(String doctorID) {
        try {
            return appointmentRepository.getAllConfirmedAppointment(doctorID);
        } catch (Exception e) {
            handleException("Viewing Upcoming Appointments", e);
            return new HashMap<>();
        }
    }

    /**
     * Records the outcome of an appointment.
     *
     * @param appointmentID ID of the appointment
     * @param doctorID ID of the doctor
     * @param consultationNotes Notes from the consultation
     * @return true if recording successful, false otherwise
     */
    public boolean recordAppointmentOutcome(String appointmentID, String doctorID, String consultationNotes) {
        try {
            Appointment appointment = appointmentRepository.getSpecificAppointment(appointmentID);
            if (appointment == null) {
                System.out.println("Appointment not found.");
                return false;
            } else {
                if (!Objects.equals(doctorID, appointment.getDoctorID())) {
                    System.out.println("This is not your appointment!");
                    return false;
                } else {
                    appointment.setStatus(AppointmentStatus.COMPLETED);
                    if (consultationNotes != null){
                        appointment.setConsultationNotes(consultationNotes);
                    }
                    return true;
                }
            }
        } catch (Exception e) {
            handleException("Recording Appointment Outcome", e);
            return false;
        }
    }

    /**
     * Creates a new medical record.
     *
     * @param doctorID ID of the doctor creating the record
     * @param patientID ID of the patient
     * @param pastDiagnosis Past diagnosis information
     * @param treatments Treatment information
     * @param newPrescribeMedications List of prescribed medications
     */
    public void createNewMedicalRecord (String doctorID, String patientID, PastDiagnosis pastDiagnosis, Treatments treatments, List<PrescribeMedications> newPrescribeMedications, String appointmentID) {
        try {
            medicalRecordRepository.createMedicalRecord(doctorID, patientID, pastDiagnosis, treatments, newPrescribeMedications,appointmentID);
        } catch (Exception e) {
            handleException("Creating Medical Record", e);
        }
    }

    /**
     * Retrieves all pending appointments for a doctor.
     *
     * @param doctorID ID of the doctor
     * @return HashMap of pending appointments
     */
    public HashMap<String, Appointment> viewPendingAppointments(String doctorID) {
        try {
            return appointmentRepository.getAllPendingAppointment(doctorID);
        } catch (Exception e) {
            handleException("Viewing Pending Appointments", e);
            return new HashMap<>();
        }
    }

    /**
     * Finds a specific appointment by ID.
     *
     * @param appointmentID ID of the appointment to find
     * @return Appointment object if found, null otherwise
     */
    public Appointment findAppointment(String appointmentID) {
        try {
            return appointmentRepository.getSpecificAppointment(appointmentID);
        } catch (Exception e) {
            handleException("Finding Appointment", e);
            return null;
        }
    }
}
