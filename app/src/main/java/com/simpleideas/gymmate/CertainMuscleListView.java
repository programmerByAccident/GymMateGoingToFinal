package com.simpleideas.gymmate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Set;

import adapters.ExerciseRecyclerViewAdapter;

/**
 * Created by Geprge on 12/6/2016.
 */

public class CertainMuscleListView extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.certain_muscle_group);
        RecyclerView exercisesListView = (RecyclerView) findViewById(R.id.muscleExerciseList);
        String muscle_name;
        String date;
        Intent intent = getIntent();

        muscle_name = intent.getExtras().getString(Constants.MUSCLE_NAME);
        date = intent.getExtras().getString("date");
        Set<String> exercises = getExercises(muscle_name);
        ArrayList<String> exercisesForAdapter = new ArrayList<>();
        exercisesForAdapter.addAll(exercises);

        ExerciseRecyclerViewAdapter exerciseRecyclerViewAdapter = new ExerciseRecyclerViewAdapter(this, exercisesForAdapter,muscle_name, date);

        exercisesListView.setLayoutManager(new LinearLayoutManager(this));
        exercisesListView.addItemDecoration(new DividerItemDecoration(CertainMuscleListView.this,RecyclerView.VERTICAL));
        exercisesListView.setAdapter(exerciseRecyclerViewAdapter);
    }


    private Set<String> getExercises(String muscle_name){

        SharedPreferences sharedPreferences = getSharedPreferences(muscle_name, MODE_PRIVATE);

        return sharedPreferences.getStringSet(muscle_name, null);

    }

}
