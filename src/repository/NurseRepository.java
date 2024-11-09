package src.repository;

import src.interfaces.INurseRepository;
import src.model.Patient;
import src.model.Appointment;
import src.model.User;
import src.enums.AppointmentStatus;
import java.util.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Repository class for managing nurse-related operations.
 * Extends UserRepository and implements INurseRepository interface.
 */
public class NurseRepository extends UserRepository implements INurseRepository {

    /**
     * Default constructor for NurseRepository.
     */
    public NurseRepository() {
    }

    /**
     * Adds a new patient to the repository if they don't already exist.
     *
     * @param patient The patient object to be added
     * @return true if patient was successfully added, false if patient already exists
     */
    @Override
    public boolean addPatient(Patient patient) {
        if (!userExists(patient.getHospitalID())) {
            addUser(patient);
            return true;
        }
        return false;
    }
}