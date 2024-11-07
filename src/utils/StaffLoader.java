package src.utils;

import src.controller.UserController;
import src.model.Administrator;
import src.model.Doctor;
import src.model.Nurse;
import src.model.Pharmacist;
import src.model.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException; 

public class StaffLoader {
    private String filePath;
    
    public StaffLoader(String filePath) {
        this.filePath = filePath;
    }

    public void loadStaff() {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String staffID = values[0];
                String staffName = values[1];
                String staffRole = values[2];
                String gender = values[3];
                String age = values[4];
                String password = values[5];
                String hashedPassword = values[6];

                User user = null;
                switch (staffRole.toUpperCase()) {
                    case "ADMINISTRATOR":
                        user = new Administrator(staffID, staffName, staffRole, gender, age);
                        break;
                    case "DOCTOR":
                        user = new Doctor(staffID, staffName, staffRole, gender, age);
                        break;
                    case "PHARMACIST":
                        user = new Pharmacist(staffID, staffName, staffRole, gender, age);
                        break;
                    case "NURSE":
                        user = new Nurse(staffID, staffName, staffRole, gender, age);
                        break;
                    default:
                        System.out.println("Invalid staff role: " + staffRole);
                }

                if (user != null) {
                    user.setHashedPassword(hashedPassword, password);
                    UserController.addUser(user);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }
}
