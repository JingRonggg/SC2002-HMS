package src.interfaces;

import src.model.Patient;
import java.util.Collection;
import java.time.Duration;

public interface INurseRepository extends IUserRepository {
    boolean addPatient(Patient patient);
}