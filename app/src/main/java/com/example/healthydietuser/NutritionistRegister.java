/**
 *  Name: Zachary Marrs
 *  Date: October 24, 2023
 *  Assignment: Midterm - Healthy Diet App
 *  Class: Mobile App Development CS 458 P - 001
 *  Professor: Essa Imhmed
 */
package com.example.healthydietuser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
/**
 *  Nutritionist Register Class
 *  Allows new nutritionists to be added to the database.
 */
public class NutritionistRegister extends AppCompatActivity {
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
        setContentView(R.layout.activity_nutritionist_register);

        Button registerButton = findViewById(R.id.buttonRegisterNut);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNutritionist(v);
            }
        });

        ImageButton cancelButton = findViewById(R.id.imageButtonCancelNutReg);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     *  Register Nutritionist Function
     *  Error messages display if fields are empty.
     *  Calls another function to submit fields to database
     *  Navigates to the success screen on successful entry.
     *
     * @param View view  - the view calling the function
     */
    public void registerNutritionist(View view) {
        // Get references to the username and password EditText fields
        EditText usernameEditText = findViewById(R.id.editTextUserNameNutReg);
        EditText passwordEditText = findViewById(R.id.editTextPasswordNutReg);
        TextView errorLabel = findViewById(R.id.textViewErrorLabelNutReg);
        TextView errorMessage = findViewById(R.id.textViewErrorMessageNutReg);

        // Get user input
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        // Check if the username is not empty and password meets your criteria
        if (!username.isEmpty() && !password.isEmpty()) {
            // Insert user into the database
            long userId = insertUser(username, password);
            if (userId != -1) {
                Intent intent = new Intent(this, Success.class);
                intent.putExtra("caller", "NutritionistRegister");
                startActivity(intent);
            } else {
                // Registration failed
                errorLabel.setText(R.string.error_label);
                errorMessage.setText(R.string.user_exists_err);
            }
        } else {
            // Username or password is empty
            errorLabel.setText(R.string.error_label);
            errorMessage.setText(R.string.empty_err);
        }
    }

    /**
     *  Insert User Function
     *  Adds a new nutritionist to the database
     *  Error messages display if no match is found.
     *
     * @param username  - A String, nutritionist inputted username
     * @param password  - A String, nutritionist inputted password
     *
     * @return boolean Whether the login is valid or not
     */
    private long insertUser(String username, String password) {
        SQLiteDatabase db = new DatabaseHelper(this).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        long userId = db.insert("users", null, values);
        db.close();
        return userId;
    }
}