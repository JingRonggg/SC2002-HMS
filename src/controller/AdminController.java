package src.controller;

import src.model.Appointment;
import src.repository.IAppointmentRepository;
import src.model.Administrator;
import src.model.Doctor;
import src.model.Pharmacist;
import src.model.Staff;
import src.repository.IAdminRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AdminController {
    private final IAdminRepository staffRepo;
    private final IAppointmentRepository appointmentRepo;
    private final Scanner scanner;


    public AdminController(IAdminRepository staffRepo, IAppointmentRepository appointmentRepo) {
        this.staffRepo = staffRepo;
        this.appointmentRepo = appointmentRepo;
        this.scanner = new Scanner(System.in);
    }

    public void viewAllStaff() {
        System.out.println("These are all the staff:");
        Collection<Staff> staffs = staffRepo.getAllStaff();
        if (staffs.isEmpty()) {
            System.out.println("No staff members found.");
        } else {
            for (Staff staff : staffs) {
                System.out.println("ID: " + staff.getHospitalID() + ", Name: " + staff.getName() + ", Role: " + staff.getRole() + ", Gender: " + staff.getGender() + ", Age: " + staff.getAge());
            }
        }
    }

    public void addStaff() {
        System.out.println("Enter staff details:");
        Staff newStaff = collectStaffDetails();
        if (newStaff != null) {
            staffRepo.addStaff(newStaff);
            System.out.println("Staff added successfully.");
        } else {
            System.out.println("Invalid staff details!");
        }
    }

    public void updateStaff() {
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
    }

    public void removeStaff() {
        System.out.print("Enter staff ID to remove: ");
        String id = scanner.nextLine();

        if (staffRepo.removeStaff(id)) {
            System.out.println("Staff removed successfully.");
        } else {
            System.out.println("Staff not found.");
        }
    }

    private Staff collectStaffDetails() {
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
    }

    private void collectStaffDetails(Staff existingStaff) {
        System.out.print("New Name (leave blank to keep current): ");
        String name = scanner.nextLine();
        System.out.print("New Gender (leave blank to keep current): ");
        String gender = scanner.nextLine();
        System.out.print("New Age (leave blank to keep current): ");
        String ageInput = scanner.nextLine();

        if (!name.isEmpty()) existingStaff.setName(name);
        if (!gender.isEmpty()) existingStaff.setGender(gender);
        if (!ageInput.isEmpty()) existingStaff.setAge(ageInput);
    }

    public void viewAllAppointments() {
        System.out.println("These are all the appointments:");
        HashMap<String, Appointment> appointments = new HashMap<>();
        appointments = appointmentRepo.getAllAppointment();

        try{
            for (Map.Entry<String, Appointment> entry : appointments.entrySet()) {
                String appointmentID = entry.getKey();
                Appointment appointment = entry.getValue();

                System.out.println("Appointment ID: " + appointmentID);
                System.out.println("PatientID: " + appointment.getPatientID());
                System.out.println("DoctorID: " + appointment.getDoctorID());
                System.out.println("Date: " + appointment.getAppointmentDate());
                System.out.println("Start time: " + appointment.getAppointmentStartTime());
                System.out.println("End time: " + appointment.getAppointmentEndTime());
                System.out.println("Status: " + appointment.getStatus());

            }
        } catch (Exception e) {
            System.out.println("Error occured while getting appointments!");
        }
    }
}
