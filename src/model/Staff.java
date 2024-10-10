package src.model;

public class Staff extends User{
    private final String age;
    public Staff(String HospitalID, String Name, String role, String doctorGender, String age) {
        super(HospitalID, role, Name, doctorGender);
        this.age = age;
    }

    public String getAge() {
        return age;
    }
}
