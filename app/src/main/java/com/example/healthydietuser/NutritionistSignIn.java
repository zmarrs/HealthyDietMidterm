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
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 *  Nutritionist Sign In Class
 *  Allows nutritionists to sign in to access their questions.
 */
public class NutritionistSignIn extends AppCompatActivity {
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
        setContentView(R.layout.activity_nutritionist_sign_in);

        Button signInButton = findViewById(R.id.buttonSignInNut2);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginNutritionist(v);
            }
        });

        ImageButton cancelButton = findViewById(R.id.imageButtonCancelNutSignIn);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     *  Login Nutritionist Function
     *  Checks the SQLite Database for nutritionist credentials.
     *  Logs the Nutritionist in on a match
     *
     * @param View view - the current view
     *
     */
    public void loginNutritionist(View view) {
        EditText usernameEditText = findViewById(R.id.editTextUserNameNutSignIn);
        EditText passwordEditText = findViewById(R.id.editTextPasswordNutSignIn);
        TextView errorLabel = findViewById(R.id.textViewErrorLabelNutSignIn);
        TextView errorMessage = findViewById(R.id.textViewErrorMessageNutSignIn);

        // Get user input
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        // Query the database to check if the user exists and if the password is correct
        boolean userExists = checkNutritionistCredentials(username, password);

        if (userExists) {
            // Login was successful
            Intent intent = new Intent(this, Success.class);
            intent.putExtra("caller", "NutritionistSignIn");
            intent.putExtra("nutId", getId(username, password));
            startActivity(intent);
        } else {
            // Login failed
            errorLabel.setText(R.string.error_label);
            errorMessage.setText(R.string.user_not_found_err);
        }
    }
    /**
     *  Check Nutritionist Credential Function
     *  Checks the SQLite Database for user credentials.
     *  Error messages display if no match is found.
     *  If a match is found, the user is sent to the success screen
     *
     * @param username  - A String nutritionist inputed username
     * @param password  - A String, nutritionist inputed password
     *
     * @return boolean Whether the login is valid or not
     */
    private boolean checkNutritionistCredentials(String username, String password) {
        SQLiteDatabase db = new DatabaseHelper(this).getReadableDatabase();
        String[] columns = {"username"};
        String selection = "username = ? AND password = ?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query("nutritionists", columns, selection, selectionArgs, null, null, null);

        boolean userExists = cursor.moveToFirst();
        cursor.close();
        db.close();

        return userExists;
    }


    /**
     *  Get Id Function
     *  Gets an nutritionist ID by username and password
     *
     * @param int username          - the nutritionist inputted username
     * @param int username          - the nutritionist's inputted password
     * @return int userId           - the nutritionist's id
     *
     */
    public int getId(String username, String password) {
        SQLiteDatabase db = new DatabaseHelper(this).getReadableDatabase();
        int userId = -1;
        String[] columns = {"id"};
        String selection = "username = ? AND password = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query("users", columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int idColumnIndex = cursor.getColumnIndex("id");
            if (idColumnIndex != -1) {
                userId = cursor.getInt(idColumnIndex);
            }
            cursor.close();
        }
        return userId;
    }
}