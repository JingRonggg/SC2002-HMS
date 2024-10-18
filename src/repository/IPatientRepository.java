package src.repository;

import src.model.Patient;

import java.util.Collection;

public interface IPatientRepository {
    Collection<Patient> getAllPatients();
    Patient getPatientInfo(String hospitalID);
    boolean updatePatientEmail(String hospitalID, String newEmail);
}
