package model;

import java.io.IOException;
import java.util.ArrayList;

import app.Photos;

/**
 * The Admin class is a model for the admin user. The admin is a special
 * username and can list users, create a new user and delete an existing user.
 * 
 * @author Vincent Phung
 * @author William McFarland
 *
 */
public class Admin {
    /**
     * The list of all users of the application.
     */
    private ArrayList<User> allUsers = Photos.getAllUsers();

    /**
     * Deletes the given user from the data file.
     * @param user User to delete.
     * @throws IOException Exception thrown if the delete fails.
     */
    public void deleteUser(User user) throws IOException {
        allUsers.remove(user);
        Photos.save(allUsers);
    }

    /**
     * Returns a list of all users saved in the application.
     * @return A list of all users saved in the application.
     */
    public ArrayList<User> listAllUsers() {
        return Photos.getAllUsers();
    }

    /**
     * Creates a new user in the application.
     * @param username Username of the user to create.
     * @throws IOException Exception if the creation fails.
     */
    public void createUser(String username) throws IOException {
        allUsers.add(new User(username));
        Photos.save(allUsers);
    }
    
    
}
