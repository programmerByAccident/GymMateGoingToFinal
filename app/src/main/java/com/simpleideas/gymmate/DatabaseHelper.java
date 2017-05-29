package com.simpleideas.gymmate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
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

    public ArrayList<String> getAllHexCodes(){

        ArrayList<String> colors = new ArrayList<>();
        String[] columns = {"color_hex_code"};
        Cursor cursor = sqLiteDatabase.query(Constants.color_map, columns,null,null,null,null,null);
        cursor.moveToFirst();
        int color = cursor.getColumnIndex("color_hex_code");
        while (cursor.moveToNext()){

            String string = cursor.getString(color);
            colors.add(string);

        }
        return colors;

    }

    public HashMap<String, Integer> mapNumberOfWorkoutsPerMonthForPieChart(String month){

        sqLiteDatabase = databaseManager.getReadableDatabase();

        String[] columns = {};
        String selection = "Difference LIKE ?";
        String[] selectionArgs = { month };
        Cursor cursor = sqLiteDatabase.query(Constants.first_table, columns, selection, selectionArgs,null,null,null);

        HashMap<String, Integer> map = new HashMap<>();

        return map;

    }

    public int selectColorBasedOnMuscle(String muscle){
        Log.d("astaVineCaParametru", muscle);
        String stringToReturn = null;
        String[] columns = {"color_hex_code"};
        String selection = "muscle_name=?";
        String[] selectionArgs = { muscle };
        Cursor cursor = sqLiteDatabase.query(Constants.color_map, columns, selection, selectionArgs, null,null,null);
        int color = cursor.getColumnIndex("color_hex_code");
        while(cursor.moveToNext()){
            stringToReturn = cursor.getString(color);
            Log.d("Color from database", stringToReturn);
        }

        Log.d("CEPLMEAICI", stringToReturn);
        return Color.parseColor("#"+stringToReturn);
    }

    public HashMap<String, ArrayList<String>> mapDateWithMuscles(){
        HashMap<String, ArrayList<String>> musclemap = new HashMap<>();
        ArrayList<String> dates = getDaysWithWorkout();
        for (String availableDate:
        dates) {

            musclemap.put(availableDate, getMuscleNames(availableDate));

        }

        for (Map.Entry<String,ArrayList<String>> entry:
             musclemap.entrySet()) {

            Log.d("PLMMMMMM", String.valueOf(entry.getValue()));

        }
        return musclemap;
    }



    public void saveOrUpdate(ArrayList<String> hexCodes, ArrayList<String> muscleNames){


        String[] columns = { "color_hex_code" };
        Cursor cursor = sqLiteDatabase.query(Constants.color_map, columns,null,null,null,null,null);
        if(cursor.moveToFirst()){

            return;

        }
        else{

            sqLiteDatabase = databaseManager.getWritableDatabase();
            String sql = "insert into " + Constants.color_map + " (color_hex_code) VALUES (?);";
            String sql_muscle = "UPDATE " + Constants.color_map + " SET muscle_name = ? WHERE _id=?;";
            for (String hexCode:
                 hexCodes) {
                sqLiteDatabase.execSQL(sql, new String[]{hexCode});
            }
            for (int i = 0; i <muscleNames.size() ; i++) {

                sqLiteDatabase.execSQL(sql_muscle, new String[]{muscleNames.get(i), String.valueOf(i+1)});

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

        sqLiteDatabase.close();

        return exists;
    }

    public void insertColorBasedOnPositionPick(String hexcode, String muscle){

        sqLiteDatabase = databaseManager.getWritableDatabase();
        String sql_muscle = "UPDATE " + Constants.color_map + " SET muscle_name = ? WHERE color_hex_code=?;";
        String sql_insert = "INSERT INTO " + Constants.color_map + " (color_hex_code, muscle_name) values(?,?);";


        String searchItem = muscle;
        String[] columns = { "muscle_name"};
        String selection = "muscle_name = ?";
        String[] selectionArgs = {searchItem};

        Cursor cursor = sqLiteDatabase.query(Constants.color_map, columns, selection, selectionArgs, null,null,null);

        if (isCursorEmpty(cursor)){

            sqLiteDatabase.execSQL(sql_insert, new String[]{hexcode, muscle});

        }else{
            sqLiteDatabase.execSQL(sql_muscle, new String[]{hexcode, muscle});
        }

        sqLiteDatabase.close();

    }
    public boolean isCursorEmpty(Cursor cursor){
        if(!cursor.moveToFirst() || cursor.getCount() == 0) return true;
        return false;
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

    public ArrayList<Float> getLast10WeightRecords(String exercise){

        ArrayList<Float> getLast10 = new ArrayList<>();

        String SearchItem = String.valueOf(exercise);
        String[] columns = {"Weight"};
        String selection = "Exercise=?";
        String[] selectionArgs = {exercise};

        Cursor cursor = sqLiteDatabase.query(Constants.first_table,columns,selection,selectionArgs,null,null,"_id DESC", "10");
        int weightIndex = cursor.getColumnIndex("Weight");
        while (cursor.moveToNext()){
            getLast10.add(cursor.getFloat(weightIndex));
        }

        return getLast10;
    }
    public ArrayList<Integer> getLast10RepsRecords(String exercise){

        ArrayList<Integer> getLast10 = new ArrayList<>();

        String SearchItem = String.valueOf(exercise);
        String[] columns = {"Repetitions"};
        String selection = "Exercise=?";
        String[] selectionArgs = {exercise};

        Cursor cursor = sqLiteDatabase.query(Constants.first_table,columns,selection,selectionArgs,null,null,"_id DESC", "10");
        int weightIndex = cursor.getColumnIndex("Repetitions");
        while (cursor.moveToNext()){
            getLast10.add(cursor.getInt(weightIndex));
        }
        Collections.sort(getLast10);
        return getLast10;
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

    public List<PieEntry> getMonthMappedWithNumbers(String[] monthMap){
        HashMap<Integer, ArrayList<String>> monthBasedDate = new HashMap<>();
        List pieEntryList = new ArrayList();


        sqLiteDatabase = databaseManager.getWritableDatabase();
        String[] columns = {"Difference", "Exercise", "Muscle"};
        String selection = "Difference LIKE ?";
        //String[] monthMap = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

        for (int i = 0; i < monthMap.length; i++){

            String[] selectionArgs = {"%"+monthMap[i]+"%"};

            Cursor cursor = sqLiteDatabase.query(Constants.first_table, columns,selection,selectionArgs,null,null,null);

            int differenceIndex = cursor.getColumnIndex("Difference");
            int exerciseIndex = cursor.getColumnIndex("Exercise");
            int muscleIndex = cursor.getColumnIndex("Muscle");
            ArrayList<String> days = new ArrayList<>();
            while(cursor.moveToNext()){

                if (days.contains(cursor.getString(differenceIndex)) == false){
                    days.add(cursor.getString(differenceIndex));
                }


            }

            monthBasedDate.put(i, days);
        }

        for (Map.Entry<Integer, ArrayList<String>> insertion:
             monthBasedDate.entrySet()) {

            if(insertion.getValue().size() != 0){
                PieEntry pieEntry = new PieEntry(insertion.getValue().size(), monthMap[insertion.getKey()]);
                pieEntryList.add(pieEntry);
            }


        }


        return pieEntryList;
    }

    public HashMap<Integer, ArrayList<String>> getInformationOnMonthlyBasesCategoryWised(String[] monthMap){

        HashMap<Integer, ArrayList<String>> monthBasedDate = new HashMap<>();


        sqLiteDatabase = databaseManager.getWritableDatabase();
        String[] columns = {"Difference", "Exercise", "Muscle"};
        String selection = "Difference LIKE ? and Muscle = ?";
        //String[] monthMap = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};


        for (int i = 0; i < monthMap.length; i++){

            String[] selectionArgs = {"%"+monthMap[i]+"%"};

            Cursor cursor = sqLiteDatabase.query(Constants.first_table, columns,selection,selectionArgs,null,null,null);

            int differenceIndex = cursor.getColumnIndex("Difference");
            int exerciseIndex = cursor.getColumnIndex("Exercise");
            int muscleIndex = cursor.getColumnIndex("Muscle");
            ArrayList<String> days = new ArrayList<>();
            while(cursor.moveToNext()){

                if (days.contains(cursor.getString(differenceIndex)) == false){
                    days.add(cursor.getString(differenceIndex));
                }


            }

            monthBasedDate.put(i, days);
        }


        return monthBasedDate;

    }

    public HashMap<Integer, ArrayList<String>> getInformationOnMonthlyBasis(String exerciseName){

        HashMap<Integer, ArrayList<String>> monthBasedDate = new HashMap<>();


        sqLiteDatabase = databaseManager.getWritableDatabase();
        String[] columns = {"Difference", "Exercise", "Muscle"};
        String selection = "Difference LIKE ? and Exercise = ?";
        String[] monthMap = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};


        for (int i = 0; i < monthMap.length; i++){

            String[] selectionArgs = {"%"+monthMap[i]+"%", exerciseName};

            Cursor cursor = sqLiteDatabase.query(Constants.first_table, columns,selection,selectionArgs,null,null,null);

            int differenceIndex = cursor.getColumnIndex("Difference");
            int exerciseIndex = cursor.getColumnIndex("Exercise");
            int muscleIndex = cursor.getColumnIndex("Muscle");
            ArrayList<String> days = new ArrayList<>();
            while(cursor.moveToNext()){

                if (days.contains(cursor.getString(differenceIndex)) == false){
                    days.add(cursor.getString(differenceIndex));
                }


            }

            monthBasedDate.put(i, days);
        }


        return monthBasedDate;


    }

    public HashMap<Integer, ArrayList<String>> getInformationOnMonthlyBasis(){

        HashMap<Integer, ArrayList<String>> monthBasedDate = new HashMap<>();


        sqLiteDatabase = databaseManager.getWritableDatabase();
        String[] columns = {"Difference", "Exercise", "Muscle"};
        String selection = "Difference LIKE ?";
        String[] monthMap = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};


        for (int i = 0; i < monthMap.length; i++){

            String[] selectionArgs = {"%"+monthMap[i]+"%"};


            Cursor cursor = sqLiteDatabase.query(Constants.first_table, columns,selection,selectionArgs,null,null,null);

            int differenceIndex = cursor.getColumnIndex("Difference");
            int exerciseIndex = cursor.getColumnIndex("Exercise");
            int muscleIndex = cursor.getColumnIndex("Muscle");
            ArrayList<String> days = new ArrayList<>();
            while(cursor.moveToNext()){

                if (days.contains(cursor.getString(differenceIndex)) == false){
                    days.add(cursor.getString(differenceIndex));
                }


            }

            monthBasedDate.put(i, days);
        }


        return monthBasedDate;


    }

    public ArrayList<ExerciseTemplate> getInformationForExercise(String exerciseName){

        sqLiteDatabase = databaseManager.getWritableDatabase();

        ArrayList<ExerciseTemplate> arrayToReturn = new ArrayList<>();

        String searchItem = String.valueOf(exerciseName);
        String[] columns = {"Difference", "Exercise", "Weight", "Repetitions", "Muscle"};
        String selection = "Exercise=?";
        String[] selectionArgs = {searchItem};

        Cursor cursor = sqLiteDatabase.query(Constants.first_table, columns, selection, selectionArgs, null,null,null);

        int exerciseIndex = cursor.getColumnIndexOrThrow("Exercise");
        int differenceIndex = cursor.getColumnIndexOrThrow("Difference");
        int weightIndex = cursor.getColumnIndex("Weight");
        int repsIndex = cursor.getColumnIndex("Repetitions");
        int muscleIndex = cursor.getColumnIndex("Muscle");

        try {
            while (cursor.moveToNext()){

                String exerciseRecord = cursor.getString(exerciseIndex);
                String muscleName = cursor.getString(muscleIndex);
                String differenceI = cursor.getString(differenceIndex);
                float weight = cursor.getFloat(weightIndex);
                int reps = cursor.getInt(repsIndex);

                ExerciseTemplate exerciseTemplate = new ExerciseTemplate(muscleName,exerciseRecord,differenceI, reps, weight);

                arrayToReturn.add(exerciseTemplate);
            }

            //cursor.close();

        } finally {
            cursor.close();
            sqLiteDatabase.close();
        }

        return arrayToReturn;

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

        for (String day:
             listToReturn) {

            Log.d("PLM trebuie sa fie", day);

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
