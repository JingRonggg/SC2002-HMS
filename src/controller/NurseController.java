package src.controller;

import src.interfaces.INurseRepository;
import src.model.Patient;
import src.model.Appointment;
import src.enums.AppointmentStatus;
import src.repository.NurseRepository;
import src.repository.AppointmentRepository;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;

public class NurseController {
    private final INurseRepository nurseRepository;
    private final AppointmentRepository appointmentRepository;


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

    public boolean isExistingPatient(String patientID) {
        try {
            return nurseRepository.userExists(patientID);
        } catch (Exception e) {
            System.out.println("Error checking patient existence: " + e.getMessage());
            return false;
        }
    }

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