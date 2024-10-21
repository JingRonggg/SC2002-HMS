package src.controller;

import src.appointment.Appointment;
import src.appointment.AppointmentRepository;
import src.appointment.IAppointmentRepository;
import src.model.Doctor;
import src.model.MedicalRecord;
import src.model.Patient;
import src.repository.AdminRepository;
import src.repository.IAdminRepository;
import src.repository.IMedicalRecordRepository;
import src.repository.IPatientRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class PatientController {
    private final IPatientRepository patientRepository;
    private final IMedicalRecordRepository medicalRecordRepository;
    private final IAppointmentRepository appointmentRepository;
    private final IAdminRepository adminRepository;

    public PatientController(IPatientRepository patientRepository, IMedicalRecordRepository medicalRecordRepository) {
        this.patientRepository = patientRepository;
        this.medicalRecordRepository = medicalRecordRepository;
        // TODO change to dependency later
        this.adminRepository = new AdminRepository();
        this.appointmentRepository = new AppointmentRepository();
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

    public void viewMedicalRecord(String hospitalID) {
//        Patient patient = patientRepository.getPatientInfo(hospitalID);
//        if (patient != null) {
//            ArrayList<MedicalRecord> medicalRecord = medicalRecordRepository.readMedicalRecord(patient.getHospitalID());
//            if (medicalRecord != null) {
//                System.out.println("Medical Record:");
//                System.out.println("Treatments: " + medicalRecord.getTreatments());
//                System.out.println("Past Diagnosis: " + medicalRecord.getPastDiagnosis());
//                System.out.println("Prescribed Medications: " + medicalRecord.getPrescribeMedications());
//            } else {
//                System.out.println("No medical record found.");
//            }
//        } else {
//            System.out.println("Could not retrieve patient information.");
//        }
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
                LocalTime nextTime = currentTime.plusHours(1);
                if (appointmentRepository.isSlotAvailable(doctorID, date, currentTime, nextTime)) {
                    doctorSlots.put(currentTime.toString(),
                            new Appointment(doctorID, null, doctorName, date, currentTime, nextTime, "available"));
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
        LocalTime endTime = startTime.plusHours(1);
        String doctorName = adminRepository.getDoctorName(doctorID);
        if (doctorName == null) {
            System.out.println("Doctor not found.");
        } else if (!appointmentRepository.isSlotAvailable(doctorID, date, startTime, endTime)) {
            System.out.println("Doctor is unavailable at this time.");
        } else{
            Appointment newappointment = new Appointment(doctorID, patientID, doctorName, date, startTime, endTime, "Pending");
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

            LocalTime newEndtime = newStartTime.plusHours(1);
            if (!appointmentRepository.isSlotAvailable(existingAppointment.getDoctorID(), newDate, newEndtime, newEndtime)) {
                return "Doctor is unavailable at this time.";
            }
            existingAppointment.setAppointmentDate(newDate);
            existingAppointment.setAppointmentStartTime(newStartTime);
            existingAppointment.setAppointmentEndTime(newEndtime);
            appointmentRepository.updateAppointment(appointmentID, existingAppointment);

        } catch (Exception e) {
            System.out.println("Error in scheduling appointment.");
        }
        return "Appointment requested successfully.";
    }

    public String cancelAppointment(String appointmentID) {
        try{
            if (appointmentRepository.getSpecificAppointment(appointmentID) == null) {
                return "Appointment not found.";
            }
            appointmentRepository.deleteAppointment(appointmentID);
        } catch (Exception e) {
            System.out.println("Error in deleting appointment.");
        }
        return "Appointment canceled successfully!";
    }

    public void viewScheduledAppointments(String patientID) {
        try{
            HashMap<String, Appointment> scheduledAppointments = appointmentRepository.getScheduledPatientAppointment(patientID);

            if (scheduledAppointments.isEmpty()) {
                System.out.println("No scheduled appointments found.");
            } else {
                System.out.println("Scheduled appointments found. Here is the list");
                for (Map.Entry<String, Appointment> entry : scheduledAppointments.entrySet()) {
                    String appointmentID = entry.getKey();
                    Appointment appointment = entry.getValue();

                    System.out.println("----------------------------------------------------------------------------");
                    System.out.println("Appointment ID: " + appointmentID);
                    System.out.println("Date: " + appointment.getAppointmentDate());
                    System.out.println("Appointment Time: " + appointment.getAppointmentStartTime());
                    System.out.println("Doctor: " + appointment.getDoctorName());
                    System.out.println("Status: " + appointment.getStatus());
                    System.out.println("----------------------------------------------------------------------------");
                }
            }
        }catch(Exception e){
            System.out.println("Error in getting scheduled appointments.");
        }
    }

    public HashMap<String, Appointment> viewPastAppointmentOutcomes(String patientID) {
        return appointmentRepository.getAllPatientAppointment(patientID);
    }
}
