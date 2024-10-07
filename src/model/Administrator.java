package src.model;

public class Administrator extends User {
    private final String adminID;
    private final String age;
//    Staff ID,Name,Role,Gender,Age
    public Administrator(String hospitalID, String adminID, String adminName, String role, String adminGender, String age) {
        super(hospitalID, role, adminName, adminGender);
        this.adminID = adminID;
        this.age = age;
    }

    public String getAdminID() {
        return adminID;
    }

    public String getAge() {
        return age;
    }

    @Override
    public void accessHMS() {
        System.out.println("Welcome, Administrator! Accessing admin-specific features...");
    }
}