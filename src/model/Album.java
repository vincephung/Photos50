package model;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import app.Photos;

/**
 * The Album class is a model for albums. An album contains photos, name, and
 * earliest/latest photo dates.
 * 
 * @author Vincent Phung
 * @author William McFarland
 *
 */
public class Album implements Serializable {
    /**
     * A default id generated by eclipse to handle serialization.
     */
    private static final long serialVersionUID = 1L;
    /**
     * A list of all photos inside of the album.
     */
    private ArrayList<Photo> photos;
    /**
     * The name of the album.
     */
    private String albumName;
    /**
     * A list of all of the users in the application, used for saving data.
     */
    private ArrayList<User> allUsers = Photos.getAllUsers();
    /**
     * The earliest modification date of all photos in the album.
     */
    private Date earliestDate;
    /**
     * The latest modification date of all photos in the album.
     * 
     */
    private Date latestDate;

    /**
     * Constructor to create a new album.
     * 
     * @param albumName Name of the album.
     */
    public Album(String albumName) {
        this.albumName = albumName;
        photos = new ArrayList<Photo>();
        earliestDate = null;
        latestDate = null;
    }

    /**
     * Constructor that creates an album containing the given arraylist of photos
     * 
     * @param albumName The name of the album.
     * @param photos    A list of photos to be added.
     */
    public Album(String albumName, ArrayList<Photo> photos) {
        this.photos = photos;
        this.albumName = albumName;
        setEarliestDate();
        setLatestDate();
    }

    /**
     * Gets the number of photos inside of the album.
     * 
     * @return The number of photos inside of the album.
     */
    public int getNumPhotos() {
        return this.photos.size();
    }

    /**
     * Sets the album name to the given input
     * 
     * @param newName New album name.
     */
    public void setAlbumName(String newName) {
        this.albumName = newName;
    }

    /**
     * Gets the name of the album.
     * 
     * @return The name of the album.
     */
    public String getAlbumName() {
        return this.albumName;
    }

    /**
     * Gets a list of all photos inside of the album.
     * 
     * @return List of all photos inside of the album.
     */
    public ArrayList<Photo> getPhotos() {
        return this.photos;
    }

    /**
     * Adds a photo to the album.
     * 
     * @param photo Photo to be added.
     * @throws IOException Exception thrown if the add fails.
     */
    public void addPhoto(Photo photo) throws IOException {
        photos.add(photo);
        setEarliestDate();
        setLatestDate();
        Photos.save(allUsers);
    }

    /**
     * Removes a photo from the album.
     * 
     * @param photo Photo to be removed.
     * @throws IOException Exception thrown if the remove fails.
     */
    public void removePhoto(Photo photo) throws IOException {
        photos.remove(photo);
        setEarliestDate();
        setLatestDate();
        Photos.save(allUsers);
    }

    /**
     * Gets the earliest modification date of all photos in the album.
     * 
     * @return The earliest modification date of all photos in the album.
     */
    public Date getEarliestDate() {
        return earliestDate;
    }

    /**
     * Gets the latest modification date of all photos in the album.
     * 
     * @return The latest modification date of all photos in the album.
     */
    public Date getLatestDate() {
        return latestDate;
    }

    /**
     * Sets the earliest modification date of all photos in the album.
     */
    private void setEarliestDate() {
        // no photos in the album
        if (photos.size() == 0) {
            earliestDate = null;
            return;
        }

        earliestDate = photos.get(0).getDate();
        for (int i = 0; i < photos.size(); i++) {
            Date curDate = photos.get(i).getDate();
            if (curDate.compareTo(earliestDate) < 0) {
                earliestDate = curDate;
            }
        }
    }

    /**
     * Sets the latest modification date of all photos in the album.
     */
    private void setLatestDate() {
        // no photos in the album
        if (photos.size() == 0) {
            latestDate = null;
            return;
        }

        latestDate = photos.get(0).getDate();
        for (int i = 0; i < photos.size(); i++) {
            Date curDate = photos.get(i).getDate();
            if (curDate.compareTo(latestDate) > 0) {
                latestDate = curDate;
            }
        }
    }

    /**
     * Checks if the picture already exists in this album.
     * 
     * @param img Image file to check.
     * @return False if the picture does not already exist, true otherwise.
     */
    public boolean duplicatePicture(File img) {
        for (Photo photo : photos) {
            if (img.equals(photo.getPath())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Prints out the album name.
     */
    public String toString() {
        return this.albumName;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Album)) {
            return false;
        }
        return ((Album) o).getAlbumName().equals(albumName);
    }

}
