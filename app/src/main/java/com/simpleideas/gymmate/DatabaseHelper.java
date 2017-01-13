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
    public void insertExerciseIntoDatabase(String date, String exercise_name, int repetitions, float weight){

        sqLiteDatabase = databaseManager.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("Difference", date);
        values.put("Exercise", exercise_name);
        values.put("Repetitions", repetitions);
        values.put("Weight", weight);


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

    public ArrayList<ExerciseTemplate> getAllExercises(String difference){

        sqLiteDatabase = databaseManager.getWritableDatabase();

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

        try {
            while (cursor.moveToNext()){

                String exerciseName = cursor.getString(exerciseIndex);
                int differenceI = cursor.getInt(differenceIndex);
                float weight = cursor.getFloat(weightIndex);
                int reps = cursor.getInt(repsIndex);

                ExerciseTemplate exerciseTemplate = new ExerciseTemplate(exerciseName,differenceI, reps, weight);

                arrayToReturn.add(exerciseTemplate);
            }

            //cursor.close();

        } finally {
            cursor.close();
            sqLiteDatabase.close();
        }

        return arrayToReturn;
    }

    public ArrayList getAllExercisesMapped(String difference){

        sqLiteDatabase = databaseManager.getWritableDatabase();

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

        try {
            while (cursor.moveToNext()){

                String exerciseName = cursor.getString(exerciseIndex);
                int differenceI = cursor.getInt(differenceIndex);
                float weight = cursor.getFloat(weightIndex);
                int reps = cursor.getInt(repsIndex);

                ExerciseTemplate exerciseTemplate = new ExerciseTemplate(exerciseName,differenceI, reps, weight);

                arrayToReturn.add(exerciseTemplate);
            }

            //cursor.close();

        } finally {
            cursor.close();
            sqLiteDatabase.close();
        }


        HashMap<String, ArrayList<String>> map = new HashMap<>();

        ArrayList<String> keyPopulated = new ArrayList<>();

        for (ExerciseTemplate exerciseTemplate:
                arrayToReturn) {
            if(keyPopulated.contains(exerciseTemplate.getExerciseName())== false){

                keyPopulated.add(exerciseTemplate.getExerciseName());
            }
        }


        ArrayList<HashMap<String, ArrayList>> finalList = new ArrayList<>();
        for (String key:
                keyPopulated) {

            HashMap<String, ArrayList> wtf = new HashMap<>();
            HashMap<String, HashMap> finalMap = new HashMap<>();
            ArrayList<HashMap<String,String>> temporaryList = new ArrayList<>();
            ArrayList<String> weight = new ArrayList<>();
            ArrayList<String> reps = new ArrayList<>();
            int counter = 0;
            for (ExerciseTemplate exerciseTemplate:
                 arrayToReturn) {
                Log.d("exerciseTemplateName", exerciseTemplate.getExerciseName());
                Log.d("key", key);

                if(exerciseTemplate.getExerciseName().equals(key)){
//                    weight.add(counter, String.valueOf(exerciseTemplate.getWeight()));
//                    counter++;
                    HashMap<String, String> keyMap = new HashMap<>();
                    keyMap.put(String.valueOf(exerciseTemplate.getWeight()), String.valueOf(exerciseTemplate.getRepetition()));
                    temporaryList.add(keyMap);
                }

            }
            weight.size();
            Log.d("temporaryList", String.valueOf(weight.size()));
            Log.d("Counter value ", String.valueOf(counter));
            wtf.put(key, temporaryList);
            finalList.add(wtf);
//            for(int index = 0; index < arrayToReturn.size(); index++){
//
//                if(String.valueOf(arrayToReturn.get(index).getExerciseName()) == key){
//                    keyMap.put(String.valueOf(arrayToReturn.get(index).getWeight()), String.valueOf(arrayToReturn.get(index).getRepetition()));
//                    String weightValue = String.valueOf(arrayToReturn.get(index).getWeight());
//                    String repValue = String.valueOf(arrayToReturn.get(index).getRepetition());
//                    weight.add(weightValue);
//                    reps.add(repValue);
//                    temporaryList.add(keyMap);
//                }
//
//            }


        }
        return finalList;







    }
}
