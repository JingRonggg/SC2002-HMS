package src.controller;

import src.enums.AppointmentStatus;
import src.model.Appointment;
import src.repository.IAppointmentRepository;
import src.model.*;
import src.repository.IAdminRepository;
import src.repository.IMedicalRecordRepository;
import src.repository.IPatientRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static java.lang.Integer.parseInt;

public class DoctorController {
    private final IAppointmentRepository appointmentRepository;
    private final IAdminRepository adminRepository;
    private final IMedicalRecordRepository medicalRecordRepository;
    private final IPatientRepository patientRepository;

    public DoctorController(IAppointmentRepository appointmentRepository, IAdminRepository adminRepository, IMedicalRecordRepository medicalRecordRepository, IPatientRepository patientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.adminRepository = adminRepository;
        this.medicalRecordRepository = medicalRecordRepository;
        this.patientRepository = patientRepository;
    }

    public HashMap<String, MedicalRecord> viewPatientMedicalRecords(String patientID) {
        return medicalRecordRepository.readMedicalRecord(patientID);
    }

    public Collection<Patient> viewAllPatients() {
        return patientRepository.getAllPatients();
    }

    public void updatePatientMedicalRecords(String medicalRecordID, PastDiagnosis newDiagnosis, Treatments newTreatment, List<PrescribeMedications> newPrescribeMedications) {
        MedicalRecord medicalRecord = medicalRecordRepository.getMedicalRecordByID(medicalRecordID);
        if (medicalRecord != null) {
            // Update the medical record with new details
            if (medicalRecordRepository.updateMedicalRecord(medicalRecordID, newDiagnosis, newTreatment, newPrescribeMedications)) {
                System.out.println("Medical Record Updated");
            } else {
                System.out.println("Failed to update Medical Record");
            }
        } else {
            System.out.println("Medical Record not found");
        }
    }

    // to check if medical record exists
    public boolean medicalRecordExists(String medicalRecordID) {
        return medicalRecordRepository.getMedicalRecordByID(medicalRecordID) != null;
    }

    // shows upcoming appointments + available slots
    public void viewPersonalSchedule(String doctorID, LocalDate date) {
        viewUpComingAppointments(doctorID);

        LocalTime startTime = LocalTime.of(8,0);
        LocalTime endTime = LocalTime.of(17,0);
        LocalTime currentTime = startTime;
        ArrayList<Appointment> availableSlots = new ArrayList<>();
        while (currentTime.isBefore(endTime)) {
            LocalTime nextTime = currentTime.plusMinutes(30);
            if (appointmentRepository.isSlotAvailable(doctorID, date, currentTime, nextTime)) {
                availableSlots.add(new Appointment(doctorID, null, null, date, currentTime, nextTime, AppointmentStatus.AVAILABLE));
            }
            currentTime = currentTime.plusMinutes(30);
        }

        availableSlots.sort(Comparator.comparing(Appointment::getAppointmentStartTime));

        // Print available slots
        if (availableSlots.isEmpty()) {
            System.out.println("No available slots for " + doctorID + " on " + date + ".");
        } else {
            System.out.println("Available slots for " + doctorID + " on " + date + ":");
            System.out.println("-----------------------------------------------------------------------------");

            for (Appointment slot : availableSlots) {
                System.out.printf("From %s to %s%n", slot.getAppointmentStartTime(), slot.getAppointmentEndTime());
            }
        }
    }

    public void setAvailabilityForAppointments(String doctorID, LocalDate date, LocalTime startTime, LocalTime endTime) {
        String doctorName = adminRepository.getDoctorName(doctorID);
        if (doctorName == null) {
            System.out.println("Doctor not found.");
        } else if (!appointmentRepository.isSlotAvailable(doctorID, date, startTime, endTime)) {
            System.out.println("You have an appointment at this time!");
        } else {
            Appointment appointment = new Appointment(doctorID, null, doctorName, date, startTime, endTime, AppointmentStatus.NOT_AVAILABLE);
            appointmentRepository.saveAppointment(appointment);
        }
    }

    public void appointmentRequestOutcome(String appointmentID, String status) {

        Appointment appointment = appointmentRepository.getSpecificAppointment(appointmentID);
        if (appointment == null) {
            System.out.println("Appointment not found.");
        } else {
            if (Objects.equals(status, "Cancelled") || Objects.equals(status, "Confirmed")) {
                // Check if the slot is available before confirming
                if (status.equals("Confirmed") && !appointmentRepository.isSlotAvailable(appointment.getDoctorID(), appointment.getAppointmentDate(), appointment.getAppointmentStartTime(), appointment.getAppointmentEndTime())) {
                    System.out.println("Slot is not available. Cancelling the appointment request.");
                    appointment.setStatus(AppointmentStatus.CANCELLED);
                    appointmentRepository.updateAppointment(appointmentID, appointment);
                } else if (status.equals("Cancelled")) {
                    appointment.setStatus(AppointmentStatus.CANCELLED);
                    appointmentRepository.updateAppointment(appointmentID, appointment);
                } else {
                    appointment.setStatus(AppointmentStatus.CONFIRMED);
                    appointmentRepository.updateAppointment(appointmentID, appointment);
                }
            } else {
                System.out.println("No such status");
            }
        }
    }

    public HashMap<String, Appointment> viewUpComingAppointments(String doctorID) {
        return appointmentRepository.getAllConfirmedAppointment(doctorID);
    }

    public boolean recordAppointmentOutcome(String appointmentID, String doctorID) {
        Appointment appointment = appointmentRepository.getSpecificAppointment(appointmentID);
        if (appointment == null) {
            System.out.println("Appointment not found.");
            return false;
        } else {
            if (!Objects.equals(doctorID, appointment.getDoctorID())) {
                System.out.println("This is not your appointment!");
                return false;
            } else {
                appointment.setStatus(AppointmentStatus.COMPLETED);
                return true;
            }
        }
    }

    public void createNewMedicalRecord (String doctorID, String patientID, PastDiagnosis pastDiagnosis, Treatments treatments, List<PrescribeMedications> newPrescribeMedications) {
        medicalRecordRepository.createMedicalRecord(doctorID, patientID, pastDiagnosis, treatments, newPrescribeMedications);
    }

    public HashMap<String, Appointment> viewPendingAppointments(String doctorID) {
        return appointmentRepository.getAllPendingAppointment(doctorID);
    }

    public Appointment findAppointment(String appointmentID) {
        return appointmentRepository.getSpecificAppointment(appointmentID);
    }
}
