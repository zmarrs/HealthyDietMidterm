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
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

/**
 *  Nutritionist Answer Class
 *  Gives a text input to allow nutritionists to respond to user questions.
 */
public class NutritionistAnswer extends AppCompatActivity {
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
        setContentView(R.layout.activity_nutritionist_answer);

        TextView questionTextView = findViewById(R.id.textViewQuestionTextAnswerer);
        EditText answerEditText = findViewById(R.id.editTextAnswerInput);
        Button submitButton = findViewById(R.id.buttonSubmitAnswer);

        Intent intent = getIntent();
        int questionId = intent.getIntExtra("question", 0);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        String questionText = dbHelper.getQuestionTextById(questionId); // Retrieve question text

        questionTextView.setText(questionText);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Save the answer to the database
                String answer = answerEditText.getText().toString();
                dbHelper.updateAnswer(questionId, answer);
                finish();
            }
        });
    }
}