package com.simpleideas.gymmate;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Geprge on 12/6/2016.
 */

public class CertainMuscleListView extends AppCompatActivity {
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
        setOnItemClickListenerMuscleGroupsListView(exercisesListView, muscleGroupsAdapter, muscle_name);
    }


    private Set<String> getExercises(String muscle_name){

        SharedPreferences sharedPreferences = getSharedPreferences(muscle_name, MODE_PRIVATE);

        return sharedPreferences.getStringSet(muscle_name, null);

    }

    private void setOnItemClickListenerMuscleGroupsListView(ListView listView, final MuscleGroupsAdapter adapter,final String muscleName){

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                ArrayList<String> muscleNames = new ArrayList<>();

                String exerciseName = (String) adapter.getItem(i);

                Intent intent = new Intent(getApplicationContext(), InsertActivity.class);

                intent.putExtra(Constants.EXERCISE_NAME, exerciseName);

                intent.putExtra(Constants.MUSCLE_NAME, muscleName);

                startActivity(intent);

            }
        });

    }
}
