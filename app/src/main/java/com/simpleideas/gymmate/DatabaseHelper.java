package com.simpleideas.gymmate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by George Ciopei on 12/12/2016.
 */

public class DatabaseHelper {

    private DatabaseManager databaseManager;

    private SQLiteDatabase sqLiteDatabase;

    public DatabaseHelper(Context context){

        databaseManager = new DatabaseManager(context);
        this.sqLiteDatabase = databaseManager.getWritableDatabase();

    }

//    public ArrayList<ExerciseTemplate> getExerciseFromDatabaseBasedOnDifference(int difference){
//
//        ArrayList<ExerciseTemplate> arrayListToReturn = new ArrayList<>();
//
//        String searchItem = String.valueOf(difference);
//
//        String[] columns = {"DIFFERENCE"};
//        String selection = "DIFFERENCE" + " =?";
//        String[] selectionArgs = { searchItem };
//
//        Cursor cursor = sqLiteDatabase.query(Constants.EXERCISE_TABLE, columns, selection, selectionArgs, null,null,null);
//        cursor.moveToFirst();
//
//        return arrayListToReturn;
//
//    }
    public void insertExerciseIntoDatabase(String date,String muscle_name, String exercise_name, int repetitions, float weight){

        sqLiteDatabase = databaseManager.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("Difference", date);
        values.put("Exercise", exercise_name);
        values.put("Repetitions", repetitions);
        values.put("Weight", weight);
        values.put("Muscle", muscle_name);


        sqLiteDatabase.insert(Constants.first_table, null, values);

        sqLiteDatabase.close();


    }

//    public void deleteRecord(String date, String exercise_name, String repetitions, String weight){
//
//        String table = Constants.first_table;
//        String whereClause = "Difference  = ? AND Exercise = ? AND Repetitions = ? and Weight = ?";
//        String[] whereTo = {date, exercise_name, repetitions, weight};
//
//        sqLiteDatabase.delete(table, whereClause, whereTo);
//
//    }

    public void deleteRecord(String date, String exercise_name, String repetitions, String weight){


        String deletestatement = "DELETE FROM " + Constants.first_table + " WHERE Difference = '" + date+ "'" + " AND Exercise = '" + exercise_name +"'"+ " AND Repetitions = '" + repetitions +"'"+ " AND Weight = '" + weight+"'";

        sqLiteDatabase.execSQL(deletestatement);
        sqLiteDatabase.close();


    }

    public boolean checkIfExerciseExists(int difference){


        String searchItem = String.valueOf(difference);

        String[] columns = {"DIFFERENCE"};
        String selection = "DIFFERENCE" + " =?";
        String[] selectionArgs = { searchItem };

        Cursor cursor = sqLiteDatabase.query(Constants.EXERCISE_TABLE, columns, selection, selectionArgs, null,null,null);

        boolean exists = (cursor.getCount() > 0);

        //cursor.close();
        sqLiteDatabase.close();

        return exists;

    }

    public ArrayList<String> exerciseNames(String difference){

        ArrayList<String> names = new ArrayList<>();

        String searchItem = String.valueOf(difference);
        String[] columns = {"Exercise"};
        String selection = "Difference=?";
        String[] selectionArgs = {searchItem};
        Cursor cursor = sqLiteDatabase.query(Constants.first_table, columns, selection, selectionArgs, null,null,null);

        int exerciseIndex = cursor.getColumnIndex("Exercise");

        while(cursor.moveToNext()){
            String exercise = cursor.getString(exerciseIndex);
            if(names.contains(exercise) == false){
                names.add(exercise);
            }

        }

        return names;
    }



    public ArrayList<String> getMuscleNames(String difference){
        ArrayList<String> muscleGroups = new ArrayList<>();

        String searchItem = String.valueOf(difference);
        String[] columns = {"Muscle"};
        String selection = "Difference=?";
        String[] selectionArgs = {searchItem};
        Cursor cursor = sqLiteDatabase.query(Constants.first_table, columns, selection, selectionArgs, null,null,null);


        while(cursor.moveToNext()){
            String muscle = cursor.getString(cursor.getColumnIndex("Muscle"));

            if(muscleGroups.contains(muscle) == false){
                muscleGroups.add(muscle);
            }
        }

        return muscleGroups;
    }

    public ArrayList<String> getExerciseName(String difference){
        ArrayList<String> muscleGroups = new ArrayList<>();

        String searchItem = String.valueOf(difference);
        String[] columns = {"Exercise"};
        String selection = "Difference=?";
        String[] selectionArgs = {searchItem};
        Cursor cursor = sqLiteDatabase.query(Constants.first_table, columns, selection, selectionArgs, null,null,null);


        while(cursor.moveToNext()){
            String muscle = cursor.getString(cursor.getColumnIndex("Exercise"));

            if(muscleGroups.contains(muscle) == false){
                muscleGroups.add(muscle);
            }
        }

        return muscleGroups;
    }

