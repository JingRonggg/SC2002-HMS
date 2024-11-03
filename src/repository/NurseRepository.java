package src.repository;

import src.interfaces.INurseRepository;
import src.model.Patient;
import src.model.Appointment;
import src.model.User;
import src.enums.AppointmentStatus;
import java.util.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class NurseRepository extends UserRepository implements INurseRepository {

    public NurseRepository() {
    }

    @Override
    public boolean addPatient(Patient patient) {
        if (!userExists(patient.getHospitalID())) {
            addUser(patient);
            return true;
        }
        return false;
    }
}