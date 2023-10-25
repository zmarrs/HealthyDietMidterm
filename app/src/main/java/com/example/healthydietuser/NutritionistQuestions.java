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
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.view.View;
import java.util.List;


/**
 *  Nutritionist Questions Class
 *  Displays a full list of answered questions from users for a particular nutritionist, to facilitate answering them.
 */
public class NutritionistQuestions extends AppCompatActivity {
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
        setContentView(R.layout.activity_nutritionist_questions);
        Intent intent = getIntent();
        int nutId = intent.getIntExtra("nutId", 0);

        ListView questionListView = findViewById(R.id.listViewQuestions);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        final List<String> questionList = dbHelper.getAllQuestions(nutId); // Retrieve questions

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, questionList);
        questionListView.setAdapter(adapter);

        questionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedQuestion = questionList.get(position);
                Intent intent = new Intent(NutritionistQuestions.this, NutritionistAnswer.class);
                intent.putExtra("question", selectedQuestion);
                startActivity(intent);
            }
        });

        ImageButton cancelButton = findViewById(R.id.imageButtonCancelNutQuestions);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}