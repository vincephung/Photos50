package model;

import java.io.Serializable;

/**
 * The Tag class is a model for tags. A tag contains a name and a value.
 * 
 * @author Vincent Phung
 * @author William McFarland
 *
 */
public class Tag implements Serializable {
    /**
     * Eclipse generated default ID used for serialization.
     */
    private static final long serialVersionUID = 1L;
    /**
     * Name of the tag.
     */
    private String name;
    /**
     * Value of the tag.
     */
    private String value;

    /**
     * Constructor used to create a new tag.
     * @param name Name of the tag.
     * @param value Value of the tag.
     */
    public Tag(String name, String value) {
        this.name = name;
        this.value = value;
    }

    /**
     * Gets the name of the tag.
     * @return Name of the tag.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the value of the tag.
     * @return Value of the tag.
     */
    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return name + ": " + value;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Tag)) {
            return false;
        }
        return ((Tag) o).getValue().equals(value) && ((Tag) o).getName().equals(name);
    }
}
