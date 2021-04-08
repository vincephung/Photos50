package model;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import app.PhotosApp;
import javafx.scene.image.Image;

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
    	this.date = new Date(path.lastModified());
    }
    
    private void initDate(Image img) {
        File imgFile = new File(img.getUrl());
        cal = Calendar.getInstance();
        cal.setTimeInMillis(imgFile.lastModified());
        cal.set(Calendar.MILLISECOND,0);
        date = cal.getTime();
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
    
    public ArrayList<Tag> getTags() {
        return this.tags;
    }
    
    public void addTag(Tag tag) throws IOException {
        tags.add(tag);
        PhotosApp.save(allUsers);
    }
    
    public void removeTag(Tag tag) throws IOException {
        //error check
        tags.remove(tag);
        PhotosApp.save(allUsers);
    }

}
