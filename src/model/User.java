package src.model;

public abstract class User {
    private final String hospitalID;
    private final String role;
    private final String name;
    private final String gender;
    private String password;

    public User(String hospitalID, String role, String name, String gender) {
        this.hospitalID = hospitalID;
        this.password = "password";
        this.role = role;
        this.name = name;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getHospitalID() {
        return hospitalID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public String getRole() {
        return role;
    }
}