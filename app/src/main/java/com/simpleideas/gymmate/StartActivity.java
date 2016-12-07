package com.simpleideas.gymmate;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class StartActivity extends AppCompatActivity
        implements DynamicFragment.DataSenderBetweenFragments {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        createSharedPreferences(getApplicationContext());
        setupActionBar();
        setupPagerAdapter();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_dynamic_fragment, menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    //    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        int id = item.getItemId();
//
//        switch (id) {
//
//            case R.id.action_current_day:
//
//                int differenceRemaining = viewPager.getCurrentItem() - 50000;
//
//                viewPager.setCurrentItem(viewPager.getCurrentItem() - differenceRemaining );
//
//        }
//
//        return super.onOptionsItemSelected(item);
//
//    }

    private String[] getMuscleGroups(){

        String[] muscleGroups = {Constants.ARMS, Constants.BACK, Constants.CHEST, Constants.LEGS, Constants.SHOULDERS};

        return muscleGroups;

    }

    private void createSharedPreferences(Context context){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        if(preferences.contains(Constants.GROUPS)){
            return;

        }
        else{
            SharedPreferences.Editor editor = preferences.edit();

            String[] muscleGroups = {Constants.BACK, Constants.CHEST, Constants.LEGS, Constants.ARMS, Constants.SHOULDERS};

            Set<String> muscleSet = new HashSet<>(Arrays.asList(muscleGroups));

            editor.putStringSet(Constants.GROUPS, muscleSet);

            editor.apply();
        }

        String[] groups = getMuscleGroups();
        HashMap<String, String[]> muscleMap = new HashMap<>();

        String[] chest = {"Bench Press", "Incline Bench Press", "Flyes", "Pushups"};
        String[] back = {"Pullups", "Deadlift", "Bentover row", "Cable Row"};
        String[] legs = {"Squats", "Leg Press", "Leg Curls"};
        String[] arms = {"Barbell Curl", "Cable Curl", "Hammer Curl"};
        String[] shoulders = {"Military Press", "Arnold Press="};

        muscleMap.put(Constants.ARMS, arms);
        muscleMap.put(Constants.BACK, back);
        muscleMap.put(Constants.CHEST, chest);
        muscleMap.put(Constants.LEGS, legs);
        muscleMap.put(Constants.SHOULDERS, shoulders);

        for (String element:groups) {
            SharedPreferences preferencesMuscle = getSharedPreferences(element, MODE_PRIVATE);

            SharedPreferences.Editor editor = preferencesMuscle.edit();

            Set<String> muscle = new HashSet<>(Arrays.asList(muscleMap.get(element)));

            editor.putStringSet(element, muscle);

            editor.apply();

        }
    }



    private void checkCredentials(){
        Log.d("RemoteDatabase","doinbackground before while");
        AccesRemoteDatabase start_background_thread = new AccesRemoteDatabase();
        start_background_thread.execute(Constants.PHP_SCRIPT_LOCAL_HOST);
        System.out.print("after connection");
//        viewPager.setAdapter(adapter);
//        viewPager.setCurrentItem(50000,false);

//        if(!credentials.contains("TOKEN")){
////            ViewPager viewPager = (ViewPager)findViewById(R.id.view_pager_main_activity);
////            EndlessPagerAdapter adapter = new EndlessPagerAdapter(getSupportFragmentManager());
////            AccesRemoteDatabase start_background_thread = new AccesRemoteDatabase();
////            String TEST_URL = " http://echo.jsontest.com/key/value/one/two";
////            start_background_thread.execute(TEST_URL);
////            viewPager.setAdapter(adapter);
////            viewPager.setCurrentItem(50000,false);
//            SharedPreferences.Editor editor = credentials.edit();
//            editor.putString("TOKEN", "token");
//            editor.commit();
//
//        }
//        else{
//            viewPager = (ViewPager)findViewById(R.id.view_pager_main_activity);
//            EndlessPagerAdapter adapter = new EndlessPagerAdapter(getSupportFragmentManager());
//            AccesRemoteDatabase start_background_thread = new AccesRemoteDatabase();
//            start_background_thread.execute(PHP_SCRIPT_LOCAL_HOST);
//            viewPager.setAdapter(adapter);
//            viewPager.setCurrentItem(50000,false);
//        }

    }


    private void setupPagerAdapter(){
        ViewPager viewPager;
        viewPager = (ViewPager)findViewById(R.id.view_pager_main_activity);
        EndlessPagerAdapter adapter = new EndlessPagerAdapter(getSupportFragmentManager(), getApplicationContext());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(50000,false);
    }

    private void setupActionBar(){
        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
    }

    private void callPhpScript(){

        AccesRemoteDatabase start_background_thread = new AccesRemoteDatabase();
        start_background_thread.execute(Constants.PHP_SCRIPT_LOCAL_HOST);

    }


















    //@Override
//    public void sendData(String message_to_send) {
//
//        ScreenTwo sc2 = (ScreenTwo)getFragmentManager().findFragmentById(R.id.scrtwo);
//        sc2.getData(message_to_send);
//
//    }

    @Override
    public void sendPhoto(Bitmap picture) {

    }

}


