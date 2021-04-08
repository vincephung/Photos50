package controller;

import java.io.IOException;

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

public class SavePhotoController {
	@FXML
	TextField photoPathTxt;
	@FXML
	Button cancelBtn;
	@FXML
	Button saveBtn;
	
	/*
	 * cancels the image adding and returns user to the inside album view
	 */
	public void cancel(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/insideAlbum.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        stage.show();
	}
	
	/*
	 * saves the image to the current album and returns the user to inside album view
	 */
	public void save(ActionEvent e) {
		//check to make sure path is entered
		if(photoPathTxt.getText().equals("")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No Path Entered");
            alert.setContentText(
                    "Please enter a path and try again.");
            alert.showAndWait();
		}
		
		
	}
	
}
