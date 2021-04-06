package model;

import java.util.ArrayList;
import java.util.Arrays;

public class User {
	
	public static ArrayList<User> users = new ArrayList<User>( Arrays.asList(new User("admin"), new User("test") ));
	
    private String username;
    //private boolean admin;
    private ArrayList<Photo> photos = new ArrayList<Photo>();
    private ArrayList<Album> albums = new ArrayList<Album>();
    
    public User(String username) {
        this.username = username;
    }
    
    public static boolean usernameExists(String username) {
    	for(User user: users) {
    		if(user.getUsername().equals(username)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    /*public boolean isAdmin() {
        return admin;
    }
    */
    
    /*
    public void deleteUser(User user) {
        //might need error handling
        user = null;
    }
    */
    
    public ArrayList<Photo> getPhotos(){
        return this.photos;
    }
    
    public ArrayList<Album> getAlbums(){
        return this.albums;
    }
    
    
    public void createAlbum(String albumName) {
        Album album = new Album(albumName);
        albums.add(album);
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
    
}
