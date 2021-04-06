package controller;

import java.io.IOException;

import app.PhotosApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import model.User;

public class AdminController {
	
	@FXML
	Button addUserBtn;
	
	@FXML Button deleteUserBtn;
	@FXML Button logoutBtn;
	@FXML ListView<User> userList;
	
	/*
	 * method that handles adding a new user
	 */
	public void addUser(ActionEvent e) throws IOException{
		
        TextInputDialog text = new TextInputDialog();
        text.setTitle("Add User");
        text.setHeaderText("Input Username");
        text.setContentText(
                "Please enter desired username: ");
        text.showAndWait();
	}
	
	public void deleteUser(ActionEvent e) throws IOException {
	    
	}
	
    public void logout(ActionEvent e) throws IOException {
        PhotosApp.setCurrentUser(null);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/Login.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }
}

