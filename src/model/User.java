package src.model;

public abstract class User {
    private final String hospitalID;
    private final String role;
    private String name;
    private String gender;
    private String password;

    public User(String hospitalID, String role, String name, String gender) {
        this.hospitalID = hospitalID;
        this.password = "password";
        this.role = role;
        this.name = name;
        this.gender = gender;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setGender(String gender) {
        this.gender = gender;
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