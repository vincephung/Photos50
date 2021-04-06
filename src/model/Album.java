package model;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import app.PhotosApp;

public class Album implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private ArrayList<Photo> photos;
    private String albumName;
    private ArrayList<User> allUsers = PhotosApp.getAllUsers();
    
    public Album(String albumName) {
        this.albumName = albumName;
        photos = new ArrayList<Photo>();
    }
    
    public int getAlbumSize() {
        return this.photos.size();
    }
    
    public void renameAlbum(String albumName) {
        this.albumName = albumName;
    }
    
    public String getAlbumName() {
        return this.albumName;
    }
    
    public ArrayList<Photo> getPhotos(){
        return this.photos;
    }
    
    public void addPhoto(Photo photo) throws IOException {
        photos.add(photo);
        PhotosApp.save(allUsers);
    }
    
    public void removePhoto(Photo photo) throws IOException {
        //might need error handling if arraylist is empty
        photos.remove(photo);
        PhotosApp.save(allUsers);
    }
    
    public String toString() {
        return this.albumName;
    }
}
