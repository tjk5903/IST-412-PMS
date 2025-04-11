package controller;

import model.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Controller for managing user accounts and authentication.
 */
public class UserController {

    private static List<User> users = new ArrayList<>(); // List to store registered users

    /**
     * Retrieves all registered users.
     *
     * @return A list of all users.
     */
    public List<User> getAllUsers() {
        return users;
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param userID The ID of the user.
     * @return The User object if found, otherwise null.
     */
    public User getUserById(String userID) {
        Optional<User> user = users.stream()
                .filter(u -> u.getUserID().equals(userID)) // Assuming the User class has an `getId` method
                .findFirst();
        return user.orElse(null);
    }

    /**
     * Deletes a user account.
     *
     * @param userID The ID of the user to be deleted.
     * @return true if deletion was successful, false otherwise.
     */
    public boolean deleteUser(String userID) {
        Optional<User> user = users.stream()
                .filter(u -> u.getUserID().equals(userID))
                .findFirst();

        if (user.isPresent()) {
            users.remove(user.get()); // Remove user from the list
            return true;
        }

        return false; // User not found
    }
}
