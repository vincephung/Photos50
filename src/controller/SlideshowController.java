package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import app.Photos;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.User;

/**
 * The SlideshowController class is responsible for handling user interactions
 * for the scene Slideshow (Slideshow.fxml). This scene occurs when the user
 * clicks start slideshow within the InsideAlbum scene.
 * 
 * @author Vincent Phung
 * @author William McFarland
 *
 */
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

    /**
     * The currently selected album.
     */
    private Album selectedAlbum;
    /**
     * The current photo shown.
     */
    private Photo currentPhoto;
    /**
     * Current index/position of photo in album Ex.. index (2/10)
     */
    private int index;

    /**
     * Initializes data when scene is first changed
     * 
     * @param album The currently selected album.
     * @throws FileNotFoundException Exception thrown if displayPicture fails.
     */
    public void initData(Album album) throws FileNotFoundException {
        selectedAlbum = album;
        albumNameLbl.setText(selectedAlbum.getAlbumName());
        index = 0;
        // display first pic in album
        displayPicture();

    }

    /**
     * Switches to the previous picture in the album.
     * 
     * @param e The previous button was clicked.
     * @throws IOException Exception thrown if previous fails.
     */
    public void previous(ActionEvent e) throws IOException {
        // if album is on the first picture, go to last picture
        if (index == 0) {
            index = selectedAlbum.getNumPhotos() - 1;
        } else {
            index -= 1;
        }
        displayPicture();
    }

    /**
     * Switches to the next picture in the album.
     * 
     * @param e The next button was clicked.
     * @throws IOException Exception thrown if next fails.
     */
    public void next(ActionEvent e) throws IOException {
        // if album is on the last picture, return back to first picture
        if (index == selectedAlbum.getNumPhotos() - 1) {
            index = 0;
        } else {
            index += 1;
        }
        displayPicture();
    }

    /**
     * Displays a picture using the index variable.
     * 
     * @throws FileNotFoundException Exception thrown if image cannot be found.
     */
    public void displayPicture() throws FileNotFoundException {
        // if album is empty, display nothing
        if (selectedAlbum.getNumPhotos() == 0) {
            return;
        }
        try {
        currentPhoto = selectedAlbum.getPhotos().get(index);
        File imgFile = currentPhoto.getPath();
        String filePath = imgFile.getPath();
        InputStream stream = new FileInputStream(filePath);
        Image image = new Image(stream);
        imageView.setImage(image);
        imageView.setPreserveRatio(true);
        captionLbl.setText(currentPhoto.getCaption());
        }
        catch(Exception exc){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Problem Opening Photo");
            alert.setContentText(exc.getMessage());
            alert.showAndWait();
            return;
        }
    }

    /**
     * Return to inside Album scene
     * 
     * @param e The back button was selected.
     * @throws IOException Exception thrown if scene fails to switch.
     */
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
