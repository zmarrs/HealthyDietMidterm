/**
 *  Name: Zachary Marrs
 *  Date: October 24, 2023
 *  Assignment: Midterm - Healthy Diet App
 *  Class: Mobile App Development CS 458 P - 001
 *  Professor: Essa Imhmed
 */
package com.example.healthydietuser;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import java.util.List;
import java.util.ArrayList;
import android.content.ContentValues;

/**
 *  DatabaseHelper Class
 *  Provides several functions for getting and adding records from the database for sign in, registration, and question/answer queries
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "HealthyDiet.db";
    private static final int DATABASE_VERSION = 1;

    // Define table and column names

    private static final String TABLE_USERS = "users";
    private static final String TABLE_QUESTIONS = "questions";
    private static final String TABLE_NUTRITIONISTS = "nutritionists";

    private static final String USER_ID = "id";
    private static final String USER_NAME = "username";
    private static final String PASSWORD = "password";

    private static final String QUESTION_ID = "id";
    private static final String QUESTION_TEXT = "question_text";
    private static final String QUESTION_ANSWER = "answer";


    private static final String NUTRITIONIST_ID = "nutritionist_id";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

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
    public void onCreate(SQLiteDatabase db) {
        // Create tables for users and questions
        String createUsersTable = "CREATE TABLE " + TABLE_USERS + " (" +
                USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                USER_NAME + " TEXT," +
                PASSWORD + " TEXT)";
        db.execSQL(createUsersTable);

        String createQuestionsTable = "CREATE TABLE " + TABLE_QUESTIONS + " (" +
                QUESTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                USER_ID + " INTEGER," +
                NUTRITIONIST_ID + " INTEGER, " +
                QUESTION_TEXT + " TEXT," +
                QUESTION_ANSWER + " TEXT)";
        db.execSQL(createQuestionsTable);

        String createNutritionistTable = "CREATE TABLE " + TABLE_NUTRITIONISTS + " (" +
                NUTRITIONIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                USER_NAME + " TEXT," +
                PASSWORD + " TEXT)";
        db.execSQL(createNutritionistTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Upgrade the database if needed
    }

    /**
     *  Get All Questions Function
     *  Gets all question text from the database for a particular nutritionist, for output to a list of buttons
     *
     * @param int nutritionistID         - the nutritionist
     * @return List<String> questionList - the questions assigned to them
     *
     */
    public List<String> getAllQuestions(int nutritionistId) {
        List<String> questionList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"question_text"};
        String selection = "nutritionist_id = ?";
        String[] selectionArgs = {String.valueOf(nutritionistId)};

        Cursor cursor = db.query("questions", columns, selection, selectionArgs, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int questionTextColumnIndex = cursor.getColumnIndex("question_text");

                if (questionTextColumnIndex != -1) {
                    do {
                        String question = cursor.getString(questionTextColumnIndex);
                        questionList.add(question);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
        }

        db.close();
        return questionList;
    }

    /**
     *  Get Question Text By Id Function
     *  Gets a question text from the database for a particular question.
     *
     * @param int questionID         - the question
     * @return String answer          - the answer to the question
     *
     */
    public String getQuestionTextById(int questionId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"question_text"};
        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(questionId)};

        Cursor cursor = db.query("questions", columns, selection, selectionArgs, null, null, null);

        String questionText = null;

        if (cursor != null) {
            int questionTextColumnIndex = cursor.getColumnIndex("question_text");
            if (questionTextColumnIndex != -1) {
                while (cursor.moveToNext()) {
                    questionText = cursor.getString(questionTextColumnIndex);
                }
            }
            cursor.close();
        }

        db.close();
        return questionText;
    }


    /**
     *  Get Question Answers By Id Function
     *  Gets an answer from the database for a particular question.
     *
     * @param int questionID         - the question
     * @return String answer          - the answer to the question
     *
     */
    public String getQuestionAnswerById(int questionId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"answer"};
        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(questionId)};

        Cursor cursor = db.query("questions", columns, selection, selectionArgs, null, null, null);

        String answer = null;

        if (cursor != null) {
            int questionTextColumnIndex = cursor.getColumnIndex("answer");
            if (questionTextColumnIndex != -1) {
                while (cursor.moveToNext()) {
                    answer = cursor.getString(questionTextColumnIndex);
                }
            }
            cursor.close();
        }

        db.close();
        return answer;
    }

    /**
     *  Update Answer Function
     *  Adds a new answer to the database for a nutritionist, for a particular question.
     *
     * @param int questionID         - the question being answered
     * @param String answer          - the nutritionist inputed answer text
     *
     */
    public int updateAnswer(int questionId, String answer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("answer", answer);

        String whereClause = "question_id = ?";
        String[] whereArgs = {String.valueOf(questionId)};

        int rowsAffected = db.update("answers", values, whereClause, whereArgs);

        db.close();
        return rowsAffected;
    }

    /**
     *  Save Question Function
     *  Adds a new question to the database for a user, assigned to a provider id.
     *
     * @param int userId         - the userId from login
     * @param int nutId          - the user inputed nutritionist ID
     * @param String question    - the user inputed question text
     *
     */
    public void saveQuestion(int userId, int nutId, String question) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_ID, userId);
        values.put(NUTRITIONIST_ID, nutId);
        values.put(QUESTION_TEXT, question);
        long newRowId = db.insert("questions", null, values);
        db.close();
    }

    /**
     *  Get Unanswered Questions Function
     *  Checks the SQLite Database for unanswered questions for a user.
     *
     * @param userId    - the userId from login
     * @return List<String> unansweredQuestions - A list of questions which have not been answered by userID
     */
    public List<String> getUnansweredQuestions(int userId) {
        List<String> unansweredQuestions = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {"question_text"};
        String selection = "user_id = ? AND answer IS NULL"; // Check for unanswered questions
        String[] selectionArgs = {String.valueOf(userId)};

        Cursor cursor = db.query("questions", columns, selection, selectionArgs, null, null, null);

        if (cursor != null) {
            int questionTextIndex = cursor.getColumnIndex("question_text");
            while (cursor.moveToNext()) {
                if (questionTextIndex != -1) {
                    String question = cursor.getString(questionTextIndex);
                    unansweredQuestions.add(question);
                }
            }
            cursor.close();
        }

        db.close();
        return unansweredQuestions;
    }

    /**
     *  Get Answered Questions Function
     *  Checks the SQLite Database for answered questions for a user.
     *
     * @param userId    - the userId from login
     * @return List<String> answeredQuestions - A list of questions which have been answered by userID
     */
    public List<String> getAnsweredQuestions(int userId) {
        List<String> answeredQuestions = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {"question_text"};
        String selection = "user_id = ? AND answer IS NOT NULL"; // Check for answered questions
        String[] selectionArgs = {String.valueOf(userId)};

        Cursor cursor = db.query("questions", columns, selection, selectionArgs, null, null, null);

        if (cursor != null) {
            int questionTextIndex = cursor.getColumnIndex("question_text");
            while (cursor.moveToNext()) {
                if (questionTextIndex != -1) {
                    String question = cursor.getString(questionTextIndex);
                    answeredQuestions.add(question);
                }
            }
            cursor.close();
        }

        db.close();
        return answeredQuestions;
    }
}
