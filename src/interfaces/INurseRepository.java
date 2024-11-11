package src.interfaces;

import src.model.Patient;

/**
 * Repository interface for managing nurse-related operations in the system.
 * Extends the base user repository interface with nurse-specific functionality.
 */
public interface INurseRepository extends IUserRepository {
    /**
     * Adds a new patient under the nurse's care.
     *
     * @param patient The patient to be added
     * @return true if the patient was successfully added, false otherwise
     */
    boolean addPatient(Patient patient);
}