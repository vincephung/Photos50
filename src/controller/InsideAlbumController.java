package controller;

import java.io.File;
import java.io.IOException;

import app.PhotosApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.User;

public class InsideAlbumController {
    @FXML Label albumNameLbl;
    @FXML Label captionLbl;
    @FXML Label dateLbl;
    @FXML Button backBtn;
    @FXML Button addPhotoBtn;
    @FXML Button removePhotoBtn;
    @FXML Button slideshowBtn;
    @FXML Button editCaptionBtn;
    @FXML Button deleteTagBtn;
    @FXML Button addTagBtn;
    @FXML Button copyMoveBtn;

    User currentUser = PhotosApp.getCurrentUser();
    Album selectedAlbum;
        
    public void initData(Album album) {
        selectedAlbum = album;
        albumNameLbl.setText(selectedAlbum.getAlbumName());
        
    }
    
    public void back(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/UserAlbums.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }
    
    public void addPhoto(ActionEvent e) throws IOException {
    	//display file chooser dialog
        FileChooser dialog = new FileChooser();
        dialog.setTitle("Add Photo");
        dialog.getExtensionFilters().addAll(
        	     new FileChooser.ExtensionFilter("JPEG Files", "*.jpeg")
        	);
        File selectedFile = dialog.showOpenDialog((Stage) ((Node) e.getSource()).getScene().getWindow());
        
        //create new photo object and add to album
        Photo newPhoto = new Photo(selectedFile);
        selectedAlbum.addPhoto(newPhoto);
		
        /*FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/AddPhoto.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        stage.show();*/
    }
    
    public void removePhoto(ActionEvent e) throws IOException {

    }
    
    public void startSlideshow(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/Slideshow.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }
    
    public void editCaption(ActionEvent e) throws IOException {

    }
    
    public void deleteTag(ActionEvent e) throws IOException {

    }
    
    public void addTag(ActionEvent e) throws IOException {

    }
    
    public void copyMovePhoto(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/CopyMovePhoto.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }
}
