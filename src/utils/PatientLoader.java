package src.utils;

import src.controller.UserController;
import src.model.Patient;
import src.model.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * A utility class for loading patient data from a CSV file into the system.
 */
public class PatientLoader {
    /** The file path of the CSV file containing patient data */
    private String filePath;

    /**
     * Constructs a new PatientLoader with the specified file path.
     *
     * @param filePath the path to the CSV file containing patient data
     */
    public PatientLoader(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads patients from the CSV file and adds them to the system.
     * The CSV file should have the following format:
     * patientID,patientName,dateOfBirth,gender,bloodType,contactInformation,password,[hashedPassword]
     * The first line (header) is skipped during processing.
     * If a hashed password is not provided in the CSV, it will be generated from the password.
     */
    public void loadPatients() {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String patientID = values[0];
                String patientName = values[1];
                String dateOfBirth = values[2];
                String gender = values[3];
                String bloodType = values[4];
                String contactInformation = values[5];
                String password = values[6];
                String hashedPassword = values.length > 7 ? values[7] : PasswordHasher.hashPassword(password);

                User user = new Patient(patientID, patientName, dateOfBirth, gender, bloodType, contactInformation);
                
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
