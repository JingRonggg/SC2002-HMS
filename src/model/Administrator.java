package src.model;

public class Administrator extends User {
    private final String age;
    public Administrator(String adminID, String adminName, String role, String adminGender, String age) {
        super(adminID, role, adminName, adminGender);
        this.age = age;
    }

    public String getAge() {
        return age;
    }
}