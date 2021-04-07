package model;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import app.PhotosApp;

public class Album implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private ArrayList<Photo> photos;
    private String albumName;
    private ArrayList<User> allUsers = PhotosApp.getAllUsers();
    private Date earliestDate;
    private Date latestDate;
    
    public Album(String albumName) {
        this.albumName = albumName;
        photos = new ArrayList<Photo>();
        earliestDate = new Date();
        latestDate = new Date();
    }
    
    public int getNumPhotos() {
        return this.photos.size();
    }
    
    public void setAlbumName(String albumName) {
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
        setEarliestDate();
        setLatestDate();
        PhotosApp.save(allUsers);
    }
    
    public void removePhoto(Photo photo) throws IOException {
        //might need error handling if arraylist is empty
        photos.remove(photo);
        setEarliestDate();
        setLatestDate();
        PhotosApp.save(allUsers);
    }
    
    public Date getEarliestDate() {
        return earliestDate;
    }
    
    public Date getLatestDate() {
        return latestDate;
    }
    
    public void setEarliestDate() {
        for(int i = 0; i < photos.size();i++) {
            Date curDate = photos.get(i).getDate();
            if(curDate.compareTo(earliestDate) < 0) {
                earliestDate = curDate;
            }
        }
    }
    
    public void setLatestDate() {
        for(int i = 0; i < photos.size();i++) {
            Date curDate = photos.get(i).getDate();
            if(curDate.compareTo(latestDate) > 0) {
                latestDate = curDate;
            }
        }
    }
    
    public String toString() {
        return this.albumName;
    }
}
