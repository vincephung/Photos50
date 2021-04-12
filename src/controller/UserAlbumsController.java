package controller;

import java.io.IOException;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import model.Album;
import model.User;

/**
 * The UserAlbumsController class is responsible for handling user interactions
 * for the scene UserAlbums (UserAlbums.fxml). This scene occurs when the user
 * logs in and is not an Admin.
 * 
 * @author Vincent Phung
 * @author William McFarland
 *
 */
public class UserAlbumsController {
    @FXML
    Label userTitleLbl;
    @FXML
    Button openBtn;
    @FXML
    Button createBtn;
    @FXML
    Button deleteBtn;
    @FXML
    Button renameBtn;
    @FXML
    Button searchBtn;
    @FXML
    Button logoutBtn;
    @FXML
    TableView<Album> tableView;
    @FXML
    TableColumn<Album, String> albumNameCol;
    @FXML
    TableColumn<Album, Integer> numPhotosCol;
    @FXML
    TableColumn<Album, Date> earliestDateCol;
    @FXML
    TableColumn<Album, Date> latestDateCol;

    /**
     * The currently logged in user.
     */
    User currentUser = Photos.getCurrentUser();
    /**
     * List of all albums that the user contains.
     */
    ObservableList<Album> albumList;
    /**
     * The currently selected album.
     */
    Album selectedAlbum;

    /**
     * Fills the tableview with data from albumList
     */
    public void initialize() {
        userTitleLbl.setText(currentUser.getUsername() + "'s Albums");
        albumList = FXCollections.observableArrayList(currentUser.getAlbums());

        albumNameCol.setCellValueFactory(new PropertyValueFactory<Album, String>("albumName"));
        numPhotosCol.setCellValueFactory(new PropertyValueFactory<Album, Integer>("numPhotos"));
        earliestDateCol.setCellValueFactory(new PropertyValueFactory<Album, Date>("earliestDate"));
        latestDateCol.setCellValueFactory(new PropertyValueFactory<Album, Date>("latestDate"));

        tableView.setItems(albumList);
        tableView.getSelectionModel().select(0);
    }

    /**
     * Opens the currently selected album. Error is shown if no album is selected.
     * 
     * @param e The open album button was selected.
     * @throws IOException Exception thrown if the album fails to open.
     */
    public void openAlbum(ActionEvent e) throws IOException {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (!selectedAlbum(selectedIndex)) {
            return; // User did not select an album
        }
        selectedAlbum = albumList.get(selectedIndex);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/insideAlbum.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.load()));

        // Open selected album
        InsideAlbumController controller = loader.getController();
        controller.initData(selectedAlbum);

        stage.show();
    }

    /**
     * Method to create a new album.
     * 
     * @param e The create album button was selected.
     * @throws IOException Exception thrown if create album fails.
     */
    public void createAlbum(ActionEvent e) throws IOException {
        Optional<String> result = createTextDialog("new");
        if (!result.isEmpty()) {
            currentUser.createAlbum(result.get());
            updateTable();
        }

    }

    /**
     * Method to rename an album.
     * 
     * @param e The rename album button was selected.
     * @throws IOException Exception thrown if rename album fails.
     */
    public void renameAlbum(ActionEvent e) throws IOException {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (!selectedAlbum(selectedIndex)) {
            return; // User did not select an album
        }

        selectedAlbum = albumList.get(selectedIndex);

        Optional<String> result = createTextDialog("rename");
        if (!result.isEmpty()) {
            currentUser.renameAlbum(selectedAlbum, result.get());
            updateTable();
        }
    }

    /**
     * Returns true if user has selected an album.
     * 
     * @param selectedIndex The selected index of the album.
     * @return True if user has selected an album, otherwise false.
     */
    private boolean selectedAlbum(int selectedIndex) {
        if (selectedIndex == -1) {
            Alert alert = new Alert(AlertType.ERROR, "You must select an album!");
            alert.setHeaderText("No Album Selected");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    /**
     * Creates a text input dialog and returns user input
     * 
     * @param type The option that the user chose (rename album / create album).
     * @return The text that the user inputted inside of the dialog.
     */
    private Optional<String> createTextDialog(String type) {
        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        inputDialog.setHeaderText("Enter Album Name:");
        final Button okBtn = (Button) inputDialog.getDialogPane().lookupButton(ButtonType.OK);
        TextField textField = inputDialog.getEditor();

        if (type.equals("new")) {
            inputDialog.setTitle("Create New Album");
        } else {
            inputDialog.setTitle("Rename Album");
            int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
            selectedAlbum = albumList.get(selectedIndex);
            textField.setText(selectedAlbum.getAlbumName());
        }

        // Handle input validation when OK button is clicked
        okBtn.addEventFilter(ActionEvent.ACTION, event -> {
            if (duplicateAlbum(textField.getText())) {
                inputDialog.setContentText("Duplicate album name try again");
                event.consume();
            }
        });

        // Return user input from text dialog
        return inputDialog.showAndWait();
    }

    /**
     * Refreshes and updates the tableview whenever the data changes.
     */
    private void updateTable() {
        albumList = FXCollections.observableArrayList(currentUser.getAlbums());
        tableView.setItems(albumList);
        tableView.refresh();
    }

    /**
     * Check if album already exists in album list
     * 
     * @param userInput Name of an album
     * @return False if the given album does not already exist.
     */
    private boolean duplicateAlbum(String userInput) {
        for (Album album : currentUser.getAlbums()) {
            if (album.getAlbumName().equals(userInput)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method to handle deleting an album.
     * 
     * @param e The delete album button is selected.
     * @throws IOException Exception thrown if the delete fails.
     */
    public void deleteAlbum(ActionEvent e) throws IOException {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (!selectedAlbum(selectedIndex)) {
            return; // User did not select an album
        }
        selectedAlbum = albumList.get(selectedIndex);

        Alert alert = new Alert(AlertType.WARNING, "Are you sure you want to delete this album?", ButtonType.YES,
                ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.YES) {
            currentUser.deleteAlbum(selectedAlbum);
            updateTable();
        } else {
            return;
        }
    }

    /**
     * Changes the scene to the search scene.
     * 
     * @param e The search button was clicked.
     * @throws IOException Exception thrown if the scene fails to switch.
     */
    public void search(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/SearchOptions.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }

    /**
     * Logs the current user out and switches the scene to the login scene.
     * 
     * @param e The logout button was clicked.
     * @throws IOException Exception thrown if the scene fails to switch.
     */
    public void logout(ActionEvent e) throws IOException {
        Photos.setCurrentUser(null);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/Login.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }

}
