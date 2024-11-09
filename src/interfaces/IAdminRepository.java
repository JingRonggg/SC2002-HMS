package src.interfaces;

import src.model.*;

import java.util.Collection;

/**
 * Interface for managing administrative operations related to hospital staff
 */
public interface IAdminRepository{
    /**
     * Retrieves all staff members in the system
     * @return Collection of all Staff objects
     */
    Collection<Staff> getAllStaff();

    /**
     * Retrieves all doctors in the system
     * @return Collection of all Doctor objects
     */
    Collection<Doctor> getAllDoctors();

    /**
     * Retrieves all pharmacists in the system
     * @return Collection of all Pharmacist objects
     */
    Collection<Pharmacist> getAllPharmacists();

    /**
     * Retrieves all administrators in the system
     * @return Collection of all Administrator objects
     */
    Collection<Administrator> getAllAdministrators();

    /**
     * Adds a new staff member to the system
     * @param staff The Staff object to be added
     */
    void addStaff(Staff staff);

    /**
     * Updates information for an existing staff member
     * @param staff The Staff object with updated information
     */
    void updateStaff(Staff staff);

    /**
     * Removes a staff member from the system
     * @param hospitalID The hospital ID of the staff member to remove
     * @return true if staff member was successfully removed, false otherwise
     */
    boolean removeStaff(String hospitalID);

    /**
     * Retrieves a user by their hospital ID
     * @param hospitalID The hospital ID to search for
     * @return User object if found, null otherwise
     */
    User getUserByHospitalID(String hospitalID);

    /**
     * Checks if a user exists in the system
     * @param hospitalID The hospital ID to check
     * @return true if user exists, false otherwise
     */
    boolean userExists(String hospitalID);

    /**
     * Gets the name of a doctor by their ID
     * @param doctorID The ID of the doctor
     * @return The name of the doctor
     */
    String getDoctorName(String doctorID);

    /**
     * Checks if an administrator exists in the system
     * @param adminID The administrator ID to check
     * @return true if administrator exists, false otherwise
     */
    boolean adminExists(String adminID);
}