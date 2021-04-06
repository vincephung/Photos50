package controller;

import java.io.IOException;
import java.util.ArrayList;

import app.PhotosApp;
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

public class LoginController {
	@FXML
	Button loginBtn;
	@FXML
	TextField loginTxt;
	
	/*
	 * changes the scene based on whether the username is an admin or regular user
	 */
	public void login(ActionEvent e) throws IOException {
		//make sure there is text if not error
		if(loginTxt.getText().equals("")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No Username Entered");
            alert.setContentText(
                    "Please enter a username and try again.");
            alert.showAndWait();
		}
		else {
			//check if admin
			if(loginTxt.getText().equals("admin")) {
				//change scene to admin page
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/view/Admin.fxml"));
				Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
				stage.setScene(new Scene(loader.load()));
				stage.show();
			}
			//check if valid username
			else if(validUsername(loginTxt.getText())) {
				//change scene to user home page
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/view/UserAlbums.fxml"));
				Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
				stage.setScene(new Scene(loader.load()));
				stage.show();
			}
			//otherwise username is incorrect
			else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Incorrect Username");
				alert.setContentText(
                    "The username entered was incorrect. Please try again.");
				alert.showAndWait();
			}
		}
	}
	
	private boolean validUsername(String username) {
	    ArrayList<User> userList = PhotosApp.getAllUsers();
	    for(User user :userList) {
           if(user.getUsername().equals(username)) {
                return true;
            }
	    }
	    return false;
	}
}
