package com.simpleideas.gymmate;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;

import adapters.TabFragmentAdapter;

/**
 * Created by George Ciopei on 12/10/2016.
 */

public class InsertActivity extends AppCompatActivity implements TabHost.OnTabChangeListener{


    public String getExercise_name() {
        return exercise_name;
    }

    public String getMuscle_name() {
        return muscle_name;
    }

    public String getDate() {
        return date;
    }

    public int getDifference() {
        return difference;
    }

    public ArrayList<ExerciseTemplate> getArrayForInsertFragment() {
        return arrayForInsertFragment;
    }

    private ArrayList<ExerciseTemplate> arrayForInsertFragment;
    private String exercise_name;
    private String muscle_name;
    private String date;
    private int difference;
    private DatabaseHelper helper;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_activity_view_pager);
        helper = new DatabaseHelper(getApplicationContext());
        getInformationFromIntent();
        viewPager = (ViewPager) findViewById(R.id.inser_viewPager);
        tabLayout = (TabLayout) findViewById(R.id.insert_activity_tabs);

        tabLayout.setupWithViewPager(viewPager);
        TabFragmentAdapter fragmentAdapter = new TabFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(fragmentAdapter);
        toolbar_actions(exercise_name);

    }

    @Override
    public void onBackPressed() {

        if (viewPager.getCurrentItem() == 0){
            super.onBackPressed();
        }
        else{
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }

    }

    private void getInformationFromIntent(){

        Intent intent = getIntent();

        exercise_name = intent.getExtras().getString(Constants.EXERCISE_NAME);
        arrayForInsertFragment = helper.getInformationForExercise(exercise_name);
        muscle_name = intent.getExtras().getString(Constants.MUSCLE_NAME);
        difference = intent.getExtras().getInt("Difference");
        date = intent.getExtras().getString("date");


    }

    @Override
    public void onTabChanged(String s) {

    }
    private void toolbar_actions(String exercise_name){
        TextView textView = (TextView) findViewById(R.id.titleTextView);
        textView.setVisibility(View.VISIBLE);
        textView.setText(exercise_name);
    }
}
