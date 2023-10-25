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
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.List;


/**
 * Unanswered Questions Activity
 * Allows user to view any questions from the database that match their user id and have no answer.
 *
 */
public class Unanswered extends AppCompatActivity {
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
        setContentView(R.layout.activity_unanswered);
        Intent intent = getIntent();
        int userId = intent.getIntExtra("userId", 0);

        ListView questionListView = findViewById(R.id.listViewUnanswered);
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        final List<String> questionList = dbHelper.getUnansweredQuestions(userId); // Retrieve unanswered questions

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, questionList);
        questionListView.setAdapter(adapter);

        ImageButton cancelButton = findViewById(R.id.imageButtonCancelUnanswered);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}