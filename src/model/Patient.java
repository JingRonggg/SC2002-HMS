package src.model;

/**
 * Represents a Patient in the healthcare system.
 * Extends the User class with additional patient-specific information.
 */
public class Patient extends User {

    //TODO declare all Data classes
    private final String dateOfBirth;
    private final String bloodType;
    private String emailAddress;

    /**
     * Constructs a new Patient with the specified details.
     *
     * @param patientID The unique identifier for the patient
     * @param patientName The full name of the patient
     * @param dateOfBirth The patient's date of birth
     * @param patientGender The gender of the patient
     * @param bloodType The patient's blood type
     * @param emailAddress The patient's email address
     */
    public Patient(String patientID, String patientName, String dateOfBirth, String patientGender, String bloodType, String emailAddress) {
        super(patientID, "Patient", patientName, patientGender);
        this.dateOfBirth = dateOfBirth;
        this.bloodType = bloodType;
        this.emailAddress = emailAddress;
    }

    /**
     * Gets the patient's email address.
     *
     * @return The email address of the patient
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Sets the patient's email address.
     *
     * @param emailAddress The new email address to set
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * Gets the patient's date of birth.
     *
     * @return The date of birth of the patient
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Gets the patient's blood type.
     *
     * @return The blood type of the patient
     */
    public String getBloodType() {
        return bloodType;
    }
}