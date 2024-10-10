package src.repository;

import src.model.Patient;

import java.util.Collection;

public interface IPatientRepository extends IUserRepository {
    Collection<Patient> getAllPatients();
}
