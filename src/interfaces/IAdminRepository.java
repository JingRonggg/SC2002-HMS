package src.interfaces;

import src.model.*;

import java.util.Collection;

public interface IAdminRepository{
    Collection<Staff> getAllStaff();
    Collection<Doctor> getAllDoctors();
    Collection<Pharmacist> getAllPharmacists();
    Collection<Administrator> getAllAdministrators();
    void addStaff(Staff staff);
    void updateStaff(Staff staff);
    boolean removeStaff(String hospitalID);
    User getUserByHospitalID(String hospitalID);
    boolean userExists(String hospitalID);
    String getDoctorName(String doctorID);
    boolean adminExists(String adminID);
}