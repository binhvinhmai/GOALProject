package com.example.binhmai.goalproject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

/**
 * Created by binhmai on 12/11/16.
 */
public class SubmitFeedbackActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submitfeedback);

        //Attach to form variables
        final EditText feedBackEdit = (EditText)findViewById(R.id.miscFeedback);
        final RatingBar satisfactionRating = (RatingBar)findViewById(R.id.satisfiedRating);
        final RatingBar organizedRating = (RatingBar)findViewById(R.id.organizedRatingBar);
        final RatingBar spaceRating = (RatingBar)findViewById(R.id.spaceRatingBar);
        Button cancel = (Button)findViewById(R.id.noFeedback);
        Button submitFeedBack = (Button)findViewById(R.id.submitFeedback);

        //Add event handlers
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reloadMain();
                return;
            }
        });
        submitFeedBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switch (v.getId()){

                }
                String emailString = compileData(satisfactionRating.getRating(), organizedRating.getRating(), spaceRating.getRating(), feedBackEdit.getText());
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");

                emailIntent.putExtra(Intent.EXTRA_EMAIL, "binhvinhmai@gmail.com");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, "binhvinhmai@gmail.com");
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback for your event");
                emailIntent.putExtra(Intent.EXTRA_TEXT, emailString);
                try {
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                    finish();
                    Log.i("Finished sending...", "");
                } catch (android.content.ActivityNotFoundException ex) {
                    Log.d("FAILED", "to send an email");
                    Toast.makeText(SubmitFeedbackActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }
                reloadMain();
                return;

            }
        });

    };

    public String compileData(float satRating, float orgRating, float spaceRating, Editable feedBackEdit) {
        //Takes in the various rating bars and compiles them into an email friendly format
        String emailString = "According to this one user (out of 5 for each score), you scored "
                + satRating+ " in satisfaction, \n"
                + orgRating + "in how organized the event was, \n"
                + spaceRating + " in terms of how well organized the space was, \n "
                + "They also had this to say: "
                + feedBackEdit;
        return emailString;
    }

    public void reloadMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
