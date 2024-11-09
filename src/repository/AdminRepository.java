package src.repository;

import src.interfaces.IAdminRepository;
import src.model.*;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Repository class for managing administrator and staff-related operations
 * Extends UserRepository and implements IAdminRepository interface
 */
public class AdminRepository extends UserRepository implements IAdminRepository {
    /**
     * Default constructor
     */
    public AdminRepository() {}

    /**
     * Retrieves all staff members from the repository
     * @return Collection of Staff objects
     */
    @Override
    public Collection<Staff> getAllStaff() {
        Collection<Staff> Staffs = new ArrayList<>();
        // System.out.println(users);
        for (User user : users.values()) {
            if (user instanceof Staff) {
                Staffs.add((Staff) user);
            }
        }
        return Staffs;
    }

    /**
     * Retrieves all doctors from the repository
     * @return Collection of Doctor objects
     */
    @Override
    public Collection<Doctor> getAllDoctors() {
        Collection<Doctor> Doctors = new ArrayList<>();
        for (User user : users.values()) {
            if (user instanceof Doctor) {
                Doctors.add((Doctor) user);
            }
        }
        return Doctors;
    }

    /**
     * Retrieves all pharmacists from the repository
     * @return Collection of Pharmacist objects
     */
    @Override
    public Collection<Pharmacist> getAllPharmacists() {
        Collection<Pharmacist> Pharmacists = new ArrayList<>();
        for (User user : users.values()) {
            if (user instanceof Pharmacist) {
                Pharmacists.add((Pharmacist) user);
            }
        }
        return Pharmacists;
    }

    /**
     * Retrieves all administrators from the repository
     * @return Collection of Administrator objects
     */
    @Override
    public Collection<Administrator> getAllAdministrators() {
        Collection<Administrator> Administrators = new ArrayList<>();
        for (User user : users.values()) {
            if (user instanceof Administrator) {
                Administrators.add((Administrator) user);
            }
        }
        return Administrators;
    }

    /**
     * Adds a new staff member to the repository
     * @param staff The Staff object to be added
     */
    @Override
    public void addStaff(Staff staff) {
        addUser(staff);
    }

    /**
     * Updates an existing staff member's information
     * @param staff The Staff object with updated information
     */
    @Override
    public void updateStaff(Staff staff) {
        updateUser(staff);
    }

    /**
     * Removes a staff member from the repository
     * @param hospitalID The hospital ID of the staff member to be removed
     * @return true if staff member was successfully removed, false otherwise
     */
    @Override
    public boolean removeStaff(String hospitalID) {
        return deleteUser(hospitalID);
    }

    /**
     * Retrieves a doctor's name using their ID
     * @param doctorID The ID of the doctor
     * @return The doctor's name if found, null otherwise
     */
    public String getDoctorName(String doctorID) {
        User user = users.get(doctorID);
        if (user instanceof Doctor) {
            return ((Doctor) user).getName();
        }
        return null;
    }

    /**
     * Checks if an administrator exists in the repository
     * @param adminID The ID of the administrator to check
     * @return true if administrator exists, false otherwise
     */
    public boolean adminExists(String adminID) {
        User user = users.get(adminID);
        if (user instanceof Administrator) {
            return true;
        }
        return false;
    }
}
