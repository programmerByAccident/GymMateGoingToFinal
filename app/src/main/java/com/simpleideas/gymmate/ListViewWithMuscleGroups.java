package com.simpleideas.gymmate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;

import adapters.MuscleGroupsAdapter;

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

        //dialogForAddingContent = DialogForAddingContent.newInstance(Constants.GROUPS);
        int difference = intent.getExtras().getInt("Difference");
        date = intent.getExtras().getString("date");
        ArrayList<String> muscleGroups = getMuscleGroups();

        Toast.makeText(getApplicationContext(), String.valueOf(muscleGroups.size()), Toast.LENGTH_SHORT).show();


        muscleGroupsAdapter = new MuscleGroupsAdapter(muscleGroups, this, date);
      //  muscles.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        muscles.addItemDecoration(new DividerItemDecoration(ListViewWithMuscleGroups.this,RecyclerView.VERTICAL));
        muscles.setLayoutManager(new LinearLayoutManager(this));
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
                startActivity(new Intent(this, DialogForAddingContent.class).putExtra("Groups", Constants.GROUPS));
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

    private void createBehaviourForSearchBar(MuscleGroupsAdapter adapter){

        EditText searchInterface = (EditText) findViewById(R.id.search_bar);

        searchInterface.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



    }

    private void setupActionBar(){
        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.muscle_groups_toolbar);
        setSupportActionBar(toolbar);
    }

}











































