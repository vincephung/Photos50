package model;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

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
    private ArrayList<String> tagPresets;

    public User(String username) {
        this.username = username;
        photos = new ArrayList<Photo>();
        albums = new ArrayList<Album>();
        tagPresets = new ArrayList<String>();
        tagPresets.add("person");
        tagPresets.add("location");
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

    public void createAlbum(String albumName, ArrayList<Photo> photos) throws IOException {
        albums.add(new Album(albumName, photos));
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
    /*
     * method that searches for photos based on given tags
     */
    public ArrayList<Photo> searchByTag(Tag tag1, Tag tag2, String indicator){
    	ArrayList<Photo> result = new ArrayList<Photo>();
    	if(indicator.equals("or")) {
    		for(Album a: albums) {
    			for(Photo p: a.getPhotos()) {
    				if(!result.contains(p)) {
    					for(Tag t: p.getTags()) {
    						if(t.equals(tag1) || t.equals(tag2)) {
    							result.add(p);
    							break;
    						}
    					}
    				}
    			}
    		}
    	}
    	else {
    		boolean first = false;
    		boolean second = false;
    		for(Album a: albums) {
    			for(Photo p: a.getPhotos()) {
    				if(!result.contains(p)) {
    					for(Tag t: p.getTags()) {
    						if(t.equals(tag1)) {
    							first = true;
    							if(first && second) {
    								result.add(p);
    								break;
    							}
    						}
    						if(t.equals(tag2)) {
    							second = true;
    							if(first && second) {
    								result.add(p);
    								break;
    							}
    						}
    					}
    				}
    			}
    		}
    	}
    	return result;
    }
    
    public ArrayList<Photo> searchByTag(Tag tag){
    	ArrayList<Photo> result = new ArrayList<Photo>();
		for(Album a: albums) {
			for(Photo p: a.getPhotos()) {
				if(!result.contains(p)) {
					for(Tag t: p.getTags()) {
						if(t.equals(tag)) {
							result.add(p);
							break;
						}
					}
				}
			}
		}
		return result;
    }
    
    /*
     * method that searches for photos based on a given date range
     */
    public ArrayList<Photo> searchByDate(Date after, Date before){
    	ArrayList<Photo> results = new ArrayList<Photo>();
    	//iterate through each album
    	for(Album alb : albums) {
    		for(Photo p : alb.getPhotos()) {
    			if(!results.contains(p) && p.getDate().after(after) && p.getDate().before(before)) {
    				results.add(p);
    			}
    		}
    	}
    	return results;
    }
    
    public ArrayList<String> getPresets(){
    	return tagPresets;
    }

    public void renameAlbum(Album album, String albumName) throws IOException {
        album.setAlbumName(albumName);
        PhotosApp.save(allUsers);
    }

    public String toString() {
        return this.username;
    }

}
