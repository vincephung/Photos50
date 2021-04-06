package model;

import java.io.IOException;
import java.util.ArrayList;

import app.PhotosApp;

public class Admin {
    private final String username = "admin";
    private ArrayList<User> allUsers = PhotosApp.getAllUsers();
    
    public void deleteUser(User user) throws IOException {
        //might need error handling
        allUsers.remove(user);
        PhotosApp.save(allUsers);
    }
    
    public ArrayList<User> listAllUsers(){
        return PhotosApp.getAllUsers();
    }
    
    public void createUser(String username) throws IOException {
        allUsers.add(new User(username));
        PhotosApp.save(allUsers);
    }
}
