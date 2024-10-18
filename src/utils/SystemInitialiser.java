package src.utils;

import src.controller.AuthenticationController;
import src.controller.MedicineController;

public class SystemInitialiser {

    // TODO move this whole class into controller, name it DB controller, ignore calling of auth controller, move CRUD operations from authentication controller to here, implement in interface
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
