package src.repository;

import src.model.Doctor;
import src.model.Patient;
import src.model.User;

import java.util.ArrayList;
import java.util.Collection;

public class PatientRepository extends UserRepository implements IPatientRepository {
    public PatientRepository() {}

    @Override
    public Collection<Patient> getAllPatients() {
        Collection<Patient> patients = new ArrayList<>();
        for (User user : users.values()) {
            if (user instanceof Patient) {
                patients.add((Patient) user);
            }
        }
        return patients;
    }

    @Override
    public Patient getPatientInfo(String hospitalID) {
        for (User user : users.values()) {
            if (user instanceof Patient) {
                if (((Patient) user).getHospitalID().equals(hospitalID)) {
                    return (Patient) user;
                }
            }
        }
        System.out.println("No such patient");
        return null;
    }

    @Override
    public boolean updatePatientEmail(String hospitalID, String newEmail) {
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.com$";

        if (!newEmail.matches(emailRegex)) {
            System.out.println("Invalid email format. Please provide a valid email address.");
            return false;
        }

        Patient patient = getPatientInfo(hospitalID);
        if (patient != null) {
            patient.setEmailAddress(newEmail);
            return true;
        }
        System.out.println("Unable to update email. Patient not found.");
        return false;
    }
}
