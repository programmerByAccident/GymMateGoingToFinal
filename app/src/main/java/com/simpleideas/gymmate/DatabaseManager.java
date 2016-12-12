package com.simpleideas.gymmate;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Geprge on 10/25/2016.
 */

public class DatabaseManager extends SQLiteOpenHelper {


    private SQL_COMMANDS sql_commands = new SQL_COMMANDS();

    public DatabaseManager(Context context){

        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION_1);
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void insertDifferenceAndObject(int difference, ExerciseTemplate exerciseTemplate){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        byte[] byteArray = SerializationClient.serializeExerciseTemplateObject(exerciseTemplate);

        values.put("Difference", difference);
        values.put("Object", byteArray);

        sqLiteDatabase.insert(Constants.EXERCISE_TABLE, null, values);
    }

    public void insertExerciseIntoDatabase(int difference, String exercise_name, int repetitions, float weight){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("Difference", difference);
        values.put("Exercise", exercise_name);
        values.put("Repetitions", repetitions);
        values.put("Weight", weight);


        sqLiteDatabase.insert(Constants.EXERCISE_TABLE, null, values);

        sqLiteDatabase.close();


    }

    public ExerciseTemplate mapExerciseObjectWithDate(int difference, ExerciseTemplate exerciseTemplate){

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();






        return null;

    }

    public ArrayList<ExerciseTemplate> getExerciseFromDatabaseBasedOnDifference(int difference){

        ArrayList<ExerciseTemplate> arrayListToReturn = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        String searchItem = String.valueOf(difference);

        String[] columns = {"DIFFERENCE"};
        String selection = "DIFFERENCE" + " =?";
        String[] selectionArgs = { searchItem };

        Cursor cursor = sqLiteDatabase.query(Constants.EXERCISE_TABLE, columns, selection, selectionArgs, null,null,null);
        cursor.moveToFirst();

        return arrayListToReturn;

    }

    public boolean checkIfExerciseExists(int difference){


        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

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

    public ArrayList<ExerciseTemplate> getAllExercises(int difference){

        ArrayList<ExerciseTemplate> arrayToReturn = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        String searchItem = String.valueOf(difference);
        String[] columns = {"DIFFERENCE"};
        String selection = "DIFFERENCE" + " =?";
        String[] selectionArgs = {searchItem};

        Cursor cursor = sqLiteDatabase.query(Constants.EXERCISE_TABLE, columns, selection, selectionArgs, null,null,null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        while (cursor.moveToNext()){

            String exerciseName = cursor.getString(cursor.getColumnIndex("EXERCISE_NAME"));
            int differenceI = cursor.getInt(cursor.getColumnIndex("DIFFERENCE"));
            float weight = cursor.getFloat(cursor.getColumnIndex("WEIGHT"));
            int reps = cursor.getInt(cursor.getColumnIndex("REPETITIONS"));

            ExerciseTemplate exerciseTemplate = new ExerciseTemplate(exerciseName,differenceI, reps, weight);

            arrayToReturn.add(exerciseTemplate);
        }

        cursor.close();
        sqLiteDatabase.close();

        return arrayToReturn;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(sql_commands.create_exercise_table(Constants.EXERCISE_TABLE, "ID", "DIFFERENCE", "EXERCISE_NAME", "WEIGHT", "REPETITIONS"));

//        sqLiteDatabase.execSQL(sql_commands.create_table("USERS" , "ID", "NAME", "", ""));
//        sqLiteDatabase.execSQL(sql_commands.create_table("MUSCLE_GROUP", "ID", "NAME", "", ""));
//        sqLiteDatabase.execSQL(sql_commands.create_table("RECORDS", "ID", "USER", "MUSCLE_GROUP", ""));

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
