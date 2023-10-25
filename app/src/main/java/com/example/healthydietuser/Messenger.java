/**
 *  Name: Zachary Marrs
 *  Date: October 24, 2023
 *  Assignment: Midterm - Healthy Diet App
 *  Class: Mobile App Development CS 458 P - 001
 *  Professor: Essa Imhmed
 */
package com.example.healthydietuser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

/**
 *  Messenger Class
 *  A UI for the Users to ask new questions, and see unanswered or answered ones.
 */
public class Messenger extends AppCompatActivity {
    /**
     *  On Create Function - called when the activity is first created.
     *  The UI elements are identified and assigned immediately.
     *  On click listeners are added to the buttons to submit answers and move along to the next part of the questionnaire
     *  The submit button is only active for the first two questions, and afterwards functions as the restart button.
     *
     * @param savedInstanceState    If the activity needs to be reinitialized, this bundle
     *                              contains the data it most recently supplied in onSaveInstanceState(Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);

        Intent intent = getIntent();
        int userId = intent.getIntExtra("userId", 0);

        Button askButton = findViewById(R.id.buttonAsk);
        askButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code to open the new activity goes here
                Intent intent = new Intent(Messenger.this, Question.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });

        Button unansweredButton = findViewById(R.id.buttonUnanswered);
        unansweredButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code to open the new activity goes here
                Intent intent = new Intent(Messenger.this, Unanswered.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });

        Button answeredButton = findViewById(R.id.buttonAnswered);
        answeredButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code to open the new activity goes here
                Intent intent = new Intent(Messenger.this, Answered.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });

        ImageButton cancelButton = findViewById(R.id.imageButtonCancelMessenger);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}