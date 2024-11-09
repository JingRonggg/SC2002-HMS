package src.model;

/**
 * Represents a Doctor in the hospital system.
 * Extends the Staff class to inherit basic staff member functionality.
 */
public class Doctor extends Staff {

    /**
     * Constructs a new Doctor with the specified details.
     *
     * @param doctorID The unique hospital identifier for the doctor
     * @param doctorName The full name of the doctor
     * @param role The role/specialty of the doctor
     * @param doctorGender The gender of the doctor
     * @param age The age of the doctor
     */
    public Doctor(String doctorID, String doctorName, String role, String doctorGender, String age) {
        super(doctorID, doctorName, role, doctorGender, age);
    }
}