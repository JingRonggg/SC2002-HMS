package src.controller;

import src.model.Appointment;
import src.enums.AppointmentStatus;
import src.interfaces.IAppointmentRepository;
import src.model.Doctor;
import src.model.MedicalRecord;
import src.model.Patient;
import src.interfaces.IAdminRepository;
import src.interfaces.IMedicalRecordRepository;
import src.interfaces.IPatientRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class PatientController {
    private final IPatientRepository patientRepository;
    private final IMedicalRecordRepository medicalRecordRepository;
    private final IAppointmentRepository appointmentRepository;
    private final IAdminRepository adminRepository;

    public PatientController(IPatientRepository patientRepository, IMedicalRecordRepository medicalRecordRepository, IAdminRepository adminRepository, IAppointmentRepository appointmentRepository) {
        this.patientRepository = patientRepository;
        this.medicalRecordRepository = medicalRecordRepository;
        this.adminRepository = adminRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public void getPatientInformation(String hospitalID) {
        Patient patient = patientRepository.getPatientInfo(hospitalID);
        if (patient != null) {
            System.out.println("Patient Information:");
            System.out.println("Name: " + patient.getName());
            System.out.println("Hospital ID: " + patient.getHospitalID());
            System.out.println("Date of Birth: " + patient.getDateOfBirth());
            System.out.println("Gender: " + patient.getGender());
            System.out.println("Blood Type: " + patient.getBloodType());
            System.out.println("Email Address: " + patient.getEmailAddress());
        } else {
            System.out.println("Could not retrieve patient information.");
        }
    }

    public void updatePatientInformation(String hospitalID) {
        Patient patient = patientRepository.getPatientInfo(hospitalID);
        if (patient != null) {
            System.out.println("New Email: ");
            Scanner scanner = new Scanner(System.in);
            String email = scanner.nextLine();
            if (patientRepository.updatePatientEmail(hospitalID, email)) {
                System.out.println("Patient Information updated successfully.");
            } else {
                System.out.println("Failed to update patient information.");
            }
        } else {
            System.out.println("Could not update patient information.");
        }
    }

    public void viewMedicalRecord(String patientID) {
        String patientName = patientRepository.getPatientInfo(patientID).getName();
        if (patientName != null) {
            HashMap<String, MedicalRecord> medicalRecord = medicalRecordRepository.readMedicalRecord(patientID);
            if (medicalRecord != null && !medicalRecord.isEmpty()) {
                for (MedicalRecord record : medicalRecord.values()) {
                    System.out.println("Medical Record ID: " + record.getMedicalRecordID());
                    System.out.println("Diagnosis: " + record.getPastDiagnosis().getConditionName());
                    System.out.println("Treatment: " + record.getTreatments().getTreatmentName());
                }
            }
        }else {
            System.out.println("Could not retrieve patient information.");
        }
    }

    // return all slots with doctors
    public void viewAvailableSlots(LocalDate date) {
        HashMap<String, HashMap<String, Appointment>> allSlots = new HashMap<>();
        LocalTime startTime = LocalTime.of(8,0);
        LocalTime endTime = LocalTime.of(17,0);

        for (Doctor doctor: adminRepository.getAllDoctors()) {
            HashMap<String, Appointment> doctorSlots = new HashMap<>();
            LocalTime currentTime = startTime;
            String doctorID = doctor.getHospitalID();
            String doctorName = doctor.getName();
            while (currentTime.isBefore(endTime)) {
                LocalTime nextTime = currentTime.plusMinutes(30);
                if (appointmentRepository.isSlotAvailable(doctorID, date, currentTime, nextTime)) {
                    doctorSlots.put(currentTime.toString(),
                            new Appointment(doctorID, null, doctorName, date, currentTime, nextTime, AppointmentStatus.AVAILABLE, null));
                }
                currentTime = currentTime.plusMinutes(30);
            }
            if (!doctorSlots.isEmpty()){
                allSlots.put(doctorID, doctorSlots);
            }
        }
        for (Map.Entry<String, HashMap<String, Appointment>> entry : allSlots.entrySet()) {
            String doctorID = entry.getKey();
            HashMap<String, Appointment> slots = entry.getValue();
            System.out.println("-----------------------------------------------------------------------------");
            System.out.println("Doctor ID: " + doctorID + " Doctor Name: " + slots.values().iterator().next().getDoctorName());

            List<String> sortedTimes = new ArrayList<>(slots.keySet());
            Collections.sort(sortedTimes);

            for (String time : sortedTimes) {
                Appointment appointment = slots.get(time);
                System.out.println("Time: " + time + " - Status: " + appointment.getStatus());
            }
        }
    }

    //creating an appointmnet
    public void scheduleAppointment (String doctorID, String patientID, LocalDate date, LocalTime startTime) {
        LocalTime endTime = startTime.plusMinutes(30);
        String doctorName = adminRepository.getDoctorName(doctorID);
        if (doctorName == null) {
            System.out.println("Doctor not found.");
        } else if (!appointmentRepository.isSlotAvailable(doctorID, date, startTime, endTime)) {
            System.out.println("Doctor is unavailable at this time.");
        } else{
            Appointment newappointment = new Appointment(doctorID, patientID, doctorName, date, startTime, endTime, AppointmentStatus.PENDING,null);
            appointmentRepository.saveAppointment(newappointment);
            System.out.println("Appointment requested successfully.");
        }

    }

    // rescheduling appointment
    public String rescheduleAppointment (String appointmentID, LocalDate newDate, LocalTime newStartTime) {
        try {
            Appointment existingAppointment = appointmentRepository.getSpecificAppointment(appointmentID);
            if (existingAppointment == null) {
                return "Appointment not found.";
            }

            LocalTime newEndtime = newStartTime.plusMinutes(30);
            if (!appointmentRepository.isSlotAvailable(existingAppointment.getDoctorID(), newDate, newEndtime, newEndtime)) {
                return "Doctor is unavailable at this time.";
            }
            existingAppointment.setAppointmentDate(newDate);
            existingAppointment.setAppointmentStartTime(newStartTime);
            existingAppointment.setAppointmentEndTime(newEndtime);
            existingAppointment.setStatus(AppointmentStatus.PENDING);
            appointmentRepository.updateAppointment(appointmentID, existingAppointment);

        } catch (Exception e) {
            System.out.println("Error in scheduling appointment.");
        }
        return "Appointment requested successfully.";
    }

    public String cancelAppointment(String appointmentID) {
        try{
            if (appointmentRepository.getSpecificAppointment(appointmentID) == null) {
                System.out.println("Appointment not found.");
                return "Appointment not found.";
            }
            appointmentRepository.deleteAppointment(appointmentID);
            return "Appointment canceled successfully!";
        } catch (Exception e) {
            System.out.println("Error in deleting appointment.");
            return "Error in deleting appointment.";
        }
    }

    public HashMap<String, Appointment> viewScheduledAppointments(String patientID) {
        try {
            return appointmentRepository.getScheduledPatientAppointment(patientID);
        } catch(Exception e) {
            System.out.println("Error in getting scheduled appointments.");
            return new HashMap<>();
        }
    }

    public HashMap<String, Appointment> viewCompletedAppointments(String patientID) {
        try{
            HashMap<String, Appointment> completedAppointments = appointmentRepository.getCompletedPatientAppointment(patientID);

            if (completedAppointments.isEmpty()) {
                System.out.println("No scheduled appointments found.");
                return new HashMap<>();
            } else {
                return completedAppointments;
            }
        }catch(Exception e){
            System.out.println("Error in getting scheduled appointments.");
            return new HashMap<>();
        }
    }

    public HashMap<String, Appointment> viewPastAppointmentOutcomes(String patientID) {
        return appointmentRepository.getAllPatientAppointment(patientID);
    }

    public HashMap<String, MedicalRecord> viewPatientMedicalRecords(String patientID) {
        return medicalRecordRepository.readMedicalRecord(patientID);
    }

    public boolean appointmentExists(String appointmentID) {
        if (appointmentRepository.getSpecificAppointment(appointmentID) == null) {
           return false;
        }
        return true;
    }
}
