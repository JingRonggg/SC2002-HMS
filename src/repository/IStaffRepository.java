package src.repository;

import src.model.Administrator;
import src.model.Doctor;
import src.model.Pharmacist;
import src.model.Staff;

import java.util.Collection;

public interface IStaffRepository extends IUserRepository {
    Collection<Staff> getAllStaff();
    Collection<Doctor> getAllDoctors();
    Collection<Pharmacist> getAllPharmacists();
    Collection<Administrator> getAllAdministrators();
}