/**
 *  Name: Zachary Marrs
 *  Date: October 24, 2023
 *  Assignment: Midterm - Healthy Diet App
 *  Class: Mobile App Development CS 458 P - 001
 *  Professor: Essa Imhmed
 */
package com.example.healthydietuser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.content.Context;
import android.widget.TextView;


/**
 * Login and Registration Success Activity
 * Gives login and registration success messages and routes users and Nutritionists to the right app section
 * Passes nutritionist and user ids appropriately
 * After a 6 second delay, navigation begins
 */
public class Success extends AppCompatActivity {
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
        setContentView(R.layout.activity_success);
        Intent intent = getIntent();
        String caller = intent.getStringExtra("caller");
        int nutId = intent.getIntExtra("nutId", 0);
        int userId = intent.getIntExtra("userId", 0);

        final Context context = this;
        TextView successMessage = findViewById(R.id.textViewSuccessMessage);

        if ("Register".equals(caller)) {
            successMessage.setText(R.string.reg_success);
            // Navigate to User Sign In after 6 seconds
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(context, SignIn.class);
                    startActivity(intent);
                    finish();
                }
            }, 6000);
        } else if ("SignIn".equals(caller)) {
            successMessage.setText(R.string.login_success);
            // Navigate to Messenger after 6 seconds
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(context, Messenger.class);
                    intent.putExtra("userId", userId);
                    startActivity(intent);
                    finish();
                }
            }, 6000);
        }else if ("NutritionistRegister".equals(caller)) {
            successMessage.setText(R.string.reg_success);
            // Navigate to Nutritionist Sign In after 6 seconds
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(context, NutritionistSignIn.class);
                    startActivity(intent);
                    finish();
                }
            }, 6000);
        }else if ("NutritionistSignIn".equals(caller)) {
            successMessage.setText(R.string.login_success);
            // Navigate to Nutritionist Questions after 6 seconds
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(context, NutritionistQuestions.class);
                    intent.putExtra("nutId", nutId);
                    startActivity(intent);
                    finish();
                }
            }, 6000);
        }
    }
}