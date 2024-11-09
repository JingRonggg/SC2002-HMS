package src.controller;

import src.model.Appointment;
import src.interfaces.IAppointmentRepository;
import src.model.Administrator;
import src.model.Doctor;
import src.model.Pharmacist;
import src.model.Staff;
import src.interfaces.IAdminRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Controller class that handles administrative operations for managing staff and appointments.
 */
public class AdminController {
    private final IAdminRepository staffRepo;
    private final IAppointmentRepository appointmentRepo;
    private final Scanner scanner;

    /**
     * Constructs an AdminController with the specified repositories.
     *
     * @param staffRepo The repository for managing staff data
     * @param appointmentRepo The repository for managing appointment data
     */
    public AdminController(IAdminRepository staffRepo, IAppointmentRepository appointmentRepo) {
        this.staffRepo = staffRepo;
        this.appointmentRepo = appointmentRepo;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays all staff members in the system.
     * Prints staff details including ID, name, role, gender and age.
     */
    public void viewAllStaff() {
        try {
            System.out.println("These are all the staff:");
            Collection<Staff> staffs = staffRepo.getAllStaff();
            if (staffs.isEmpty()) {
                System.out.println("No staff members found.");
            } else {
                for (Staff staff : staffs) {
                    System.out.println("ID: " + staff.getHospitalID() + ", Name: " + staff.getName() + ", Role: " + staff.getRole() + ", Gender: " + staff.getGender() + ", Age: " + staff.getAge());
                }
            }
        } catch (Exception e) {
            System.out.println("Error viewing staff: " + e.getMessage());
        }
    }

    /**
     * Adds a new staff member to the system.
     * Collects staff details through user input and creates appropriate staff object.
     */
    public void addStaff() {
        try {
            System.out.println("Enter staff details:");
            Staff newStaff = collectStaffDetails();
            if (newStaff != null) {
                staffRepo.addStaff(newStaff);
                System.out.println("Staff added successfully.");
            } else {
                System.out.println("Invalid staff details!");
            }
        } catch (Exception e) {
            System.out.println("Error adding staff: " + e.getMessage());
        }
    }

    /**
     * Updates an existing staff member's information.
     * Allows modification of name, gender and age.
     */
    public void updateStaff() {
        try {
            System.out.print("Enter staff ID to update: ");
            String id = scanner.nextLine();
            Staff existingStaff = (Staff) staffRepo.getUserByHospitalID(id);

            if (existingStaff != null) {
                collectStaffDetails(existingStaff);
                staffRepo.updateStaff(existingStaff);
                System.out.println("Staff updated successfully.");
            } else {
                System.out.println("Staff not found.");
            }
        } catch (Exception e) {
            System.out.println("Error updating staff: " + e.getMessage());
        }
    }

    /**
     * Removes a staff member from the system.
     * Prevents removal of administrative staff.
     */
    public void removeStaff() {
        try {
            System.out.print("Enter staff ID to remove: ");
            String id = scanner.nextLine();
            if (!staffRepo.adminExists(id)) {
                if (staffRepo.removeStaff(id)) {
                    System.out.println("Staff removed successfully.");
                } else {
                    System.out.println("Staff not found.");
                }
            } else {
                System.out.println("Cannot remove Admin Staff.");
            }
        } catch (Exception e) {
            System.out.println("Error removing staff: " + e.getMessage());
        }
    }

    /**
     * Collects details for a new staff member through user input.
     *
     * @return Staff object with collected details, or null if input is invalid
     */
    private Staff collectStaffDetails() {
        try {
            System.out.print("ID: ");
            String id = scanner.nextLine();
            if (staffRepo.userExists(id)){
                System.out.println("Staff already exists.");
                return null;
            }

            System.out.print("Name: ");
            String name = scanner.nextLine();

            System.out.print("Role: (doctor/pharmacist/administrator)");
            String role = scanner.nextLine();
            role = role.toLowerCase();
            role = role.substring(0, 1).toUpperCase() + role.substring(1);

            if (!role.equals("Doctor") && !role.equals("Administrator") && !role.equals("Pharmacist")) {
                return null;
            }

            System.out.print("Gender: ");
            String gender = scanner.nextLine();
            System.out.print("Age: ");
            String age = scanner.nextLine();

            if (role.equals("Doctor")) {
                return new Doctor(id, name, role, gender, age);
            } else if (role.equals("Pharmacist")) {
                return new Pharmacist(id, name, role, gender, age);
            } else {
                return new Administrator(id, name, role, gender, age);
            }
        } catch (Exception e) {
            System.out.println("Error collecting staff details: " + e.getMessage());
            return null;
        }
    }

    /**
     * Updates details for an existing staff member through user input.
     *
     * @param existingStaff The staff member to update
     */
    private void collectStaffDetails(Staff existingStaff) {
        try {
            System.out.print("New Name (leave blank to keep current): ");
            String name = scanner.nextLine();
            System.out.print("New Gender (leave blank to keep current): ");
            String gender = scanner.nextLine();
            System.out.print("New Age (leave blank to keep current): ");
            String ageInput = scanner.nextLine();

            if (!name.isEmpty()) existingStaff.setName(name);
            if (!gender.isEmpty()) existingStaff.setGender(gender);
            if (!ageInput.isEmpty()) existingStaff.setAge(ageInput);
        } catch (Exception e) {
            System.out.println("Error updating staff details: " + e.getMessage());
        }
    }

    /**
     * Retrieves and displays all appointments in the system.
     *
     * @return HashMap containing all appointments, with appointment IDs as keys
     */
    public HashMap<String, Appointment> viewAllAppointments() {
        try {
            System.out.println("These are all the appointments:");
            return appointmentRepo.getAllAppointment();
        } catch (Exception e) {
            System.out.println("Error viewing appointments: " + e.getMessage());
            return new HashMap<>();
        }
    }
}
