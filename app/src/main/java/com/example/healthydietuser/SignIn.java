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
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * User Sign In Activity
 * Allows user to check credentials and sign in to app
 *
 */
public class SignIn extends AppCompatActivity {

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
        setContentView(R.layout.activity_sign_in);

        Button signInButton = findViewById(R.id.buttonSignIn2);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser(v);
            }
        });

        ImageButton cancelButton = findViewById(R.id.imageButtonCancelSignIn);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    /**
     *  Login Function - called when the user clicks the submit button
     *  The UI elements are identified and assigned immediately.
     *  Checks the SQLite Database for user credentials.
     *  Error messages display if no match is found.
     *  If a match is found, the user is sent to the success screen
     *
     * @param View view  the view calling the login
     *
     */
    public void loginUser(View view) {
        EditText usernameEditText = findViewById(R.id.editTextUserName);
        EditText passwordEditText = findViewById(R.id.editTextPassword);
        TextView errorLabel = findViewById(R.id.textViewErrorLabel);
        TextView errorMessage = findViewById(R.id.textViewErrorMessage);

        // Get user input
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        // Query the database to check if the user exists and if the password is correct
        boolean userExists = checkUserCredentials(username, password);

        if (userExists) {
            // Login was successful
            Intent intent = new Intent(this, Success.class);
            intent.putExtra("caller", "SignIn");
            startActivity(intent);
        } else {
            // Login failed
            errorLabel.setText(R.string.error_label);
            errorMessage.setText(R.string.user_not_found_err);
        }
    }

    /**
     *  Check User Credential Function
     *  Checks the SQLite Database for user credentials.
     *  Error messages display if no match is found.
     *  If a match is found, the user is sent to the success screen
     *
     * @param username  - A String user inputed username
     * @param password  - A String, user inputed password
     *
     * @return boolean Whether the login is valid or not
     */
    private boolean checkUserCredentials(String username, String password) {
        SQLiteDatabase db = new DatabaseHelper(this).getReadableDatabase();
        String[] columns = {"username"};
        String selection = "username = ? AND password = ?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query("users", columns, selection, selectionArgs, null, null, null);

        boolean userExists = cursor.moveToFirst();
        cursor.close();
        db.close();

        return userExists;
    }
}