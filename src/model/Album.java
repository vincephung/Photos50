package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Album implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private ArrayList<Photo> photos;
    private String albumName;
    
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
    
    public void addPhoto(Photo photo) {
        photos.add(photo);
    }
    
    public void removePhoto(Photo photo) {
        //might need error handling if arraylist is empty
        photos.remove(photo);
    }
    
    public String toString() {
        return this.albumName;
    }
}
