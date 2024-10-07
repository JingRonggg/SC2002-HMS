package src.model;

public class Pharmacist extends User {
    private final String age;

    public Pharmacist(String pharmacistID, String pharmacistName, String role, String pharmacistGender, String age) {
        super(pharmacistID, role, pharmacistName, pharmacistGender);
        this.age = age;
    }

    public String getAge() {
        return age;
    }
}