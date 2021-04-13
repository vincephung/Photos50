package controller;

import java.io.IOException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import app.Photos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Photo;
import model.Tag;
import model.User;

/**
 * The SearchController class is responsible for handling user interactions for
 * the scene Search (Search.fxml). This scene occurs when the user clicks search
 * within the UserAlbums scene.
 * 
 * @author Vincent Phung
 * @author William McFarland
 *
 */
public class SearchController {
    @FXML
    Button saveBtn;
    @FXML
    Button searchBtn;
    @FXML
    Button cancelBtn;
    @FXML
    DatePicker afterDate;
    @FXML
    DatePicker beforeDate;
    @FXML
    TextField firstTag;
    @FXML
    TextField secondTag;
    @FXML
    RadioButton andBtn;
    @FXML
    RadioButton orBtn;
    @FXML
    ListView<Photo> searchResults;
    @FXML
    ListView<String> tagList;
    @FXML
    Button clearBtn;

    /**
     * Holds the buttons that can be toggled.
     */
    private ToggleGroup group;
    /**
     * Currently logged in user.
     */
    private User currentUser = Photos.getCurrentUser();
    /**
     * The list of tags.
     */
    private ObservableList<String> obsTagList;
    /**
     * Observable list to handle refreshing the scene whenever changes occur to the
     * result list.
     */
    private ObservableList<Photo> obsResultList;
    /**
     * The list of photos that is returned after a search.
     */
    private ArrayList<Photo> result;

    /**
     * Initializes the tagslist and toggle groups when scene is first run.
     */
    public void initialize() {
        obsTagList = FXCollections.observableArrayList(currentUser.getPresets());
        tagList.setItems(obsTagList);
        group = new ToggleGroup();
        andBtn.setToggleGroup(group);
        orBtn.setToggleGroup(group);
    }

    /**
     * Method to create a new album with the search results.
     * 
     * @param e The save album button is selected.
     * @throws IOException Exception thrown if the save fails.
     */
    public void saveAlbum(ActionEvent e) throws IOException {
        TextInputDialog text = new TextInputDialog();
        text.setTitle("Save to Album");
        text.setHeaderText("Input Album Name");
        text.setContentText("Please enter the name of new album with the search results: ");
        Optional<String> result = text.showAndWait();
        result.ifPresent(temp -> {
            try {
                if (currentUser.duplicateAlbum(result.get())) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Duplicate Album Name");
                    alert.setContentText("An album with this name already exists. Please try again.");
                    alert.showAndWait();
                } else {
                    currentUser.createAlbum(temp, new ArrayList<Photo>(this.result)); //deep copy so clearing search results wont affect creation
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
    }

    /**
     * Method that handles searching for photos based on a date range or 1 or 2 tags
     * 
     * @param e The search button is selected.
     * @throws IOException Exception thrown if the search fails.
     */
    public void search(ActionEvent e) throws IOException {
        // check for dates first
        if (afterDate.getValue() != null && beforeDate.getValue() != null && firstTag.getText().isEmpty()
                && secondTag.getText().isEmpty() && !orBtn.isPressed() && !andBtn.isPressed()) {
            result = currentUser.searchByDate(
                    Date.from(afterDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                    Date.from(beforeDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            obsResultList = FXCollections.observableArrayList(result);

        } else if (afterDate.getValue() == null && beforeDate.getValue() == null && !firstTag.getText().isEmpty()) {
            // extract data from first tag;
            int index;
            index = firstTag.getText().indexOf('=');
            if (index == -1) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Tag Input");
                alert.setContentText("All tag type and value pairs should be typed as 'type=value'. Please try again.");
                alert.showAndWait();
                return;
            }
            Tag tag1 = new Tag(firstTag.getText().substring(0, index), firstTag.getText().substring(index + 1));
            // check to see if there's a second tag
            if (!secondTag.getText().isEmpty()) {
                if (!orBtn.isSelected() && !andBtn.isSelected()) {
                       Alert alert = new Alert(AlertType.ERROR);
                       alert.setTitle("Error");
                       alert.setHeaderText("No Combination Selected");
                       alert.setContentText(
                               "And or Or must be selected when searching for two tags. Please try again.");
                       alert.showAndWait();
                       return;
                }
                index = secondTag.getText().indexOf('=');
                if (index == -1) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Invalid Tag Input");
                    alert.setContentText(
                            "All tag type and value pairs should be typed as 'type=value'. Please try again.");
                    alert.showAndWait();
                    return;
                }
                Tag tag2 = new Tag(secondTag.getText().substring(0, index), secondTag.getText().substring(index + 1));
                if (andBtn.isSelected()) {
                    result = currentUser.searchByTag(tag1, tag2, "and");
                    obsResultList = FXCollections.observableArrayList(result);
                } else {
                    result = currentUser.searchByTag(tag1, tag2, "or");
                    obsResultList = FXCollections.observableArrayList(result);
                }
            }
            // otherwise just search by first tag
            else {
                result = currentUser.searchByTag(tag1);
                obsResultList = FXCollections.observableArrayList(result);
            }
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Search Criteria");
            alert.setContentText(
                    "Search by a date range or a combination of one or two tags but NOT BOTH. Please try again.");
            alert.showAndWait();
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

    /**
     * Method that handles clearing all the search fields
     * 
     * @param e The clear button was selected.
     * @throws IOException Exception thrown if clear fails.
     */
    public void clear(ActionEvent e) throws IOException {
        afterDate.setValue(null);
        beforeDate.setValue(null);
        firstTag.clear();
        secondTag.clear();
        andBtn.setSelected(false);
        orBtn.setSelected(false);
        if (result != null) {
            result.clear();
        }
        searchResults.setItems(null);
            
    }

    /**
     * Switches the scene back to user albums.
     * @param e The cancel button was selected.
     * @throws IOException Exception thrown if the scene fails to switch.
     */
    public void cancel(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/UserAlbums.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }

}
