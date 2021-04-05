package model;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javafx.scene.image.Image;

public class Photo {
    private ArrayList<Tag> tags;
    private String caption;
    private Date date; //need to import calender
    private Image img;
    private Calendar cal;
    
    
    public Photo(String caption,Image img) {
        this.caption = caption;
        this.img = img;
        tags = new ArrayList<Tag>(); 
        initDate(img); //set date
    }
    
    private void initDate(Image img) {
        File imgFile = new File(img.getUrl());
        cal = Calendar.getInstance();
        cal.setTimeInMillis(imgFile.lastModified());
        date = cal.getTime();
        //cal.set(Calendar.MILLISECOND,0)
    }
    
    public String getCaption() {
        return this.caption;
    }
    
    public void changeCaption(String caption) {
        this.caption = caption;
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
    
    public void addTag(Tag tag) {
        tags.add(tag);
    }
    
    public void removeTag(Tag tag) {
        //error check
        tags.remove(tag);
    }

}
