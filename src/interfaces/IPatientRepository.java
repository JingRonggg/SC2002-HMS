package src.interfaces;

import src.model.Patient;

import java.util.Collection;

/**
 * Repository interface for managing patient-related operations in the system.
 */
public interface IPatientRepository {
    /**
     * Retrieves all patients registered in the system.
     * 
     * @return Collection of all Patient objects
     */
    Collection<Patient> getAllPatients();

    /**
     * Retrieves detailed information for a specific patient.
     * 
     * @param hospitalID The unique hospital identifier for the patient
     * @return Patient object containing the patient's information
     */
    Patient getPatientInfo(String hospitalID);

    /**
     * Updates the email address for a specific patient.
     * 
     * @param hospitalID The unique hospital identifier for the patient
     * @param newEmail The new email address to be set
     * @return true if the email was successfully updated, false otherwise
     */
    boolean updatePatientEmail(String hospitalID, String newEmail);
}
