package src.model;

public class Doctor extends User {
    private final String doctorID;
    private final String age;
    public Doctor(String hospitalID, String doctorID, String doctorName, String role, String doctorGender, String age) {
        super(hospitalID, role, doctorName, doctorGender);
        this.doctorID = doctorID;
        this.age = age;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public String getAge() {
        return age;
    }

    @Override
    public void accessHMS() {
        System.out.println("Welcome, Doctor! Accessing doctor-specific features...");
    }
}