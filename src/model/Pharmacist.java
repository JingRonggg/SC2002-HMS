package src.model;

/**
 * Represents a Pharmacist in the system who is a type of Staff member.
 * Extends the Staff class to inherit basic staff member properties and behaviors.
 */
public class Pharmacist extends Staff {

    /**
     * Constructs a new Pharmacist with the specified details.
     *
     * @param pharmacistID      The unique identifier for the pharmacist
     * @param pharmacistName    The name of the pharmacist
     * @param role             The role of the pharmacist in the organization
     * @param pharmacistGender The gender of the pharmacist
     * @param age             The age of the pharmacist
     */
    public Pharmacist(String pharmacistID, String pharmacistName, String role, String pharmacistGender, String age) {
        super(pharmacistID, pharmacistName, role, pharmacistGender, age);
    }
}