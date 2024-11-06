package src.controller;

import src.enums.AppointmentStatus;
import src.model.Appointment;
import src.interfaces.IAppointmentRepository;
import src.model.*;
import src.interfaces.IAdminRepository;
import src.interfaces.IMedicalRecordRepository;
import src.interfaces.IPatientRepository;

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

    private void handleException(String operation, Exception e) {
        System.out.println("An error occurred while " + operation + e.getMessage());
    }

    public HashMap<String, MedicalRecord> viewPatientMedicalRecords(String patientID) {
        try {
            return medicalRecordRepository.readMedicalRecord(patientID);
        } catch (Exception e) {
            handleException("viewing Medical Records", e);
            return new HashMap<>();
        }
    }

    public Collection<Patient> viewAllPatients() {
        try {
            return patientRepository.getAllPatients();
        } catch (Exception e) {
            handleException("viewing All Patients", e);
            return new ArrayList<>();
        }
    }

    public void updatePatientMedicalRecords(String medicalRecordID, PastDiagnosis newDiagnosis, Treatments newTreatment, List<PrescribeMedications> newPrescribeMedications) {
        try {
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
        } catch (Exception e) {
            handleException("Updating Medical Record", e);
        }
    }

    public boolean medicalRecordExists(String medicalRecordID) {
        try {
            return medicalRecordRepository.getMedicalRecordByID(medicalRecordID) != null;
        } catch (Exception e) {
            handleException("Checking Medical Record", e);
            return false;
        }
    }

    public ArrayList<Appointment> viewPersonalSchedule(String doctorID, LocalDate date) {
        try {
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
            return (availableSlots);
        } catch (Exception e) {
            handleException("Checking Personal Schedule", e);
        }
        return null;
    }

    public void setAvailabilityForAppointments(String doctorID, LocalDate date, LocalTime startTime, LocalTime endTime) {
        try {
            String doctorName = adminRepository.getDoctorName(doctorID);
            if (doctorName == null) {
                System.out.println("Doctor not found.");
            } else if (!appointmentRepository.isSlotAvailable(doctorID, date, startTime, endTime)) {
                System.out.println("You have an appointment at this time!");
            } else {
                Appointment appointment = new Appointment(doctorID, null, doctorName, date, startTime, endTime, AppointmentStatus.NOT_AVAILABLE);
                appointmentRepository.saveAppointment(appointment);
            }
        } catch (Exception e) {
            handleException("Setting availability for Appointments", e);
        }
    }

    public void appointmentRequestOutcome(String appointmentID, String status) {
        try {
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
        } catch (Exception e) {
            handleException("Processing Appointment Request", e);
        }
    }

    public HashMap<String, Appointment> viewUpComingAppointments(String doctorID) {
        try {
            return appointmentRepository.getAllConfirmedAppointment(doctorID);
        } catch (Exception e) {
            handleException("Viewing Upcoming Appointments", e);
            return new HashMap<>();
        }
    }

    public boolean recordAppointmentOutcome(String appointmentID, String doctorID) {
        try {
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
        } catch (Exception e) {
            handleException("Recording Appointment Outcome", e);
            return false;
        }
    }

    public void createNewMedicalRecord (String doctorID, String patientID, PastDiagnosis pastDiagnosis, Treatments treatments, List<PrescribeMedications> newPrescribeMedications) {
        try {
            medicalRecordRepository.createMedicalRecord(doctorID, patientID, pastDiagnosis, treatments, newPrescribeMedications);
        } catch (Exception e) {
            handleException("Creating Medical Record", e);
        }
    }

    public HashMap<String, Appointment> viewPendingAppointments(String doctorID) {
        try {
            return appointmentRepository.getAllPendingAppointment(doctorID);
        } catch (Exception e) {
            handleException("Viewing Pending Appointments", e);
            return new HashMap<>();
        }
    }

    public Appointment findAppointment(String appointmentID) {
        try {
            return appointmentRepository.getSpecificAppointment(appointmentID);
        } catch (Exception e) {
            handleException("Finding Appointment", e);
            return null;
        }
    }
}