    public ArrayList<String> getDaysWithWorkout(){

        sqLiteDatabase = databaseManager.getWritableDatabase();

        ArrayList<String> listToReturn = new ArrayList<>();

        String[] columns = {"DIFFERENCE"};

        Cursor cursor = sqLiteDatabase.query(Constants.first_table, columns,null,null,null,null,null,null);

        while(cursor.moveToNext()){

            String date = cursor.getString(cursor.getColumnIndex("Difference"));

            listToReturn.add(date);
        }

        return listToReturn;
    }
    
    public void insertColorsIntoDatabase(HashMap<String, String> colorMap){

        sqLiteDatabase = databaseManager.getWritableDatabase();

        for (Map.Entry<String, String> inserationIntoDatabase:
             colorMap.entrySet()) {
            ContentValues values = new ContentValues();

            values.put("ColorName", inserationIntoDatabase.getKey());
            values.put("ColorValue", inserationIntoDatabase.getValue());

            sqLiteDatabase.insert(Constants.first_table, null, values);
        }

        sqLiteDatabase.close();
        
    }
    
    public ArrayList<ExerciseTemplate> getAllExercises(String difference){

        sqLiteDatabase = databaseManager.getWritableDatabase();

        ArrayList<ExerciseTemplate> arrayToReturn = new ArrayList<>();

        String searchItem = String.valueOf(difference);
        String[] columns = {"Difference", "Exercise", "Weight", "Repetitions", "Muscle"};
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
        int muscleIndex = cursor.getColumnIndex("Muscle");

        try {
            while (cursor.moveToNext()){

                String exerciseName = cursor.getString(exerciseIndex);
                String muscleName = cursor.getString(muscleIndex);
                String differenceI = cursor.getString(differenceIndex);
                float weight = cursor.getFloat(weightIndex);
                int reps = cursor.getInt(repsIndex);

                ExerciseTemplate exerciseTemplate = new ExerciseTemplate(muscleName,exerciseName,differenceI, reps, weight);

                arrayToReturn.add(exerciseTemplate);
            }

            //cursor.close();

        } finally {
            cursor.close();
            sqLiteDatabase.close();
        }

        return arrayToReturn;
    }

    public ArrayList<ExerciseTagInformation> getAllExercisesMapped(String difference){

        sqLiteDatabase = databaseManager.getWritableDatabase();

        ArrayList<ExerciseTemplate> arrayToReturn = new ArrayList<>();

        String searchItem = String.valueOf(difference);
        String[] columns = {"Difference", "Exercise","Muscle", "Weight", "Repetitions"};
        String selection = "Difference=?";
        String[] selectionArgs = {searchItem};

        Cursor cursor = sqLiteDatabase.query(Constants.first_table, columns, selection, selectionArgs, null,null,null);

        int exerciseIndex = cursor.getColumnIndexOrThrow("Exercise");
        int differenceIndex = cursor.getColumnIndexOrThrow("Difference");
        int weightIndex = cursor.getColumnIndex("Weight");
        int repsIndex = cursor.getColumnIndex("Repetitions");
        int muscleIndex = cursor.getColumnIndex("Muscle");

        try {
            while (cursor.moveToNext()){

                String exerciseName = cursor.getString(exerciseIndex);
                String differenceI = cursor.getString(differenceIndex);
                float weight = cursor.getFloat(weightIndex);
                int reps = cursor.getInt(repsIndex);
                String muscleName = cursor.getString(muscleIndex);

                ExerciseTemplate exerciseTemplate = new ExerciseTemplate(muscleName, exerciseName,differenceI, reps, weight);

                arrayToReturn.add(exerciseTemplate);
            }

            //cursor.close();

        } finally {
            cursor.close();
            sqLiteDatabase.close();
        }

        ArrayList<String> keyPopulated = new ArrayList<>();

        for (ExerciseTemplate exerciseTemplate:
                arrayToReturn) {
            if(keyPopulated.contains(exerciseTemplate.getExerciseName())== false){

                keyPopulated.add(exerciseTemplate.getExerciseName());
            }
        }


        ArrayList<ExerciseTagInformation> exercisesMapped = new ArrayList<>();

        for (String key:
                keyPopulated) {

            ExerciseTagInformation exerciseTagInformation = new ExerciseTagInformation();
            ArrayList<String> weights = new ArrayList<>();
            ArrayList<String> repetitions = new ArrayList<>();
            exerciseTagInformation.setTAG(key);
            int counter = 0;

            for (ExerciseTemplate exerciseTemplate:
                 arrayToReturn) {

                if(exerciseTemplate.getExerciseName().equals(key)){

                    weights.add(counter, String.valueOf(exerciseTemplate.getWeight()));
                    repetitions.add(counter, String.valueOf(exerciseTemplate.getRepetition()));
                    counter++;
                }

            }

            exerciseTagInformation.setWeight(weights);
            exerciseTagInformation.setRepetition(repetitions);

            exercisesMapped.add(exerciseTagInformation);
        }

        return exercisesMapped;







    }
}
