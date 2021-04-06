package model;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String username;
    private ArrayList<Photo> photos = new ArrayList<Photo>();
    private ArrayList<Album> albums = new ArrayList<Album>();
    
    
    public User(String username) {
        this.username = username;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public ArrayList<Photo> getPhotos(){
        return this.photos;
    }
    
    public ArrayList<Album> getAlbums(){
        return this.albums;
    }
    
    
    public void createAlbum(String albumName) {
        albums.add(new Album(albumName));
    }
    
    public void deleteAlbum(Album album) {
        //error checking needed if list is empty
        //check if remove works correctly
        albums.remove(album);
        
    }
    
    public void openAlbum() {
        
    }
    
    public void renameAlbum(Album album, String albumName) {
        album.renameAlbum(albumName);
    }
    
    public String toString() {
        return this.username;
    }
    
}
