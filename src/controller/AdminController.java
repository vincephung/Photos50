package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;

public class AdminController {
	
	@FXML
	Button addUserBtn;
	
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
}

