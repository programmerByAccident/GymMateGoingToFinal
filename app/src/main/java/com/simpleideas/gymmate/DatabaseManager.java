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


    private static final String DATABASENAME = "bazadedate.db";

    private static final int DATABASEVERSION = 4;

    private static final String database_table_creation = "create table firsttable  (_id integer primary key,Muscle varchar(255), Exercise varchar(255), Difference varchar(255),  Repetitions integer, Weight real);";

    private static final String database_color_table_creation = "create table color_map (_id integer primary key, color_hex_code varchar(255), muscle_name varchar(255));";

    public DatabaseManager(Context context){

        super(context, DATABASENAME, null, DATABASEVERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(database_table_creation);
        sqLiteDatabase.execSQL(database_color_table_creation);

//        sqLiteDatabase.execSQL(sql_commands.create_table("USERS" , "ID", "NAME", "", ""));
//        sqLiteDatabase.execSQL(sql_commands.create_table("MUSCLE_GROUP", "ID", "NAME", "", ""));
//        sqLiteDatabase.execSQL(sql_commands.create_table("RECORDS", "ID", "USER", "MUSCLE_GROUP", ""));

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists firsttable");
    }
}
