package src.utils;

import src.controller.AuthenticationController;
import src.controller.MedicineController;

public class SystemInitialiser {

    public static void initialiser(AuthenticationController authController, MedicineController medicineController) {
        String patientFilePath = "./data/Patient_List.csv";
        String staffFilePath = "./data/Staff_List.csv";
        String medicineFilePath = "./data/Medicine_List.csv";

        PatientLoader patientLoader = new PatientLoader(patientFilePath);
        StaffLoader staffLoader = new StaffLoader(staffFilePath);
        MedicationLoader medicationLoader = new MedicationLoader(medicineFilePath);

        patientLoader.loadPatients(authController);
        staffLoader.loadStaff(authController);
        medicationLoader.loadMedication(medicineController);
    }
}
