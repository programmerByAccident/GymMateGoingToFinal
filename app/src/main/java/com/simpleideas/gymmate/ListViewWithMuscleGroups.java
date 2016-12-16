package com.simpleideas.gymmate;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Geprge on 12/4/2016.
 */

public class ListViewWithMuscleGroups extends AppCompatActivity {

    private ArrayList<String> muscleGroups;


    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.muscle_groups);
        super.onCreate(savedInstanceState);
        setupActionBar();
        ListView muscles = (ListView) findViewById(R.id.muscle_groups);
        Intent intent = getIntent();

        int difference = intent.getExtras().getInt("Difference");
        String date = intent.getExtras().getString("date");
        ArrayList<String> muscleGroups = getMuscleGroups();

        MuscleGroupsAdapter muscleGroupsAdapter = new MuscleGroupsAdapter(muscleGroups, getApplicationContext());

        muscles.setAdapter(muscleGroupsAdapter);

        //setAdapterToMuscleGroupListView(muscles);
        setOnItemClickListenerMuscleGroupsListView(muscles, muscleGroupsAdapter, difference, date);

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

    private void setOnItemClickListenerMuscleGroupsListView(ListView listView, final MuscleGroupsAdapter adapter, final int difference, final String date){

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                ArrayList<String> muscleNames = new ArrayList<>();

                String muscleName = (String) adapter.getItem(i);

                Intent intent = new Intent(getApplicationContext(), CertainMuscleListView.class);

                intent.putExtra(Constants.MUSCLE_NAME, muscleName);
                intent.putExtra("Difference", difference);
                intent.putExtra("date", date);
                startActivity(intent);

            }
        });

    }

    private void setAdapterToMuscleGroupListView(ListView listView){

    }

    //================================================================================
    // Get muscle groups from sharedPreferences
    //================================================================================

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_muscle_category, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.action_addition){
            Toast.makeText(getApplicationContext(),"message", Toast.LENGTH_LONG);
        }

        return super.onOptionsItemSelected(item);
    }

    private ArrayList<String> getMuscleGroups(){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        SharedPreferences.Editor editor = preferences.edit();

        ArrayList<String> arralist = new ArrayList<>();

        if(preferences.contains(Constants.GROUPS)){

            arralist.addAll(preferences.getStringSet(Constants.GROUPS, new HashSet<String>()));

        }

        return  arralist;

    }


    private void setupActionBar(){
        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.muscle_groups_toolbar);
        setSupportActionBar(toolbar);
    }

}
