package controller;

import java.io.IOException;

import app.PhotosApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.User;

public class UserAlbumsController {
    @FXML Label userTitleLbl;
    @FXML Button openBtn;
    @FXML Button createBtn;
    @FXML Button deleteBtn;
    @FXML Button renameBtn;
    @FXML Button searchBtn;
    @FXML Button logoutBtn;
    
    User currentUser = PhotosApp.getCurrentUser();
    
    
    public void initialize() {
        userTitleLbl.setText(currentUser.getUsername() + "'s Albums");
    }
    
    public void openAlbum(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/insideAlbum.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }
    
    public void createAlbum(ActionEvent e) throws IOException {

    }
    
    public void deleteAlbum(ActionEvent e) throws IOException {
      
    }
    
    public void renameAlbum(ActionEvent e) throws IOException {
        
    }
    
    public void search(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/SearchOptions.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        stage.show();
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
