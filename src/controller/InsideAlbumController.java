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
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
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
    
    /*
     * method that deletes the currently selected tag in list
     */
    public void deleteTag(ActionEvent e) throws IOException {
    	Tag selected = tagsList.getSelectionModel().getSelectedItem();
    	selectedPhoto.deleteTag(selected);
    	obsTagList.remove(selected);
    	tagsList.getSelectionModel().selectFirst();
    }
    
    public void addTag(ActionEvent e) throws IOException {
    	Dialog<Tag> dialog = new Dialog<>();
    	dialog.setTitle("Add Tag");
    	dialog.setHeaderText("Create a tag using an existing tag type or create a tag using a new tag type.");
    	dialog.setResizable(true);
    	 
    	Label presetLbl = new Label("Existing Tag Type: ");
    	Label valueLbl = new Label("Tag Value: ");
    	Label newLbl = new Label("New Tag Type: ");
    	Label value2Lbl = new Label("Tag Value: ");
    	Label orLbl = new Label("OR");
    	ChoiceBox<String> dropDown = new ChoiceBox<String>(FXCollections.observableArrayList(currentUser.getPresets()));
    	TextField presetTxt = new TextField();
    	TextField typeTxt = new TextField();
    	TextField valueTxt = new TextField();
    	         
    	GridPane grid = new GridPane();
    	grid.add(presetLbl, 1, 1);
    	grid.add(dropDown, 2, 1);
    	grid.add(valueLbl, 1, 2);
    	grid.add(presetTxt, 2, 2);
    	grid.add(orLbl, 2, 4);
    	grid.add(newLbl, 1, 6);
    	grid.add(typeTxt, 2, 6);
    	grid.add(value2Lbl, 1, 7);
    	grid.add(valueTxt, 2, 7);
    	dialog.getDialogPane().setContent(grid);
    	         
    	ButtonType buttonTypeOk = new ButtonType("Create Tag", ButtonData.OK_DONE);
    	ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
    	dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
    	dialog.getDialogPane().getButtonTypes().add(buttonTypeCancel);
    	
    	dialog.setResultConverter(new Callback<ButtonType, Tag>() {
    	    @Override
    	    public Tag call(ButtonType b) {
    	 
    	        if (b == buttonTypeOk) {
    	 
    	            //check to see if using existing tag type or new tagtype
    	        	if(!dropDown.getSelectionModel().isEmpty() && !presetTxt.getText().isEmpty() && typeTxt.getText().isEmpty() && valueTxt.getText().isEmpty()) {
    	        		Tag temp = new Tag(dropDown.getValue(), presetTxt.getText());
    	        		try {
							if(selectedPhoto.addTag(temp)) {
								obsTagList.add(temp);
								tagsList.getSelectionModel().select(temp);
							}
							else {
								Alert alert = new Alert(AlertType.ERROR);
								alert.setTitle("Error");
								alert.setHeaderText("Duplicate Tag");
								alert.setContentText(
				                    "The tag you've tried to create already exists. Please try again.");
								alert.showAndWait();
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
    	        	}
    	        	else if(dropDown.getSelectionModel().isEmpty() && presetTxt.getText().isEmpty() && !typeTxt.getText().isEmpty() && !valueTxt.getText().isEmpty()) {
    	        		Tag temp = new Tag(typeTxt.getText(), valueTxt.getText());
    	        		try {
							if(selectedPhoto.addTag(temp)) {
								obsTagList.add(temp);
								tagsList.getSelectionModel().select(temp);
								if(!currentUser.getPresets().contains(typeTxt.getText())){
									currentUser.getPresets().add(typeTxt.getText());
    							}
    	
							}
							else {
								Alert alert = new Alert(AlertType.ERROR);
								alert.setTitle("Error");
								alert.setHeaderText("Duplicate Tag");
								alert.setContentText(
				                    "The tag you've tried to create already exists. Please try again.");
								alert.showAndWait();
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
    	        	}
    	        	else {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error");
						alert.setHeaderText("Illegal Input");
						alert.setContentText(
		                    "Choose an existing tag type and enter a tag value or enter a new tag type and a new tag value.");
						alert.showAndWait();
    	        	}
    	        }
    	 
    	        return null;
    	    }
    	});

    	 Optional<Tag> result = dialog.showAndWait();
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
