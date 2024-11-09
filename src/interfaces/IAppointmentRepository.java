package src.interfaces;

import src.model.Appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;

/**
 * Main interface for appointment management that combines basic CRUD operations,
 * patient-specific operations, doctor-specific operations, and query operations.
 * The underlying data structure uses foreign key relationships through patientID and doctorID.
 */
public interface IAppointmentRepository extends
        IAppointmentBasicOperations,
        IAppointmentPatientOperations,
        IAppointmentDoctorOperations,
        IAppointmentQueryOperations {
    
    /**
     * Saves all appointment data to a CSV file for persistence
     */
    void saveAllToCsv();
}
