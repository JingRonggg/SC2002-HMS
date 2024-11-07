package src.model;

import src.utils.PasswordHasher;

public abstract class User {
    private final String hospitalID;
    private final String role;
    private String name;
    private String gender;
    private String password;
    private String hashedPassword;

    public User(String hospitalID, String role, String name, String gender) {
        this.hospitalID = hospitalID;
        this.role = role;
        this.name = name;
        this.gender = gender;
        this.password = "password"; // Default password
        this.hashedPassword = PasswordHasher.hashPassword(this.password);
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

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
        this.hashedPassword = PasswordHasher.hashPassword(newPassword);
    }

    public void setHashedPassword(String hashedPassword, String plainPassword) {
        this.hashedPassword = hashedPassword;
        this.password = plainPassword;
    }

    public String getRole() {
        return role;
    }

    public boolean verifyPassword(String password) {
        return PasswordHasher.verifyPassword(password, this.hashedPassword);
    }
}
