package controller;

import java.io.IOException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.Tag;
import model.User;

public class SearchController {
    @FXML
    Button saveBtn;
    @FXML
    Button searchBtn;
    @FXML
    Button cancelBtn;
    @FXML DatePicker afterDate;
    @FXML DatePicker beforeDate;
    @FXML TextField firstTag;
    @FXML TextField secondTag;
    @FXML RadioButton andBtn;
    @FXML RadioButton orBtn;
    @FXML ListView<Photo> searchResults;
    @FXML ListView<String> tagList;
    @FXML Button clearBtn;
    
    private ToggleGroup group;
    

    User currentUser = PhotosApp.getCurrentUser();
    private ObservableList<String> obsTagList;
    private ObservableList<Photo> obsResultList;
    private ArrayList<Photo> result;
    
    public void initialize() {
    	obsTagList = FXCollections.observableArrayList(currentUser.getPresets());
    	tagList.setItems(obsTagList);
    	group = new ToggleGroup();

    	andBtn.setToggleGroup(group);

    	orBtn.setToggleGroup(group);
    }

    public void saveAlbum(ActionEvent e) throws IOException {
        TextInputDialog text = new TextInputDialog();
        text.setTitle("Save to Album");
        text.setHeaderText("Input Album Name");
        text.setContentText(
                "Please enter the name of new album with the search results: ");
        Optional<String> result = text.showAndWait();
        result.ifPresent(temp -> {
        	currentUser.getAlbums().add(new Album(temp, this.result));
        });
    }
    
    /*
     * method that handles searching for photos based on a date range or 1 or 2 tags
     */
    public void search(ActionEvent e) throws IOException {
    	//check for dates first
    	if(afterDate.getValue() != null && beforeDate.getValue() != null) {
    		obsResultList = FXCollections.observableArrayList(currentUser.searchByDate(Date.from(afterDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()), Date.from(afterDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant())));
    		
    	}
    	else if(afterDate.getValue() == null && beforeDate.getValue() == null && !firstTag.getText().isEmpty()) {
    		//extract data from first tag;
    		int index; 
    		index = firstTag.getText().indexOf('=');
    		Tag tag1 = new Tag(firstTag.getText().substring(0, index), firstTag.getText().substring(index+1));
    		//check to see if there's a second tag
    		if(!secondTag.getText().isEmpty()) {
	    		index = secondTag.getText().indexOf('=');
	    		Tag tag2 = new Tag(secondTag.getText().substring(0, index), secondTag.getText().substring(index+1));
    			if(andBtn.isSelected()) {
    				result = currentUser.searchByTag(tag1, tag2, "and");
    				obsResultList = FXCollections.observableArrayList(result);
    			}
    			else {
    				result = currentUser.searchByTag(tag1, tag2, "or");
    				obsResultList = FXCollections.observableArrayList(result);
    			}
    		}
    	}
        searchResults.setItems(obsResultList);
        searchResults.setCellFactory(listView -> new ListCell<Photo>() {
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
    	
    }

    /*
     * method that handles clearing all the search fields
     */
    public void clear(ActionEvent e) throws IOException {
    	afterDate.setValue(null);
    	beforeDate.setValue(null);
    	firstTag.clear();
    	secondTag.clear();
    	andBtn.setSelected(false);
    	orBtn.setSelected(false);
    }
    
    public void cancel(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/UserAlbums.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }

}
