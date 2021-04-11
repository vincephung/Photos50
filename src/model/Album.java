package model;

import java.io.File;
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
        earliestDate = null;
        latestDate = null;
    }
    
    /*
     * constructor that creates an album containing the given arraylist of photos
     */
    public Album(String albumName, ArrayList<Photo> photos) {
    	this.photos = photos;
    	this.albumName = albumName;
    	setEarliestDate();
    	setLatestDate();
    }
    
    public int getNumPhotos() {
        return this.photos.size();
    }
    
    public void setAlbumName(String newName) {
        this.albumName = newName;
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
    	if(earliestDate == null) {
    		earliestDate = photos.get(0).getDate();
    		return;
    	}
        for(int i = 0; i < photos.size();i++) {
            Date curDate = photos.get(i).getDate();
            if(curDate.compareTo(earliestDate) < 0) {
                earliestDate = curDate;
            }
        }
    }
    
    public void setLatestDate() {
    	if(latestDate == null) {
    		latestDate = photos.get(0).getDate();
    		return;
    	}
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
