package app;

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
import model.Album;
import model.Photo;
import model.User;

/**
 * The Photos class is the main application driver for the project. It contains
 * the main method that launches the project as an application. Photos is
 * serializable and contains the logic that is used within the entire
 * application such as saving data, loading data, getting logged in user etc.
 * 
 * @author Vincent Phung
 * @author William McFarland
 *
 */
public class Photos extends Application {

    /**
     * String for the data directory
     */
    public static final String storeDir = "data";
    /**
     * String for where the serialization data is stored. Stores the data for all
     * users.
     */
    public static final String storeFile = "users.dat";
    /**
     * Array list to hold all the users of the application.
     */
    private static ArrayList<User> allUsers;
    /**
     * User object that refers to the currently logged in user
     */
    private static User currentUser;

    /**
     * The start method loads up the file "Login.fxml" and creates a scene with the
     * data contained within this file.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/Login.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Photos");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * The main function loads the data from data/users.dat and launches the
     * application.
     * 
     * @param args Command line arguments
     * @throws ClassNotFoundException Exception thrown when load() fails.
     * @throws IOException            Exception thrown when save() or load() fails.
     */
    public static void main(String[] args) throws ClassNotFoundException, IOException {
        try {
            allUsers = load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        launch(args);
    }

    /**
     * Method to save data for all users into data/users.dat file. Used for
     * serialization.
     * 
     * @param userList ArrayList that contains the list of all users in the
     *                 application.
     * @throws IOException Exception thrown when serialization goes wrong
     *                     (writeObject).
     */
    public static void save(ArrayList<User> userList) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile));
        oos.writeObject(userList);
        oos.close();
    }

    /**
     * Method to load data for all users from the data/users.dat file. Used for
     * serialization.
     * 
     * @return List of all users that were saved within the file.
     * @throws IOException            Exception thrown when serialization goes wrong
     *                                (readObject).
     * @throws ClassNotFoundException Exception thrown when file cannot be
     *                                found/filepath is incorrect.
     */
    public static ArrayList<User> load() throws IOException, ClassNotFoundException {
        if (fileEmpty()) {
            return new ArrayList<User>(); // handle empty read
        }

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir + File.separator + storeFile));
        ArrayList<User> userList = (ArrayList<User>) ois.readObject();
        ois.close();
        return userList;
    }

    /**
     * Determines whether the users.dat file is empty.
     * 
     * @return True if the file is users.dat file is empty.
     */
    private static boolean fileEmpty() {
        File file = new File(storeDir + File.separator + storeFile);
        return file.length() == 0;
    }

    /**
     * Returns an ArrayList that contains all users.
     * 
     * @return ArrayList of all of the users saved in the application.
     */
    public static ArrayList<User> getAllUsers() {
        return allUsers;
    }

    /**
     * Returns the currently logged in user.
     * 
     * @return The currently logged in user.
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets the currently logged in user to a given user.
     * 
     * @param user A given user object.
     */
    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    /**
     * Getter method that returns a User object given a String (username).
     * 
     * @param username User name to search for.
     * @return A user object if an User with the username exists, otherwise null.
     */
    public static User getUser(String username) {
        for (User user : allUsers) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null; // error, no user found
    }

    /**
     * Creates a user that contains 5 stock photos included with the project.
     * 
     * @throws IOException Exception thrown if saving goes wrong.
     */
    private static void createStockUser() throws IOException {
        if (getUser("stock") == null) {
            Admin admin = new Admin();
            admin.createUser("stock");
            User stock = getUser("stock");
            stock.createAlbum("stock");
            Album album = stock.getAlbums().get(0);
            for (int i = 1; i <= 5; i++) {
                File newFile = new File("data/stockPhotos/stock" + i + ".jpeg");
                System.out.println(newFile.getPath());
                Photo photo = new Photo(newFile);
                album.addPhoto(photo);
                save(allUsers);
            }
        }
    }

}
