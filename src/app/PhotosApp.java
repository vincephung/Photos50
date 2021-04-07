package app;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Admin;
import model.User;

public class PhotosApp extends Application {

    public static final String storeDir = "data";
    public static final String storeFile = "users.dat";
    private static ArrayList<User> allUsers;
    private static User currentUser;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/Login.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) throws ClassNotFoundException, IOException {
        try {
            allUsers = load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        launch(args);
    }

    public static void save(ArrayList<User> userList) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile));
        oos.writeObject(userList);
        oos.close();
    }

    public static ArrayList<User> load() throws IOException, ClassNotFoundException {
        if (fileEmpty())
            return new ArrayList<User>(); // handle empty read

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir + File.separator + storeFile));
        ArrayList<User> userList = (ArrayList<User>) ois.readObject();
        ois.close();
        return userList;
    }

    private static boolean fileEmpty() {
        File file = new File(storeDir + File.separator + storeFile);
        return file.length() == 0;
    }

    public static ArrayList<User> getAllUsers() {
        return allUsers;
    }
    
    public static User getCurrentUser() {
        return currentUser;
    }
    
    public static void setCurrentUser(User user) {
        currentUser = user;
    }
    
    // given username return User object
    public static User getUser(String username) {
        for (User user : allUsers) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null; // error, no user found
    }

}
