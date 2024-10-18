package src.repository;

import src.model.*;

import java.util.ArrayList;
import java.util.Collection;

public class AdminRepository extends UserRepository implements IAdminRepository {
    public AdminRepository() {}

    @Override
    public Collection<Staff> getAllStaff() {
        Collection<Staff> Staffs = new ArrayList<>();
        System.out.println(users);
        for (User user : users.values()) {
            if (user instanceof Staff) {
                Staffs.add((Staff) user);
            }
        }
        return Staffs;
    }

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
}