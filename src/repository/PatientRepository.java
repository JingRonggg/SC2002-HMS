package src.repository;

import src.interfaces.IPatientRepository;
import src.model.Patient;
import src.model.User;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Repository class for managing Patient data and operations.
 * Extends UserRepository and implements IPatientRepository interface.
 */
public class PatientRepository extends UserRepository implements IPatientRepository {
    
    /**
     * Default constructor for PatientRepository.
     */
    public PatientRepository() {}

    /**
     * Retrieves all patients from the repository.
     *
     * @return Collection of all Patient objects in the repository
     */
    @Override
    public Collection<Patient> getAllPatients() {
        Collection<Patient> patients = new ArrayList<>();
        for (User user : users.values()) {
            if (user instanceof Patient) {
                patients.add((Patient) user);
            }
        }
        return patients;
    }

    /**
     * Retrieves patient information based on hospital ID.
     *
     * @param hospitalID The unique hospital identifier for the patient
     * @return Patient object if found, null otherwise
     */
    @Override
    public Patient getPatientInfo(String hospitalID) {
        for (User user : users.values()) {
            if (user instanceof Patient) {
                if (((Patient) user).getHospitalID().equals(hospitalID)) {
                    return (Patient) user;
                }
            }
        }
        System.out.println("No such patient");
        return null;
    }

    /**
     * Updates the email address for a patient.
     *
     * @param hospitalID The unique hospital identifier for the patient
     * @param newEmail The new email address to be set
     * @return true if update successful, false otherwise
     */
    @Override
    public boolean updatePatientEmail(String hospitalID, String newEmail) {
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.com$";

        if (!newEmail.matches(emailRegex)) {
            System.out.println("Invalid email format. Please provide a valid email address.");
            return false;
        }

        Patient patient = getPatientInfo(hospitalID);
        if (patient != null) {
            patient.setEmailAddress(newEmail);
            return true;
        }
        System.out.println("Unable to update email. Patient not found.");
        return false;
    }
}
