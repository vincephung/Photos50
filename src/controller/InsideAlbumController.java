package controller;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import app.PhotosApp;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.Tag;
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
    @FXML Button copyBtn;
    @FXML Button moveBtn;
    @FXML ListView<Photo> listView;
    @FXML ImageView displayImage;
    @FXML ListView<Tag> tagsList;
    
    private User currentUser = PhotosApp.getCurrentUser();
    private Album selectedAlbum;
    private ObservableList<Photo> obsList;
    private ObservableList<Tag> obsTagList;
    private Photo selectedPhoto;
    
    /*
     * method that handles displaying selected photo and relevant info
     */
    public void displayPhoto() {
    	selectedPhoto = listView.getSelectionModel().getSelectedItem();
    	displayImage.setImage(new Image(selectedPhoto.getPath().toURI().toString()));
    }
    
    public void initData(Album album) {
        selectedAlbum = album;
        albumNameLbl.setText(selectedAlbum.getAlbumName());
        this.initList();
    }
    
   /*
    * method that initializes the tag 
    */
    private void initTagList() {
    	obsTagList = FXCollections.observableArrayList(selectedPhoto.getTags());
    	tagsList.setItems(obsTagList);
    	tagsList.getSelectionModel().selectFirst();
    }
    
   /*
    * method that initializes the listview
    */
    public void initList() {
        obsList = FXCollections.observableArrayList(selectedAlbum.getPhotos());
        listView.setItems(obsList);
        listView.setCellFactory(listView -> new ListCell<Photo>() {
            private ImageView imageView = new ImageView();
            @Override
            public void updateItem(Photo photo, boolean empty) {
                super.updateItem(photo, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Image image = new Image(photo.getPath().toURI().toString(), 100, 100, false, false);
                    imageView.setImage(image);
                    setText(photo.getCaption());
                    setGraphic(imageView);
                }
            }
        });
        listView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Photo>() {
                    public void changed(ObservableValue<? extends Photo> ov, 
                        Photo old_val, Photo new_val) {
                    		selectedPhoto = new_val;
                    		if(new_val != null) {
                    			displayImage.setImage(new Image(selectedPhoto.getPath().toURI().toString()));
                    			captionLbl.setText(selectedPhoto.getCaption());
                    			dateLbl.setText(selectedPhoto.getDate().toGMTString());
                    			initTagList();
                    		}
                    		else {
                    			displayImage.setImage(null);
                    			captionLbl.setText("");
                    			dateLbl.setText("");
                    		}
                }
            });
        if(listView.getSelectionModel().isEmpty()) {
        	listView.getSelectionModel().selectFirst();
        }
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
        obsList.add(newPhoto);
        listView.getSelectionModel().select(newPhoto);
		
        /*FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/AddPhoto.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        stage.show();*/
    }
    
    /*
     * method that handles removing a photo from an album
     */
    public void removePhoto(ActionEvent e) throws IOException {
        Alert alert = new Alert(AlertType.WARNING, "Are you sure you want to delete this photo?", ButtonType.YES,
                ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.YES) {
            selectedAlbum.removePhoto(listView.getSelectionModel().getSelectedItem());
            obsList.remove(listView.getSelectionModel().getSelectedItem());
            listView.getSelectionModel().selectFirst();
        } else {
            return;
        }
    }
    
    public void startSlideshow(ActionEvent e) throws IOException {        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/Slideshow.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.load()));

        // Open slideshow for current album
        SlideshowController controller = loader.getController();
        controller.initData(selectedAlbum);

        stage.show();
    }
    
    public void editCaption(ActionEvent e) throws IOException {
        TextInputDialog text = new TextInputDialog();
        text.setTitle("Edit Caption");
        text.setHeaderText("Input Caption");
        text.setContentText(
                "Please enter desired caption: ");
        text.getEditor().setText(selectedPhoto.getCaption());
        Optional<String> result = text.showAndWait();
        result.ifPresent(temp -> {
        	try {
				selectedPhoto.changeCaption(temp);
				this.initList();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	
        });

    }
    
    public void deleteTag(ActionEvent e) throws IOException {

    }
    
    public void addTag(ActionEvent e) throws IOException {

    }
    
    public void copyPhoto(ActionEvent e) throws IOException {
        ChoiceDialog<Album> choice = new ChoiceDialog<Album>(selectedAlbum, currentUser.getAlbums());
        choice.setTitle("Copy Photo");
        choice.setHeaderText("Destination Album");
        choice.setContentText(
                "Please choose album to copy to: ");
        Optional<Album> result = choice.showAndWait();
        result.ifPresent(temp -> {
        	try {
				temp.addPhoto(selectedPhoto);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	
        });
    }
    
    /*
     * method that handles moving a photo to a different album
     */
    public void movePhoto(ActionEvent e) throws IOException {
        ChoiceDialog<Album> choice = new ChoiceDialog<Album>(selectedAlbum, currentUser.getAlbums());
        choice.setTitle("Move Photo");
        choice.setHeaderText("Destination Album");
        choice.setContentText(
                "Please choose album to move to: ");
        Optional<Album> result = choice.showAndWait();
        result.ifPresent(temp -> {
        	try {
				temp.addPhoto(selectedPhoto);
				selectedAlbum.removePhoto(selectedPhoto);
				obsList.remove(selectedPhoto);
				listView.getSelectionModel().selectFirst();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	
        });
    }
    
}
