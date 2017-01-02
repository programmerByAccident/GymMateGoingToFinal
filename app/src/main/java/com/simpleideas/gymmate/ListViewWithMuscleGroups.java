package com.simpleideas.gymmate;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
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
    String date;
    RecyclerView muscles;
    MuscleGroupsAdapter muscleGroupsAdapter;
    private DialogForAddingContent dialogForAddingContent;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.muscle_groups);
        //setupActionBar();
        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.muscle_groups_toolbar);
        setSupportActionBar(toolbar);
        muscles = (RecyclerView) findViewById(R.id.muscle_groups);
        Intent intent = getIntent();

        dialogForAddingContent = DialogForAddingContent.newInstance(Constants.GROUPS);

        int difference = intent.getExtras().getInt("Difference");
        date = intent.getExtras().getString("date");
        ArrayList<String> muscleGroups = getMuscleGroups();


        Toast.makeText(getApplicationContext(), String.valueOf(muscleGroups.size()), Toast.LENGTH_SHORT).show();


        muscleGroupsAdapter = new MuscleGroupsAdapter(muscleGroups, this, date);
        muscles.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        muscles.setAdapter(muscleGroupsAdapter);


        //setAdapterToMuscleGroupListView(muscles);
        //setOnItemClickListenerMuscleGroupsListView(muscles, muscleGroupsAdapter, difference, date);

    }

    @Override
    protected void onResume() {
        super.onResume();
        ArrayList<String> muscleList = getMuscleGroups();
        muscleGroupsAdapter.setMuscleGroups(muscleList);
        muscleGroupsAdapter.notifyDataSetChanged();

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

        switch (item.getItemId()){

            case R.id.action_addition:

                //dialogForAddingContent.show(getSupportFragmentManager(), "tag");
                Dialog dialog = new Dialog(this);

                dialog.setContentView(R.layout.dialog_fragment);

                dialog.show();

                Toast.makeText(this, "addition", Toast.LENGTH_SHORT).show();

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
