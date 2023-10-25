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
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 *  Register Class
 *  Allows new users to be added to the database.
 */
public class Register extends AppCompatActivity {
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
        setContentView(R.layout.activity_register);

        Button registerButton = findViewById(R.id.buttonRegister);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code to open the new activity goes here
                registerUser(v);
            }
        });

        ImageButton cancelButton = findViewById(R.id.imageButtonCancelRegister);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    /**
     *  Register User Credential Function
     *  Error messages display if fields are empty.
     *  Calls another function to submit fields to database
     *  Navigates to the success screen on successful entry.
     *
     * @param View view  - the view calling the function
     */
    public void registerUser(View view) {
        // Get references to the username and password EditText fields
        EditText usernameEditText = findViewById(R.id.editTextUserName2);
        EditText passwordEditText = findViewById(R.id.editTextPassword2);
        TextView errorLabel = findViewById(R.id.textViewErrorLabel2);
        TextView errorMessage = findViewById(R.id.textViewErrorMessage);

        // Get user input
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        // Check if the username is not empty and password meets your criteria
        if (!username.isEmpty() && !password.isEmpty()) {
            // Insert user into the database
            long userId = insertUser(username, password);
            if (userId != -1) {
                Intent intent = new Intent(this, Success.class);
                intent.putExtra("caller", "Register");
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
     *  Insert User Credential Function
     *  Adds the new user to the SQLite Database.
     *  Error messages display if fields are empty.
     *  Navigates to the success screen on successful entry.
     *
     * @param String username  - the user inputed user name
     * @param String password  - the user inputed password
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