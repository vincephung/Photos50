package controller;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Consumer;

import app.Photos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.Admin;
import model.User;

/**
 * The AdminController class is responsible for handling user interactions for
 * the Admin scene(Admin.fxml). The Admin Scene occurs when the user decides to
 * log in with the username "admin".
 * 
 * @author Vincent Phung
 * @author William McFarland
 *
 */
public class AdminController {
    @FXML
    Button addUserBtn;
    @FXML
    Button deleteUserBtn;
    @FXML
    Button logoutBtn;
    @FXML
    ListView<User> userList;

    /**
     * Instance of the Admin class to handle admin operations.
     */
    private Admin admin = new Admin();
    /**
     * Observable List that contains the list of all users in the application and
     * updates automatically when changes occur.
     */
    private ObservableList<User> obsList = FXCollections.observableArrayList(admin.listAllUsers());

    /**
     * Method that is called when this scene is loaded. Initialize populates the
     * users list and selects the first user in the list.
     */
    public void initialize() {
        userList.setItems(obsList);
        userList.getSelectionModel().select(0);
    }

    /**
     * Method to check for duplicate usernames.
     * 
     * @param username Username to check.
     * @return False if the username is not already used, otherwise returns true.
     */
    private boolean isDup(String username) {
        for (User user : Photos.getAllUsers()) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method that handles adding a new user. Creates a text input dialog and
     * creates an error if the admin inputs an existing username.
     * 
     * @param e The add button is clicked.
     * @throws IOException Exception thrown if creating a user fails.
     */
    public void addUser(ActionEvent e) throws IOException {
        TextInputDialog text = new TextInputDialog();
        text.setTitle("Add User");
        text.setHeaderText("Input Username");
        text.setContentText("Please enter desired username: ");
        Optional<String> result = text.showAndWait();
        result.ifPresent(temp -> {
            if (isDup(temp)) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Duplicate Username");
                alert.setContentText("The username entered already exists. Please try again.");
                alert.showAndWait();
            } else {
                obsList.add(new User(temp));
                try {
                    admin.createUser(temp);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

        });
    }

    /**
     * Method that handles deleting a new user. Creates an alert dialog for the
     * admin to confirm the delete action.
     * 
     * @param e The delete button is clicked.
     * @throws IOException Exception thrown if creating a user fails.
     */
    public void deleteUser(ActionEvent e) throws IOException {
        // dialog asking for confirmation
        Alert alert = new Alert(AlertType.WARNING, "Are you sure you want to delete this user?", ButtonType.YES,
                ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.YES) {
            User selectedUser = obsList.get(userList.getSelectionModel().getSelectedIndex());
            obsList.remove(selectedUser);
            admin.deleteUser(selectedUser);
        } else {
            return;
        }
    }

    /**
     * Method that handles logging out
     * 
     * @param e The delete button is clicked.
     * @throws IOException Exception thrown if creating a user fails.
     */
    public void logout(ActionEvent e) throws IOException {
        Photos.setCurrentUser(null);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/Login.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }
}
