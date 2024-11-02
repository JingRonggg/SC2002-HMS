package src.interfaces;

import src.model.Appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
// table is connected through FK of patientID and doctorID
public interface IAppointmentRepository extends
        IAppointmentBasicOperations,
        IAppointmentPatientOperations,
        IAppointmentDoctorOperations,
        IAppointmentQueryOperations {
    void saveAllToCsv();
}

