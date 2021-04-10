package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Tag implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String name;
    private String value;
    
    public Tag(String name, String value) {
        this.name = name;
        this.value = value;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getValue() {
        return this.value;
    }
    
    @Override
    public String toString() {
    	return name + ": " + value;
    }
}
