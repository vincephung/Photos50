package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import app.PhotosApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.User;

public class SlideshowController {
    @FXML
    Button backBtn;
    @FXML
    Button previousBtn;
    @FXML
    Button nextBtn;
    @FXML
    Label albumNameLbl;
    @FXML
    Label captionLbl;
    @FXML
    ImageView imageView;

    private User currentUser = PhotosApp.getCurrentUser();
    private Album selectedAlbum;
    private Photo currentPhoto;
    private int index; //current index of photo in album Ex.. index (2/10)
    
    //initializes data when scene is first changed
    public void initData(Album album) throws FileNotFoundException {
        selectedAlbum = album;
        albumNameLbl.setText(selectedAlbum.getAlbumName());
        index = 0;
        //display first pic in album
        displayPicture();

    }
    
    public void previous(ActionEvent e) throws IOException {
        //if album is on the first picture, go to last picture
        if(index == 0) {
            index = selectedAlbum.getNumPhotos()-1;
        }else {
            index -= 1;
        }
        displayPicture();
    }

    public void next(ActionEvent e) throws IOException {
        //if album is on the last picture, return back to first picture
        if(index == selectedAlbum.getNumPhotos()-1) {
            index = 0;
        }else {
            index += 1;
        }
        displayPicture();
    }

    // displays imageview picture
    public void displayPicture() throws FileNotFoundException {
        //if album is empty, display nothing
        if(selectedAlbum.getNumPhotos() == 0) {
            return;
        }
        
        currentPhoto = selectedAlbum.getPhotos().get(index);
        File imgFile = currentPhoto.getPath();
        String filePath = imgFile.getPath();
        InputStream stream = new FileInputStream(filePath);
        Image image = new Image(stream);
        imageView.setImage(image);
        captionLbl.setText(currentPhoto.getCaption());
    }

    //return to inside Album scene
    public void back(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/insideAlbum.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.load()));

        // Open selected album
        InsideAlbumController controller = loader.getController();
        controller.initData(selectedAlbum);

        stage.show();
    }
}
