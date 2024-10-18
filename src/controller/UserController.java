package src.controller;

import src.model.User;
import src.repository.IUserRepository;
import src.repository.UserRepository;
import src.utils.PatientLoader;
import src.utils.StaffLoader;

import java.util.Collection;

public class UserController {
    private static IUserRepository userRepository;

    public UserController() {
        userRepository = new UserRepository();
        loadPatients();
        loadStaff();
    }

    private void loadPatients() {
        String patientFilePath = "./data/Patient_List.csv";
        PatientLoader patientLoader = new PatientLoader(patientFilePath);
        patientLoader.loadPatients();
    }

    private void loadStaff() {
        String staffFilePath = "./data/Staff_List.csv";
        StaffLoader staffLoader = new StaffLoader(staffFilePath);
        staffLoader.loadStaff();
    }

    public static Collection<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public static void addUser(User user) {
        userRepository.addUser(user);
    }

    public static void deleteUser(String hospitalID) {
        userRepository.deleteUser(hospitalID);
    }
}
