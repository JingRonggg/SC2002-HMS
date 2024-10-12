package src.utils;

import src.model.*;
import src.controller.AuthenticationController;
import src.controller.PatientController;

public class SystemInitialiser {

    public static void initialiser(AuthenticationController authController) {
        String patientFilePath = "./data/Patient_List.csv";
        String staffFilePath = "./data/Staff_List.csv";
        String medicineFilePath = "./data/Medicine_List.csv";

        PatientLoader patientLoader = new PatientLoader(patientFilePath);
        StaffLoader staffLoader = new StaffLoader(staffFilePath);

        patientLoader.loadPatients(authController);
        staffLoader.loadStaff(authController);

        // TODO
        // removing this part cause its for testing
        System.out.println("System initialised with default users:");
        for (User user : authController.getAllUsers()){
            System.out.println("HospitalID: " + user.getHospitalID() + ", Role: " + user.getRole() + ", Name: " + user.getName() + ", Gender: " + user.getGender());
        }
    }
}
