package com.example.android.thoughtbox;

import java.util.Calendar;
import java.io.Serializable;

/**
 * Created by mwmcnall on 9/15/15.
 */
public class Thought implements Serializable
{
// Variables
    // The actual data of the thought
    String data;
    // The box it will be contained in
    String box;
    // When the thought was created
    // Used to help organize thoughts by date
    Calendar calCreated;

    private static final long serialVersionUID = 85L;

// Methods

    /**
     * A constructor for Thought
     * @param data The actual string data of the Thought
     * @param box Where to store the Thought
     */
    public Thought( String data, String box )
    {
        this.data = data;
        this.box = box;
        this.calCreated = setCalendar();
    }

    // A simple blank constructor
    public Thought(){}

    /**
     * A simple method designed to return the current value of the local date and time to be saved in the thought
     * @return The current date
     */
    public Calendar setCalendar()
    {
        Calendar cal;

        cal = Calendar.getInstance();

        return  cal;
    }

    // Think about taking the save method from NewThought.java and making it more of a method inside of the class, as we'll be saving thoughts a lot

}
