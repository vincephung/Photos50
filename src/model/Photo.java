package model;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import app.PhotosApp;

public class Photo implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private ArrayList<Tag> tags;
    private String caption;
    private Date date; 
    private File path;
    private Calendar cal;
    private ArrayList<User> allUsers = PhotosApp.getAllUsers();
    
    public Photo(File path) {
    	this.path = path;
    	//this.date = new Date(path.lastModified());
    	this.date = initDate(path);
    	this.caption = "no caption";
    	this.tags = new ArrayList<Tag>();
    }
    
    private Date initDate(File filePath) {
        cal = Calendar.getInstance();
        cal.setTimeInMillis(filePath.lastModified());
        cal.set(Calendar.MILLISECOND,0);
        return cal.getTime();
    }
    
    public String getCaption() {
        return this.caption;
    }
    
    public void changeCaption(String caption) throws IOException {
        this.caption = caption;
        PhotosApp.save(allUsers);
    }
    
    public Date getDate() {
        return this.date;
    }
    
    public File getPath() {
        return this.path;
    }
    
    /*
     * method that deletes specified tag from list
     */
    public void deleteTag(Tag tag) {
    	tags.remove(tag);
    }
    
    public ArrayList<Tag> getTags() {
        return this.tags;
    }
    
    public boolean addTag(Tag tag) throws IOException {
    	if(isDup(tag)) {
    		return false;
    	}
        tags.add(tag);
        PhotosApp.save(allUsers);
        return true;
    }
    
    public void removeTag(Tag tag) throws IOException {
        //error check
        tags.remove(tag);
        PhotosApp.save(allUsers);
    }
    
    /*
     * method that returns true if temp is already a tag and false if temp isn't already a tag
     */
    private boolean isDup(Tag temp) {
    	for(Tag t : tags) {
    		if(t.equals(temp)) {
    			return true;
    		}
    	}
    	return false;
    }

}
