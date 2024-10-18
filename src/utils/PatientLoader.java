package src.utils;

import src.controller.UserController;
import src.model.Patient;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PatientLoader {
    private String filePath;

    public PatientLoader(String filePath) {
        this.filePath = filePath;
    }

    public void loadPatients() {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String patientID = values[0];
                String patientName = values[1];
                String dateOfBirth = values[2];
                String gender = values[3];
                String bloodType = values[4];
                String contactInformation = values[5];

                UserController.addUser(new Patient(patientID, patientName, dateOfBirth, gender, bloodType, contactInformation));
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }
}
