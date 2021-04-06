package controller;

import java.io.IOException;

import app.PhotosApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

public class SearchController {
    @FXML
    Button saveBtn;
    @FXML
    Button searchBtn;
    @FXML
    Button cancelBtn;
    @FXML DatePicker datePicker;
    @FXML TextField firstTag;
    @FXML TextField secondTag;
    @FXML RadioButton andBtn;
    @FXML RadioButton orBtn;
    @FXML ListView searchResults;
    @FXML ListView tagList;
    

    User currentUser = PhotosApp.getCurrentUser();

    public void initialize() {

    }

    public void saveAlbum() {

    }

    public void search() {

    }

    public void cancel(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/UserAlbums.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }

}
