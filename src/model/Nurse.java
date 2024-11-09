package src.model;

/**
 * Represents a Nurse in the hospital system.
 * Extends the Staff class to inherit basic staff member functionality.
 */
public class Nurse extends Staff {

    /**
     * Constructs a new Nurse with the specified details.
     *
     * @param nurseID The unique hospital identifier for the nurse
     * @param nurseName The full name of the nurse
     * @param role The role/specialty of the nurse
     * @param nurseGender The gender of the nurse
     * @param age The age of the nurse
     */
    public Nurse(String nurseID, String nurseName, String role, String nurseGender, String age) {
        super(nurseID, nurseName, role, nurseGender, age);
    }
}
