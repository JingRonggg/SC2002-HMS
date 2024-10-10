package src.controller;

import src.model.Staff;
import src.repository.IStaffRepository;

import java.util.Collection;

public class AdminController {
    private final IStaffRepository staffRepo;

    public AdminController(IStaffRepository staffRepo) {
        this.staffRepo = staffRepo;
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
