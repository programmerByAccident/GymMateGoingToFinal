package com.simpleideas.gymmate;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Geprge on 12/4/2016.
 */

public class ListViewWithMuscleGroups extends Activity {

    private ArrayList<String> muscleGroups;


    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.muscle_groups);
        super.onCreate(savedInstanceState);

        ListView muscles = (ListView) findViewById(R.id.muscle_groups);

        ArrayList<String> muscleGroups = getMuscleGroups();

        MuscleGroupsAdapter muscleGroupsAdapter = new MuscleGroupsAdapter(muscleGroups, getApplicationContext());

        muscles.setAdapter(muscleGroupsAdapter);

        //setAdapterToMuscleGroupListView(muscles);
        setOnItemClickListenerMuscleGroupsListView(muscles, muscleGroupsAdapter);

    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //================================================================================
    // MuscleGroups listview actions(setAdapter and setOnItemClickListener)
    //================================================================================

    private void setOnItemClickListenerMuscleGroupsListView(ListView listView, final MuscleGroupsAdapter adapter){

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                ArrayList<String> muscleNames = new ArrayList<>();

                String muscleName = (String) adapter.getItem(i);

                Intent intent = new Intent(getApplicationContext(), CertainMuscleListView.class);

                intent.putExtra(Constants.MUSCLE_NAME, muscleName);

                startActivity(intent);

            }
        });

    }

    private void setAdapterToMuscleGroupListView(ListView listView){

    }

    //================================================================================
    // Get muscle groups from sharedPreferences
    //================================================================================

    private ArrayList<String> getMuscleGroups(){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        SharedPreferences.Editor editor = preferences.edit();

        ArrayList<String> arralist = new ArrayList<>();

        if(preferences.contains(Constants.GROUPS)){

            arralist.addAll(preferences.getStringSet(Constants.GROUPS, new HashSet<String>()));

        }

        return  arralist;

    }




}
