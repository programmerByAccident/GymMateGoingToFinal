package com.simpleideas.gymmate;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import java.io.IOException;

/**
 * Created by Geprge on 12/3/2016.
 */

public class ExerciseIODatabase {

    private SQLiteDatabase sqLiteDatabase;
    private DatabaseManager databaseManager;


    public ExerciseIODatabase(Context context){

        databaseManager = new DatabaseManager(context);

    }

    public void open() throws SQLException{

        sqLiteDatabase = databaseManager.getWritableDatabase();

    }

    public void close(){

        databaseManager.close();

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void insertDifferenceAndObject(int difference, ExerciseTemplate exerciseTemplate){

        sqLiteDatabase = databaseManager.getWritableDatabase();

        ContentValues values = new ContentValues();

        byte[] byteArray = SerializationClient.serializeExerciseTemplateObject(exerciseTemplate);

        values.put("Difference", difference);
        values.put("Object", byteArray);

        sqLiteDatabase.insert(Constants.EXERCISE_TABLE, null, values);
    }

    public ExerciseTemplate mapExerciseObjectWithDate(int difference, ExerciseTemplate exerciseTemplate){

        sqLiteDatabase = databaseManager.getReadableDatabase();






        return null;

    }

    public boolean checkIfExerciseExists(int difference){



        boolean values = databaseManager.checkIfExerciseExists(difference);

        return values;
    }

}
