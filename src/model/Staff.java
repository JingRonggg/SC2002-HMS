package src.model;

/**
 * Represents a staff member in the hospital system.
 * Extends the User class with additional age information.
 */
public class Staff extends User{
    /** The age of the staff member */
    private String age;

    /**
     * Constructs a new Staff object with the specified details.
     * @param HospitalID The unique hospital ID of the staff member
     * @param Name The name of the staff member
     * @param role The role of the staff member
     * @param doctorGender The gender of the staff member
     * @param age The age of the staff member
     */
    public Staff(String HospitalID, String Name, String role, String doctorGender, String age) {
        super(HospitalID, role, Name, doctorGender);
        this.age = age;
    }

    /**
     * Gets the age of the staff member.
     * @return The age of the staff member
     */
    public String getAge() {
        return age;
    }

    /**
     * Sets the age of the staff member.
     * @param age The new age to set
     */
    public void setAge(String age) {
        this.age = age;
    }
}
