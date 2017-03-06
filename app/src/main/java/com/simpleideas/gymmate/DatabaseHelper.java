package com.simpleideas.gymmate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Array;
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

    private Context context;

    public DatabaseHelper(Context context){

        databaseManager = new DatabaseManager(context);
        this.sqLiteDatabase = databaseManager.getWritableDatabase();
        this.context = context;

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


    public String selectColorBasedOnMuscle(String muscle){

        String stringToReturn = null;
        String[] columns = {"color_hex_code"};
        String selection = "muscle_name=?";
        String[] selectionArgs = { muscle };
        Cursor cursor = sqLiteDatabase.query(Constants.color_map, columns, selection, selectionArgs, null,null,null);
        int color = cursor.getColumnIndex("color_hex_code");
        while(cursor.moveToFirst()){
            stringToReturn = cursor.getString(color);
        }


        return stringToReturn;
    }

    public void saveOrUpdate(ArrayList<String> hexCodes, ArrayList<String> muscleNames){


        String[] columns = { "color_hex_code" };


        Cursor cursor = sqLiteDatabase.query(Constants.color_map, columns,null,null,null,null,null);

        if(cursor.moveToFirst()){

            Toast.makeText(context, "EXISTS", Toast.LENGTH_SHORT).show();

            return;

        }
        else{

            sqLiteDatabase = databaseManager.getWritableDatabase();
            Toast.makeText(context, "Does not Exist", Toast.LENGTH_SHORT).show();
            String sql = "insert into " + Constants.color_map + " (color_hex_code) VALUES (?);";
            String sql_muscle = "UPDATE " + Constants.color_map + " SET muscle_name = ? WHERE _id=?;";
            for (String hexCode:
                 hexCodes) {
                sqLiteDatabase.execSQL(sql, new String[]{hexCode});
            }
            for (int i = 0; i <muscleNames.size() ; i++) {

                sqLiteDatabase.execSQL(sql_muscle, new String[]{muscleNames.get(i), String.valueOf(i)});

            }

            sqLiteDatabase.close();
        }


    }

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


    public String getColorBaseOnMuscleName(String muscle_name){

        String[] columns = {"ColorName"};
        String selection = "Muscle=?";
        String[] selectionArgs = { muscle_name };
        Cursor cursor = sqLiteDatabase.query(Constants.first_table, columns, selection, selectionArgs, null,null,null);



        return "";
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

    public void updateRecordColor(String colorHexCode, String muscleName){

        sqLiteDatabase = databaseManager.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(muscleName, muscleName);
        String where = "color_hex_code=?";
        String[] whereArgs = { colorHexCode };
        sqLiteDatabase.update(Constants.color_map,values,where,whereArgs);
        sqLiteDatabase.close();

    }

    public void insertColorsHexCodes(ArrayList<String> hexCodes){

        sqLiteDatabase = databaseManager.getWritableDatabase();

        ContentValues values = new ContentValues();

        for (String hexcode:
             hexCodes) {
            values.put(hexcode, hexcode);
        }

    }

    public void insertColorsIntoDatabase(HashMap<String, String> colorMap){

        sqLiteDatabase = databaseManager.getWritableDatabase();

        for (Map.Entry<String, String> inserationIntoDatabase:
             colorMap.entrySet()) {
            ContentValues values = new ContentValues();

            values.put("MuscleName", inserationIntoDatabase.getKey());
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
