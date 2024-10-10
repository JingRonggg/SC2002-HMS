package src.utils;

import src.controller.AuthenticationController;
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

    public void loadStaff(AuthenticationController authController) {
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
                        authController.addUser(new Administrator(staffID, staffRole, staffName, gender, age));
                        break;
                    case "DOCTOR":
                        authController.addUser(new Doctor(staffID, staffRole, staffName, gender, age));
                        break;
                    case "PHARMACIST":
                        authController.addUser(new Pharmacist(staffID, staffRole, staffName, gender, age));
                        break;
                    default:
                        System.out.println("Invalid staff role: " + staffRole);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
