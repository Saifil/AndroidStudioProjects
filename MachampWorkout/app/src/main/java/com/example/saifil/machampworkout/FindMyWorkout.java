package com.example.saifil.machampworkout;

import java.util.ArrayList;
import java.util.List;

public class FindMyWorkout {
    List<String> getWorkoutType(String myWorkOut) { // function that returns a List

        //Initialize a List of Strings
        List<String> ls = new ArrayList<String> ();

        if (myWorkOut.equals("Chest")) {
            ls.add("Body Slam"); // similar to pushback
            ls.add("Rock Throw");
        }
        else if (myWorkOut.equals("Biceps")) {
            ls.add("Rock Smash");
            ls.add("Chop");
        }
        else if (myWorkOut.equals("Legs")) {
            ls.add("Roll Out");
            ls.add("Sprint");
        }
        else if (myWorkOut.equals("Abs")) {
            ls.add("Seismic Toss");
            ls.add("High Jump Kick");
        }
        return ls;
    }
}
