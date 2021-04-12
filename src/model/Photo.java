package model;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import app.Photos;

/**
 * The Photo class is a model for photos. A photo contains a list of tags, date,
 * caption and image.
 * 
 * @author Vincent Phung
 * @author William McFarland
 *
 */
public class Photo implements Serializable {
    /**
     * Eclipse generated default ID used for serialization.
     */
    private static final long serialVersionUID = 1L;
    /**
     * List of tags.
     */
    private ArrayList<Tag> tags;
    /**
     * Caption for the photo.
     */
    private String caption;
    /**
     * 
     * Date that the photo was modified.
     */
    private Date date;
    /**
     * The image file of the photo.
     */
    private File path;
    /**
     * Calendar instance used to handle the date.
     */
    private Calendar cal;
    /**
     * List of all users in the application, used for serialization.
     */
    private ArrayList<User> allUsers = Photos.getAllUsers();

    /**
     * Constructor to create a new photo.
     * 
     * @param path Image file of the photo.
     */
    public Photo(File path) {
        this.path = path;
        this.date = initDate(path);
        this.caption = "no caption";
        this.tags = new ArrayList<Tag>();
    }

    /**
     * Method to initialize the date of the photo.
     * 
     * @param filePath Image file.
     * @return The modification date of the given image.
     */
    private Date initDate(File filePath) {
        cal = Calendar.getInstance();
        cal.setTimeInMillis(filePath.lastModified());
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * Gets the caption of the photo.
     * 
     * @return The caption of the photo.
     */
    public String getCaption() {
        return this.caption;
    }

    /**
     * Method to change the caption of the photo.
     * 
     * @param caption Caption of the photo.
     * @throws IOException Exception thrown if the change fails.
     */
    public void changeCaption(String caption) throws IOException {
        this.caption = caption;
        Photos.save(allUsers);
    }

    /**
     * Gets the modification date of the photo.
     * 
     * @return The modification date of the photo.
     */
    public Date getDate() {
        return this.date;
    }

    /**
     * Returns the image file of the photo.
     * 
     * @return The image file of the photo.
     */
    public File getPath() {
        return this.path;
    }

    /**
     * Method that deletes specified tag from list
     * 
     * @param tag Tag to be deleted.
     */
    public void deleteTag(Tag tag) {
        tags.remove(tag);
    }

    /**
     * Gets all of the tags of the photo.
     * 
     * @return List of tags that the photo contains.
     */
    public ArrayList<Tag> getTags() {
        return this.tags;
    }

    /**
     * Adds a tag to the photo.
     * 
     * @param tag Tag to be added.
     * @return true if the add was successful.
     * @throws IOException Exception thrown if the add fails.
     */
    public boolean addTag(Tag tag) throws IOException {
        if (isDup(tag)) {
            return false;
        }
        tags.add(tag);
        Photos.save(allUsers);
        return true;
    }

    /**
     * Method to remove a tag from the photo.
     * 
     * @param tag Tag to be removed.
     * @throws IOException Exception thrown if the remove fails.
     */
    public void removeTag(Tag tag) throws IOException {
        // error check
        tags.remove(tag);
        Photos.save(allUsers);
    }

    /**
     * Method that returns true if temp is already a tag and false if temp isn't
     * already a tag
     * 
     * @param temp Tag to be checked.
     * @return False if the tag is not already contained in the photo.
     */
    private boolean isDup(Tag temp) {
        for (Tag t : tags) {
            if (t.equals(temp)) {
                return true;
            }
        }
        return false;
    }

}
