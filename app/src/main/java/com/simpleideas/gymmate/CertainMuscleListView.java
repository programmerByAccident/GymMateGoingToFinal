package com.simpleideas.gymmate;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Geprge on 12/6/2016.
 */

public class CertainMuscleListView extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.certain_muscle_group);
        ListView exercisesListView = (ListView) findViewById(R.id.muscleExerciseList);
        String muscle_name;

        Intent intent = getIntent();

        muscle_name = intent.getExtras().getString(Constants.MUSCLE_NAME);

        Set<String> exercises = getExercises(muscle_name);
        ArrayList<String> exercisesForAdapter = new ArrayList<>();
        exercisesForAdapter.addAll(exercises);
        MuscleGroupsAdapter muscleGroupsAdapter = new MuscleGroupsAdapter(exercisesForAdapter, getApplicationContext());
        exercisesListView.setAdapter(muscleGroupsAdapter);

    }


    private Set<String> getExercises(String muscle_name){

        SharedPreferences sharedPreferences = getSharedPreferences(muscle_name, MODE_PRIVATE);

        return sharedPreferences.getStringSet(muscle_name, null);

    }
}
