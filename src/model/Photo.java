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
    private Image img;
    private Calendar cal;
    private ArrayList<User> allUsers = PhotosApp.getAllUsers();
    
    public Photo(String caption,Image img) {
        this.caption = caption;
        this.img = img;
        tags = new ArrayList<Tag>(); 
        initDate(img); //set date
    }
    
    private void initDate(Image img) {
        File imgFile = new File(img.getUrl());
        cal = Calendar.getInstance();
        cal.set(Calendar.MILLISECOND,0);
        cal.setTimeInMillis(imgFile.lastModified());
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
    
    public Image getImage() {
        return this.img;
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
