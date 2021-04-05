package model;

import java.util.ArrayList;

public class Album {
    private ArrayList<Photo> photos;
    private String albumName;
    private int numberOfPhotos;
    
    public Album(String albumName) {
        this.albumName = albumName;
        photos = new ArrayList<Photo>();
        numberOfPhotos = 0;
    }
    
    public ArrayList<Photo> getPhotos(){
        return this.photos;
    }
    
    public int getAlbumSize() {
        return this.numberOfPhotos;
    }
    
    public void addPhoto(Photo photo) {
        photos.add(photo);
        numberOfPhotos++;
    }
    
    public void removePhoto(Photo photo) {
        //might need error handling if arraylist is empty
        photos.remove(photo);
        numberOfPhotos--;
    }
    
    public void renameAlbum(String albumName) {
        this.albumName = albumName;
    }
    
    public String getAlbumName() {
        return this.albumName;
    }
}
