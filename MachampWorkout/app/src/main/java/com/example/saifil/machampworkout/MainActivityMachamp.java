package com.example.saifil.machampworkout;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivityMachamp extends Activity {

    private FindMyWorkout fmw = new FindMyWorkout();
    private Spinner workouttype;
    private TextView workouts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_machamp);

        workouts = findViewById(R.id.txtViewWrk); // sets it to the textView reference
        workouttype = findViewById(R.id.workouttypeSpn);

        Toast.makeText(this, workouttype.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();

        workouttype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                workouts.setText(workouttype.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                workouts.setText("nothing selected");
            }
        });

    }
    public void onClickFindWorkout(View view) { // is called when the button is clicked

        workouts = findViewById(R.id.txtViewWrk); // sets it to the textView reference
        workouttype = findViewById(R.id.workouttypeSpn); // sets it to the Spinner reference

        String myWorkOut = String.valueOf(workouttype.getSelectedItem()); // sets the string to selected Spinner Value
        // workouts.setText(myWorkOut); // sets the string value to TextView in main activity

        // call the getWorkoutType method which returns a string
        List<String> printList = fmw.getWorkoutType(myWorkOut);

        StringBuilder formattedWorkout = new StringBuilder(); // creates a StringBuilder obj

        for (String w : printList) { // Loops through the elements of the List (return List)
            formattedWorkout.append(w).append('\n'); // add elements of the List to StringBuilder obj
        }

        //Display the StringBuilder in the TextView on the Main Activity
        workouts.setText(formattedWorkout);

    }
}
