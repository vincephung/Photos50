package model;

import java.util.ArrayList;

public class Tag {
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
}
