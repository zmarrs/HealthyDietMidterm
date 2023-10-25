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
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.EditText;

/**
 *  Question Class
 *  Allows users to enter a text question to be saved into the database for their nutritionist.
 */
public class Question extends AppCompatActivity {
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
        setContentView(R.layout.activity_question);
        Intent intent = getIntent();
        int userId = intent.getIntExtra("userId", 0);

        Button submitButton = findViewById(R.id.buttonSubmitQuestion);
        EditText nutIdInput = findViewById(R.id.editTextNumberNutId);
        EditText questionInput = findViewById(R.id.editTextMultiLineQuestionInput);
        DatabaseHelper dbHelper = new DatabaseHelper(this);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String question = questionInput.getText().toString();
                int nutId = Integer.parseInt(nutIdInput.getText().toString());

                // Save the question to the database
                dbHelper.saveQuestion(userId, nutId, question);
                finish();
            }
        });

        ImageButton cancelButton = findViewById(R.id.imageButtonCancelUserQuestion);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}