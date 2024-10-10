package src.repository;

import src.model.Patient;
import src.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
}
