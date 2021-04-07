package model;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import app.PhotosApp;

public class User implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String username;
    private ArrayList<Photo> photos;
    private ArrayList<Album> albums;
    private ArrayList<User> allUsers = PhotosApp.getAllUsers();

    public User(String username) {
        this.username = username;
        photos = new ArrayList<Photo>();
        albums = new ArrayList<Album>();
    }

    public String getUsername() {
        return this.username;
    }

    public ArrayList<Photo> getPhotos() {
        return this.photos;
    }

    public ArrayList<Album> getAlbums() {
        return this.albums;
    }

    public void createAlbum(String albumName) throws IOException {
        albums.add(new Album(albumName));
        PhotosApp.save(allUsers);
    }

    public void deleteAlbum(Album album) throws IOException {
        // error checking needed if list is empty
        // check if remove works correctly
        albums.remove(album);
        PhotosApp.save(allUsers);
    }

    public void openAlbum() {

    }

    public void renameAlbum(Album album, String albumName) throws IOException {
        album.setAlbumName(albumName);
        PhotosApp.save(allUsers);
    }

    public String toString() {
        return this.username;
    }

}
