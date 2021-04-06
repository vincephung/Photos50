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
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.User;

public class SlideshowController {
    @FXML Button backBtn;
    @FXML Button previousBtn;
    @FXML Button nextBtn;
    @FXML Label albumNameLbl;
    @FXML Label captionLbl;
    @FXML ImageView img; 

    User currentUser = PhotosApp.getCurrentUser();
    
    
    public void initialize() {
        
    }
    
    public void previous() {
        
    }
    
    public void next() {
        
    }
    
    public void back(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/insideAlbum.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }
}
