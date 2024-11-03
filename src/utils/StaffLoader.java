package src.utils;

import src.controller.UserController;
import src.model.Administrator;
import src.model.Doctor;
import src.model.Pharmacist;

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
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String staffID = values[0];
                String staffName = values[1];
                String staffRole = values[2];
                String gender = values[3];
                String age = values[4];

                switch (staffRole.toUpperCase()) {
                    case "ADMINISTRATOR":
                        UserController.addUser(new Administrator(staffID, staffName, staffRole, gender, age));
                        break;
                    case "DOCTOR":
                        UserController.addUser(new Doctor(staffID, staffName, staffRole, gender, age));
                        break;
                    case "PHARMACIST":
                        UserController.addUser(new Pharmacist(staffID, staffName, staffRole, gender, age));
                        break;
                    default:
                        System.out.println("Invalid staff role: " + staffRole);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }
}
