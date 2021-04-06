package controller;

import java.io.IOException;

import app.PhotosApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import model.User;

public class CopyMovePhotoController {
    @FXML ChoiceBox choiceBox;
    @FXML Button moveBtn;
    @FXML Button copyBtn;
    @FXML Button cancelBtn;

    User currentUser = PhotosApp.getCurrentUser();
    
    
    public void initialize() {
        
    }
    
    public void move(ActionEvent e) throws IOException {
        
    }
    
    public void copy(ActionEvent e) throws IOException {
        
    }
    
    public void cancel(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/insideAlbum.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }
}
