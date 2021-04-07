package controller;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Consumer;

import app.PhotosApp;
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


public class AdminController {
	
	@FXML
	Button addUserBtn;
	
	@FXML Button deleteUserBtn;
	@FXML Button logoutBtn;
	@FXML ListView<User> userList;
	
	private Admin admin = new Admin();
    private ObservableList<User> obsList = FXCollections.observableArrayList(admin.listAllUsers());

    /*
     * populates the users list
     */
    public void initialize() {
    	userList.setItems(obsList);
    	userList.getSelectionModel().select(0);
    }
    
    /*helper method to check for duplicate usernames*/
    private boolean isDup(String username) {
    	for(User user: PhotosApp.getAllUsers()) {
    		if(user.getUsername().equals(username)) {
    			return true;
    		}
    	}
    	return false;
    }
    
	/*
	 * method that handles adding a new user
	 */
	public void addUser(ActionEvent e) throws IOException{
		
        TextInputDialog text = new TextInputDialog();
        text.setTitle("Add User");
        text.setHeaderText("Input Username");
        text.setContentText(
                "Please enter desired username: ");
        Optional<String> result = text.showAndWait();
        result.ifPresent(temp -> {
        	if(isDup(temp)) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Duplicate Username");
				alert.setContentText(
                    "The username entered already exists. Please try again.");
				alert.showAndWait();
        	}
        	else {
        		obsList.add(new User(temp));
        		try {
					admin.createUser(temp);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
        	
        });
	}
	
	/*
	 * method that handles deleting a user
	 */
	public void deleteUser(ActionEvent e) throws IOException {
		//dialog asking for confirmation
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
	
	/*
	 * method that handles logging out
	 */
    public void logout(ActionEvent e) throws IOException {
        PhotosApp.setCurrentUser(null);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/Login.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }
}

