package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Tag implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String name;
    private ArrayList<String> values;
    
    public Tag(String name, ArrayList<String> values) {
        this.name = name;
        this.values = values;
    }
    
    public String getName() {
        return this.name;
    }
    
    public ArrayList<String> getValues() {
        return this.values;
    }
    
    @Override
    public String toString() {
    	String result = name + ":";
    	for(String temp: values) {
    		result += (" " + temp);
    	}
    	return result;
    }
}
