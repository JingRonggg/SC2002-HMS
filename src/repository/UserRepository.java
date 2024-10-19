package src.repository;

import src.model.User;

import java.util.Collection;
import java.util.HashMap;

public class UserRepository implements IUserRepository {
     static HashMap<String, User> users = new HashMap<>(); // Changed to static

    public UserRepository() {}

    @Override
    public void addUser(User user) {
        users.put(user.getHospitalID(), user);
    }

    @Override
    public User getUserByHospitalID(String hospitalID) {
        return users.get(hospitalID);
    }

    @Override
    public Collection<User> getAllUsers() {
        return users.values();
    }

    @Override
    public void updateUser(User user) {
        users.put(user.getHospitalID(), user);
    }

    @Override
    public boolean deleteUser(String hospitalID) {
        if (users.containsKey(hospitalID)) {
            users.remove(hospitalID);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean userExists(String hospitalID) {
        return users.containsKey(hospitalID);
    }

    //TODO Delete later, for checking purposes
    public void checkUserClasses() {
        System.out.println("Checking user classes in repository:");
        for (User user : users.values()) {
            System.out.println("User ID: " + user.getHospitalID() + ", Class: " + user.getClass());
        }
    }
}