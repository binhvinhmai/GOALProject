package com.example.binhmai.goalproject;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.text.ParseException;
import java.util.ArrayList;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;

import static com.example.binhmai.goalproject.MainActivity.eventArrayList;
import static com.example.binhmai.goalproject.MainActivity.total_points;
import static com.example.binhmai.goalproject.R.id.eventCodeTextField;


/**
 * Created by binhmai on 9/18/16.
 */
public class GetPointsActivity extends AppCompatActivity {

    //List of events to iterate through it
    EditText eventEdit;
    private static final String PREFS_NAME = "PrefsFile";
    private static final String TOTALPOINTS = "TotalPoints";

    public GetPointsActivity() throws ParseException {
        //This activity throws a ParseException as the function must take in a date, and parse it
        //In order for this function to even load, a ParseException must be in place to safeguard bad input
    }

    public void getPointsFromEvent(int userCode){
        for (Event e_iter: eventArrayList) {
            if (e_iter.getEventCode() == String.valueOf(userCode)) {
                MainActivity.total_points += e_iter.getEventPoints();
                MainActivity.monthly_points += e_iter.getEventPoints();

                //Save data into shared preferences
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt(TOTALPOINTS, MainActivity.total_points);
                // Commit the edits
                editor.commit();

                //Reload main activity
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getpoints);
        final EditText eventEdit = (EditText)findViewById(R.id.eventCodeTextField);

        Button get_points = (Button)findViewById(R.id.gp_points_button);
        get_points.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.gp_points_button: {
                        int eventCode = Integer.parseInt(eventEdit.getText().toString());
                        getPointsFromEvent(eventCode);
                        break;
                    }
                }
            }
        });
    };
}
