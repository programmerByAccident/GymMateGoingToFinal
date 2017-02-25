package com.simpleideas.gymmate;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by George Ciopei on 2/17/2017.
 */

public class CaldroidCustomImplementation extends AppCompatActivity{
    
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.caldroid);

        DateFormat format = new SimpleDateFormat("", Locale.ENGLISH);

        ArrayList<Map<Date, Drawable>> mapArrayList = new ArrayList<>();

        createCaldroidBehavior();

    }

    public void createCaldroidBehavior() {

        final CaldroidFragment caldroidFragment = new CaldroidFragment();
        
        DatabaseHelper databaseHelper = new DatabaseHelper(CaldroidCustomImplementation.this);
        
        caldroidFragment.setCaldroidListener(new CaldroidListener() {

            @Override
            public void onLongClickDate(Date date, View view) {
                super.onLongClickDate(date, view);
            }

            @Override
            public void onChangeMonth(int month, int year) {
                super.onChangeMonth(month, year);

                Toast.makeText(CaldroidCustomImplementation.this, String.valueOf(month) + " " + String.valueOf(year), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSelectDate(Date date, View view) {



            }

            @Override
            public void onCaldroidViewCreated() {
                super.onCaldroidViewCreated();
                caldroidFragment.getLeftArrowButton().setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_chevron_left));
                caldroidFragment.getRightArrowButton().setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_chevron_right));
            }
        });


        Map<String, ArrayList<String>> mapDateWithMuscleGroups = new HashMap<>();

        for (String availableDate:
             databaseHelper.getDaysWithWorkout()) {

            mapDateWithMuscleGroups.put(availableDate, databaseHelper.getMuscleNames(availableDate));

        }

        for (Map.Entry<String, ArrayList<String>> mapInseration:
             mapDateWithMuscleGroups.entrySet()) {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E - d - MMMM - yyyy", Locale.ENGLISH);

            Date date = new Date();
            try {
                date = simpleDateFormat.parse(mapInseration.getKey());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            CustomLayerDrawable customLayerDrawable = new CustomLayerDrawable(mapInseration.getValue().size(), CaldroidCustomImplementation.this);

            caldroidFragment.setBackgroundDrawableForDate(customLayerDrawable.getLayerDrawable(), date);


        }

//        ArrayList<ArrayList<ExerciseTemplate>> map = new ArrayList<>();
//
//        for (String inseration:
//                databaseHelper.getDaysWithWorkout()) {
//
//            map.add(databaseHelper.getAllExercises(inseration));
//
//
//        }
//
//        Map<String, ArrayList<ExerciseTemplate>> mapDateWIthOptions = new HashMap<>();
//
//        for (String inseration:
//             databaseHelper.getDaysWithWorkout()) {
//            Log.d("FIrst for", inseration);
//            ArrayList<ExerciseTemplate> exercisesForEachDay = new ArrayList<>();
//
//            for (ArrayList<ExerciseTemplate> inserationExerciseTemplateList:
//                 map) {
//                Log.d("Inseration Value", inserationExerciseTemplateList.get(0).getDifference());
//                Log.d("Primary iteration", inseration);
//                if(inseration.equals(inserationExerciseTemplateList.get(0).getDifference())){
//                    Log.d("First if", "if");
//                    if(mapDateWIthOptions.containsKey(inseration) == false){
//                        Log.d("Second if", "if");
//                        mapDateWIthOptions.put(inseration, inserationExerciseTemplateList);
//
//                    }
//
//                }
//
//            }
//        }
//
//        Log.d("Size of final map", String.valueOf(mapDateWIthOptions.size()));
//
//        for (Map.Entry<String, ArrayList<ExerciseTemplate>> entry:
//             mapDateWIthOptions.entrySet()) {
//            Log.d(entry.getKey(), "eachIteration");
//            for (ExerciseTemplate inserationTemplate:
//                 entry.getValue()) {
//                Log.d(inserationTemplate.getDifference(), inserationTemplate.getMuscleName());
//            }
//        }


        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        
        Map<Date, Drawable> dateDrawableMap = new HashMap<>();

        DateFormat simpleDateFormat = new SimpleDateFormat("E - d - MMMM - yyyy", Locale.ENGLISH);

        String dateString = "Sat - 17 - February - 2017";
        Date date = null;
        try {
            date = simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        CustomLayerDrawable customLayerDrawable = new CustomLayerDrawable(4, getApplicationContext());

        //dateDrawableMap.put(date, ContextCompat.getDrawable(CaldroidCustomImplementation.this, R.drawable.oval_shape));
        dateDrawableMap.put(date, customLayerDrawable.getLayerDrawable());

        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        caldroidFragment.setBackgroundDrawableForDates(dateDrawableMap);
        caldroidFragment.setArguments(args);
        caldroidFragment.refreshView();
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.caldroid_object, caldroidFragment);
        t.commit();

    }

    public void behaviourForCaldroid(){

        Toast.makeText(CaldroidCustomImplementation.this, "plm", Toast.LENGTH_SHORT).show();

    }

}
