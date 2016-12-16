package com.simpleideas.gymmate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by George Ciopei on 12/12/2016.
 */

public class DatabaseHelper {

    private DatabaseManager databaseManager;

    private SQLiteDatabase sqLiteDatabase;

    public DatabaseHelper(Context context){

        databaseManager = new DatabaseManager(context);
        sqLiteDatabase = databaseManager.getWritableDatabase();

    }

    public ArrayList<ExerciseTemplate> getExerciseFromDatabaseBasedOnDifference(int difference){

        ArrayList<ExerciseTemplate> arrayListToReturn = new ArrayList<>();

        String searchItem = String.valueOf(difference);

        String[] columns = {"DIFFERENCE"};
        String selection = "DIFFERENCE" + " =?";
        String[] selectionArgs = { searchItem };

        Cursor cursor = sqLiteDatabase.query(Constants.EXERCISE_TABLE, columns, selection, selectionArgs, null,null,null);
        cursor.moveToFirst();

        return arrayListToReturn;

    }
    public void insertExerciseIntoDatabase(String date, String exercise_name, int repetitions, float weight){

        ContentValues values = new ContentValues();
        values.put("Difference", date);
        values.put("Exercise", exercise_name);
        values.put("Repetitions", repetitions);
        values.put("Weight", weight);


        sqLiteDatabase.insert(Constants.first_table, null, values);

        sqLiteDatabase.close();


    }

    public boolean checkIfExerciseExists(int difference){


        String searchItem = String.valueOf(difference);

        String[] columns = {"DIFFERENCE"};
        String selection = "DIFFERENCE" + " =?";
        String[] selectionArgs = { searchItem };

        Cursor cursor = sqLiteDatabase.query(Constants.EXERCISE_TABLE, columns, selection, selectionArgs, null,null,null);

        boolean exists = (cursor.getCount() > 0);

        cursor.close();
        sqLiteDatabase.close();

        return exists;

    }

    public ArrayList<ExerciseTemplate> getAllExercises(String difference){

        ArrayList<ExerciseTemplate> arrayToReturn = new ArrayList<>();

        String searchItem = String.valueOf(difference);
        String[] columns = {"Difference", "Exercise", "Weight", "Repetitions"};
        String selection = "Difference=?";
        String[] selectionArgs = {searchItem};

        Cursor cursor = sqLiteDatabase.query(Constants.first_table, columns, selection, selectionArgs, null,null,null);

//        if(cursor!=null){
//            cursor.moveToFirst();
//        }

        int exerciseIndex = cursor.getColumnIndexOrThrow("Exercise");
        int differenceIndex = cursor.getColumnIndexOrThrow("Difference");
        int weightIndex = cursor.getColumnIndex("Weight");
        int repsIndex = cursor.getColumnIndex("Repetitions");

        while (cursor.moveToNext()){

            String exerciseName = cursor.getString(exerciseIndex);
            int differenceI = cursor.getInt(differenceIndex);
            float weight = cursor.getFloat(weightIndex);
            int reps = cursor.getInt(repsIndex);

            ExerciseTemplate exerciseTemplate = new ExerciseTemplate(exerciseName,differenceI, reps, weight);

            arrayToReturn.add(exerciseTemplate);
        }

        //cursor.close();
        //sqLiteDatabase.close();

        return arrayToReturn;
    }

}
