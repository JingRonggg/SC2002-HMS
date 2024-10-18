package src.controller;

import src.model.Staff;
import src.repository.IAdminRepository;
import src.repository.AdminRepository;

import java.util.Collection;

public class AdminController {
    private static IAdminRepository staffRepo;

    public AdminController() {
        staffRepo = new AdminRepository();
    }

    public void viewAllStaff() {
        System.out.println("This are all the staff");
        Collection<Staff> staffs = staffRepo.getAllStaff();
        if (staffs.isEmpty()) {
            System.out.println("No staff members found.");
        } else {
            for (Staff staff : staffs) {
                System.out.println("ID: " + staff.getHospitalID() + ", Name: " + staff.getName() + ", Role: " + staff.getRole() + ", Gender: " + staff.getGender() + ", Age: " + staff.getAge());
            }
        }
    }
}
