package src.repository;

import src.model.User;

import java.util.Collection;

public interface IUserRepository {

    //Crud operations on "User table"
    //Create
    void addUser(User user);

    //Read by hospitalID
    User getUserByHospitalID(String id);

    //Read all users
    Collection<User> getAllUsers();

    //Update user
    void updateUser(User user);

    //Delete user
    boolean deleteUser(String id);
}
