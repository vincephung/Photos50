package controller;

import java.io.IOException;
import java.util.ArrayList;

import app.Photos;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.User;

/**
 * The LoginController class is responsible for handling user interactions for
 * the scene Login (Login.fxml). This scene occurs when the user first starts up
 * the application.
 * 
 * @author Vincent Phung
 * @author William McFarland
 *
 */
public class LoginController {
    @FXML
    Button loginBtn;
    @FXML
    TextField loginTxt;

    /**
     * Handles the login logic for the user. If the user enters "admin", the scene
     * switches to the Admin scene, otherwise the scene switches to the UserAlbums
     * scene for a regular user.
     * 
     * @param e The login button was selected.
     * @throws IOException Exception thrown if the login failed.
     */
    public void login(ActionEvent e) throws IOException {
        // make sure there is text if not error
        if (loginTxt.getText().equals("")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No Username Entered");
            alert.setContentText("Please enter a username and try again.");
            alert.showAndWait();
        } else {
            // check if admin
            if (loginTxt.getText().equals("admin")) {
                // change scene to admin page
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/view/Admin.fxml"));
                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                stage.setScene(new Scene(loader.load()));
                stage.show();
            }
            // check if valid username
            else if (validUsername(loginTxt.getText())) {
                // Set current user
                Photos.setCurrentUser(Photos.getUser(loginTxt.getText()));
                // change scene to user home page
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/view/UserAlbums.fxml"));
                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                stage.setScene(new Scene(loader.load()));
                stage.show();
            }
            // otherwise username is incorrect
            else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Incorrect Username");
                alert.setContentText("The username entered was incorrect. Please try again.");
                alert.showAndWait();
            }
        }
    }

    /**
     * Checks if the given username already exists.
     * @param username Username that the user inputs.
     * @return False if the user does not already exist, true otherwise.
     */
    private boolean validUsername(String username) {
        ArrayList<User> userList = Photos.getAllUsers();
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

}
