package com.simpleideas.gymmate;


/**
 * Created by Geprge on 10/25/2016.
 */

public class SQL_COMMANDS {

    public SQL_COMMANDS(){}

    public String create_table(String TABLE_NAME, String ID, String FIRST_COLUMN, String SECOND_COLUMN, String THIRD_COLUMN){

        String create_query = "";

        if(SECOND_COLUMN.isEmpty()){
            create_query = "CREATE TABLE " + TABLE_NAME + " (" + ID +
                           " INTEGER PRIMARY KEY," + FIRST_COLUMN +
                           " TEXT" + " )";
        }
        if(THIRD_COLUMN.isEmpty()){
            create_query = "CREATE TABLE " + TABLE_NAME + " (" + ID +
                           " INTEGER PRIMARY KEY," + FIRST_COLUMN +
                           " TEXT" + "," + SECOND_COLUMN + " TEXT" + " )";

        }
        else{
            create_query = "CREATE TABLE " + TABLE_NAME + " (" + ID +
                    " INTEGER PRIMARY KEY," + FIRST_COLUMN +
                    " TEXT" + "," + THIRD_COLUMN + " TEXT" + " )";

        }
        return create_query;

    }


    public String create_exercise_table(String TABLE_NAME, String ID, String DIFFERENCE_FOR_DAYS, String EXERCISE_OBJECT){

        String query = "CREATE TABLE " + TABLE_NAME + " (" + ID + " INTEGER PRIMARY KEY," + DIFFERENCE_FOR_DAYS + " INTEGER" + EXERCISE_OBJECT + " BLOB not null" + " )";


        return query;
    }

}
