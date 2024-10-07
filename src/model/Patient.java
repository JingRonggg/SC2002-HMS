package src.model;

import java.util.Date;
import java.util.List;

public class Patient extends User {

    //TODO declare all Data classes
    private final String dateOfBirth;
    private final String bloodType;
//    private final List<String, PastDiagnosis> pastDiagnosisList;
//    private final List<String, Treatments> treatmentsList;
//    private final List<String, PrescribeMedications> prescribeMedicationsList;
//    private String phoneNumber;
    private String emailAddress;

    public Patient(String patientID, String patientName, String dateOfBirth, String patientGender, String bloodType, String emailAddress) {
        super(patientID, "Patient", patientName, patientGender);
        this.dateOfBirth = dateOfBirth;
        this.bloodType = bloodType;
        this.emailAddress = emailAddress;
    }

//    public String getPhoneNumber() {
//        return phoneNumber;
//    }
//
//    public void setPhoneNumber(String phoneNumber) {
//        this.phoneNumber = phoneNumber;
//    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getBloodType() {
        return bloodType;
    }
}