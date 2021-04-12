package model;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import app.Photos;

/**
 * The User class is a model for users. A user contains a username, photos and albums.
 * 
 * @author Vincent Phung
 * @author William McFarland
 *
 */
public class User implements Serializable {
    /**
     * Eclipse generated default ID used for serialization.
     */
    private static final long serialVersionUID = 1L;
    /**
     * Username of the user.
     */
    private String username;
    /**
     * List of the user's photos.
     */
    private ArrayList<Photo> photos;
    /**
     * List of the user's albums.
     */
    private ArrayList<Album> albums;
    /**
     * List of all users in the application.
     */
    private ArrayList<User> allUsers = Photos.getAllUsers();
    /**
     * List of the user's tag presets.
     */
    private ArrayList<String> tagPresets;

    /**
     * Constructor to create a new user.
     * @param username Username of the user.
     */
    public User(String username) {
        this.username = username;
        photos = new ArrayList<Photo>();
        albums = new ArrayList<Album>();
        tagPresets = new ArrayList<String>();
        tagPresets.add("person");
        tagPresets.add("location");
    }

    /**
     * Gets the username of the user.
     * @return Username of the user.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Gets the user's list of photos.
     * @return User's list of photos.
     */
    public ArrayList<Photo> getPhotos() {
        return this.photos;
    }

    /**
     * Gets the user's list of albums.
     * @return User's list of albums.
     */
    public ArrayList<Album> getAlbums() {
        return this.albums;
    }

    /**
     * Create a new album.
     * @param albumName Name of the album.
     * @throws IOException Exception if the creation fails.
     */
    public void createAlbum(String albumName) throws IOException {
    	albums.add(new Album(albumName));
    	Photos.save(allUsers);

    }

    /**
     * Create a new album.
     * @param albumName Name of the new album.
     * @param photos List of photos to be added to.
     * @throws IOException Exception thrown if creation fails.
     */
    public void createAlbum(String albumName, ArrayList<Photo> photos) throws IOException {
        albums.add(new Album(albumName, photos));
        Photos.save(allUsers);
    }
    
    /**
     * Deletes an album.
     * @param album Album to be deleted.
     * @throws IOException Exception thrown if delete fails.
     */
    public void deleteAlbum(Album album) throws IOException {
        albums.remove(album);
        Photos.save(allUsers);
    }

    /**
     * Method that searches for photos based on given tags
     * @param tag1 First tag to search for.
     * @param tag2 Second tag to search for.
     * @param indicator Indicator to search for.
     * @return List of photos based on tags.
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
    
    /**
     * Search for photos based on a single tag.
     * @param tag Tag to search for.
     * @return List of photos based on results.
     */
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
    
    /**
     * Method that searches for photos based on a given date range
     * @param after Date to search after by.
     * @param before Date to search before.
     * @return List of photos.
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
    
    /**
     * Gets the tag presets.
     * @return Tag presets.
     */
    public ArrayList<String> getPresets(){
    	return tagPresets;
    }

    /**
     * Renames the given album. 
     * @param album Album to rename.
     * @param albumName Name of the new album.
     * @throws IOException Exception thrown if rename fails.
     */
    public void renameAlbum(Album album, String albumName) throws IOException {
        album.setAlbumName(albumName);
        Photos.save(allUsers);
    }

    /**
     * Prints out the user's username.
     */
    public String toString() {
        return this.username;
    }
    
    /**
     * Checks if the album name already exists.
     * @param name Name of an album to check.
     * @return False if the album does not already exist, true otherwise.
     */
    public boolean duplicateAlbum(String name) {
    	for(Album a: albums) {
    		if(name.equals(a.getAlbumName())) {
    			return true;
    		}
    	}
    	return false;
    }

}
