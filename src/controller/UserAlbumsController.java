package controller;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import app.PhotosApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
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
    @FXML TableView<Album> tableView;
    @FXML TableColumn<Album,String> albumNameCol;
    @FXML TableColumn<Album,Integer> numPhotosCol;
    @FXML TableColumn<Album,Date> earliestDateCol;
    @FXML TableColumn<Album,Date> latestDateCol;
    
    User currentUser = PhotosApp.getCurrentUser();
    ObservableList<Album> albumList;


    public void initialize() {
        userTitleLbl.setText(currentUser.getUsername() + "'s Albums");
        albumList = FXCollections.observableArrayList(currentUser.getAlbums());
        tableView = new TableView<Album>();
        
        albumNameCol = new TableColumn<Album,String>("Album Name");
        numPhotosCol = new TableColumn<Album,Integer>("Number of Photos");
        earliestDateCol = new TableColumn<Album,Date>("Earliest Date");
        latestDateCol = new TableColumn<Album,Date>("Latest Date");
        
        
        albumNameCol.setCellValueFactory(new PropertyValueFactory<Album,String>("albumName"));
        //numphotos needs attribute?
        numPhotosCol.setCellValueFactory(new PropertyValueFactory<Album,Integer>("numPhotos"));
        earliestDateCol.setCellValueFactory(new PropertyValueFactory<Album,Date>("earliestDate"));
        latestDateCol.setCellValueFactory(new PropertyValueFactory<Album,Date>("latestDate"));

        tableView.setItems(albumList);
        tableView.getColumns().addAll(albumNameCol,numPhotosCol,earliestDateCol,latestDateCol);

        

    }

    public void openAlbum(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/insideAlbum.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }

    public void createAlbum(ActionEvent e) throws IOException {
        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        inputDialog.setTitle("Create New Album");
        inputDialog.setHeaderText("Enter Album Name:");

        final Button okBtn = (Button) inputDialog.getDialogPane().lookupButton(ButtonType.OK);
        TextField textField = inputDialog.getEditor();
        
        //Handle input validation when OK button is clicked
        okBtn.addEventFilter(ActionEvent.ACTION, event -> {
            if (duplicateAlbum(textField.getText())) {
                inputDialog.setContentText("Duplicate album name try again");
                event.consume();
            }
        });
        
        Optional<String> result = inputDialog.showAndWait();
        if(!result.isEmpty()) {
            //successfully create album
            currentUser.createAlbum(result.get());
        }
    }

    private boolean duplicateAlbum(String userInput) {
        for (Album album : currentUser.getAlbums()) {
            if (album.getAlbumName().equals(userInput)) {
                return true;
            }
        }
        return false;
    }
    
    public void renameAlbum(ActionEvent e) throws IOException {
        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        inputDialog.setTitle("Rename Album");
        inputDialog.setHeaderText("Enter Album Name:");

        final Button okBtn = (Button) inputDialog.getDialogPane().lookupButton(ButtonType.OK);
        TextField textField = inputDialog.getEditor();
        
        //Handle input validation when OK button is clicked
        okBtn.addEventFilter(ActionEvent.ACTION, event -> {
            if (duplicateAlbum(textField.getText())) {
                inputDialog.setContentText("Duplicate album name try again");
                event.consume();
            }
        });
        
        Optional<String> result = inputDialog.showAndWait();
        if(!result.isEmpty()) {
            //currentUser.renameAlbum(result.get());
        }

    }

    public void deleteAlbum(ActionEvent e) throws IOException {

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
