package src.model;

public class Pharmacist extends User {
    private final String pharmacistID;
    private final String age;

    public Pharmacist(String hospitalID, String pharmacistID, String pharmacistName, String role, String pharmacistGender, String age) {
        super(hospitalID, role, pharmacistName, pharmacistGender);
        this.pharmacistID = pharmacistID;
        this.age = age;
    }

    @Override
    public void accessHMS() {
        System.out.println("Welcome, Pharmacist! Accessing pharmacist-specific features...");
    }
}