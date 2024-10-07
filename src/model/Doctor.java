package src.model;

public class Doctor extends User {
    private final String age;
    public Doctor(String doctorID, String doctorName, String role, String doctorGender, String age) {
        super(doctorID, role, doctorName, doctorGender);
        this.age = age;
    }

    public String getAge() {
        return age;
    }
}