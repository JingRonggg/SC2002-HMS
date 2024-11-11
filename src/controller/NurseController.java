package src.controller;

import src.interfaces.INurseRepository;
import src.model.Patient;
import src.repository.AppointmentRepository;
import src.repository.NurseRepository;

/**
 * Controller class that manages nurse operations including patient registration and validation.
 * This class serves as the main interface between nurses and patient data.
 */
public class NurseController {
    /** Repository interface for managing nurse and patient data */
    private final INurseRepository nurseRepository;
    /** Repository for managing appointment data */
    private final AppointmentRepository appointmentRepository;

    /**
     * Validates a patient object by checking required fields.
     *
     * @param patient The patient object to validate
     * @return true if patient data is valid, false otherwise
     */
    private boolean isValidPatient(Patient patient) {
        try {
            if (patient == null) return false;

            boolean isValid = patient.getHospitalID() != null &&
                    !patient.getHospitalID().trim().isEmpty() &&
                    patient.getName() != null &&
                    !patient.getName().trim().isEmpty();

            if (!isValid) {
                System.out.println("Warning: Invalid patient data - missing required fields");
            }
            return isValid;

        } catch (Exception e) {
            System.out.println("Error validating patient: " + e.getMessage());
            return false;
        }
    }

    /**
     * Constructs a new NurseController with the specified repositories.
     * Initializes default repositories if null values are provided.
     *
     * @param nurseRepository Repository for managing nurse and patient data
     * @param appointmentRepository Repository for managing appointments
     */
    public NurseController(INurseRepository nurseRepository, AppointmentRepository appointmentRepository) {
        if (nurseRepository == null || appointmentRepository == null) {
            System.out.println("Warning: Attempted to initialize NurseController with null repositories");
            this.nurseRepository = new NurseRepository();
            this.appointmentRepository = new AppointmentRepository();
            return;
        }
        this.nurseRepository = nurseRepository;
        this.appointmentRepository = appointmentRepository;
    }

    /**
     * Checks if a patient exists in the system using their ID.
     *
     * @param patientID The hospital ID of the patient to check
     * @return true if patient exists, false otherwise
     */
    public boolean isExistingPatient(String patientID) {
        try {
            return nurseRepository.userExists(patientID);
        } catch (Exception e) {
            System.out.println("Error checking patient existence: " + e.getMessage());
            return false;
        }
    }

    /**
     * Registers a new patient in the system after validation.
     * Checks for existing patients and validates patient data before registration.
     *
     * @param patient The patient object containing registration details
     * @return String message indicating registration status
     */
    public String registerNewPatient(Patient patient) {
        try {
            if (patient == null || !isValidPatient(patient)) {
                System.out.println("Warning: Invalid patient data provided for registration");
                return "Invalid patient data provided";
            }
            
            // Check if patient already exists
            if (isExistingPatient(patient.getHospitalID())) {
                System.out.println("Warning: Patient with ID " + patient.getHospitalID() + " already exists");
                return "Patient already exists in the system";
            }
            
            boolean success = nurseRepository.addPatient(patient);
            if (success) {
                System.out.println("Success: Patient registered successfully");
                return "Patient registered successfully";
            } else {
                System.out.println("Warning: Patient registration failed");
                return "Patient registration failed";
            }

        } catch (Exception e) {
            System.out.println("Error registering new patient: " + e.getMessage());
            return "Unable to register patient at this time: " + e.getMessage();
        }
    }
}