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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 *  Answered Class
 *  Displays a list of answered questions, for the viewer to get from the database, by user ID
 */
public class Answered extends AppCompatActivity {
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
        setContentView(R.layout.activity_answered);
        Intent intent = getIntent();
        int userId = intent.getIntExtra("userId", 0);

        ListView questionListView = findViewById(R.id.listViewAnswered);
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        final List<String> questionList = dbHelper.getAnsweredQuestions(userId); // Retrieve unanswered questions

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, questionList);
        questionListView.setAdapter(adapter);

        TextView questionText = findViewById(R.id.textViewQuestionTextAnswered);
        TextView answerText = findViewById(R.id.textViewAnswerTextAnswered);

        questionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedQuestion = questionList.get(position);
                questionText.setText(dbHelper.getQuestionTextById(Integer.parseInt(selectedQuestion)));
                answerText.setText(dbHelper.getQuestionAnswerById(Integer.parseInt(selectedQuestion)));
            }
        });

        ImageButton cancelButton = findViewById(R.id.imageButtonCancelAnswered);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}