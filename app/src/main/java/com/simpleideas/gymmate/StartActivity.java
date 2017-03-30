package com.simpleideas.gymmate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.nshmura.recyclertablayout.RecyclerTabLayout;

import com.facebook.FacebookActivity;
import com.facebook.login.widget.ProfilePictureView;
import com.roomorama.caldroid.CaldroidFragment;

import org.w3c.dom.Text;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import Fragments.DialogBoxForMetrics;
import adapters.EndlessPagerAdapter;
import de.hdodenhof.circleimageview.CircleImageView;

public class StartActivity extends AppCompatActivity implements DynamicFragment.DataSenderBetweenFragments, NavigationView.OnNavigationItemSelectedListener{


    HashMap<String, String[]> muscleMap;
    ViewPager viewPager;
    NavigationView navigationView;
    DrawerLayout Drawer;
    CaldroidFragment globalFragment;
    private CircleImageView globalForResume;
    private final String TAG = "StartActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Log.d(TAG, "onCreate: IF not passed");
        createSharedPreferences(getApplicationContext());
        setupActionBar();
        //setupDrawerLayout();
        setupPagerAdapter();
        checkColorMapExistanceIntoDatabase();
        navigationView = (NavigationView) findViewById(R.id.navigation_view);


        SharedPreferences preferences = getSharedPreferences(Constants.bitmap, MODE_PRIVATE);
        String encoded = preferences.getString("bitmap","123");
        byte[] imageAsBytes = Base64.decode(encoded,0);
        View headerLayout = navigationView.inflateHeaderView(R.layout.drawer_header);
        CircleImageView profilePictureView = (CircleImageView) headerLayout.findViewById(R.id.profile_image);
        TextView userCredentials = (TextView) headerLayout.findViewById(R.id.nameAndLastName);
        userCredentials.setText(preferences.getString("firstname","") + " "+ preferences.getString("lastname",""));
        globalForResume = profilePictureView;
        profilePictureView.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));


        navigationView.setNavigationItemSelectedListener(this);
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

    private void populateTheView(){


    }
        @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {

            case R.id.material_calendar_view:


//                Intent intent = new Intent(this, CalendarViewCustomized.class);
//
//                startActivity(intent);

                break;

            case R.id.adjustViewPager:
                viewPager.setCurrentItem(50000);


        }

        return super.onOptionsItemSelected(item);

    };

    private String[] getMuscleGroups(){

        String[] muscleGroups = {Constants.ARMS, Constants.BACK, Constants.CHEST, Constants.LEGS, Constants.SHOULDERS};

        return muscleGroups;

    }



    private void createSharedPreferences(Context context){



        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        if(preferences.contains(Constants.GROUPS)){
            Toast.makeText(context, "Exists", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            Toast.makeText(context, "Does not exist", Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor = preferences.edit();

            String[] muscleGroups = {Constants.BACK, Constants.CHEST, Constants.LEGS, Constants.ARMS, Constants.SHOULDERS};

            Set<String> muscleSet = new HashSet<>(Arrays.asList(muscleGroups));

            editor.putStringSet(Constants.GROUPS, muscleSet);

            editor.apply();
        }

        String[] groups = getMuscleGroups();
        muscleMap = new HashMap<>();

        String[] chest = {"Dumbbell Flyes", "Barbell Incline Bench Press", "Barbell Bench Press", "Dumbbell Bench Press", "Decline Dumbbell Flyes", "Incline Dumbbell Press"};
        String[] back = {"Barbell Deadlift", "Bent-Over Barbell Deadlift", "Wide-Grip Pull-Up", "Standing T-Bar Row","Wide-Grip Seated Cable Row", "Reverse-Grip Smith Machine Row","Close-Grip Pull-Down","Single-Arm Dumbbell Row","Decline Bench Dumbbell Pull-Over","Single-Arm Smith Machine Row"};
        String[] legs = {"Squat", "Front Squat", "Snatch","Power Clean","Deadlift","Bulgarian Split Squat","Hack Squat","Dumbbell Lunge","Leg Press","Romanian Deadlift","Machine Squat"};
        String[] arms = {"Standing Barbell Curl", "Standing Cable Curl", "Dumbbell Curl","Weighted Chin-Up","Reverse-Grip Barbell Row","Rope Hammer Curl","Incline-Bench Curl","Concentration Curl","Preacher Curl","Barbell Drag Curl"};
        String[] shoulders = {"Barbell Push Press", "Barbell Standing Military Press","Dumbbell Standing Military Press","Dumbbell Incline Row","Seated Overhead Dumbbell Press","Seated Overhead Barbell Press","Upright Row","Arnold Press","Dumbbell Lateral Raise","Front Dumbbell Raise"};

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


//    @Override
//    protected void onResume() {
//        super.onResume();
//        SharedPreferences preferences = getSharedPreferences(Constants.bitmap, MODE_PRIVATE);
//        String encoded = preferences.getString("bitmap","123");
//        byte[] imageAsBytes = Base64.decode(encoded,0);
//        globalForResume.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
//    }

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

    public int getViewPagerPosition(){

        return viewPager.getCurrentItem();
    }

    private void checkColorMapExistanceIntoDatabase(){

        DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());

        ArrayList<String> hexCodes = new ArrayList<>();

        hexCodes.add("800000");
        hexCodes.add("7FFFD4");
        hexCodes.add("7FFF00");
        hexCodes.add("7CFC00");
        hexCodes.add("7B68EE");
        hexCodes.add("778899");
        hexCodes.add("708090");
        hexCodes.add("6B8E23");
        hexCodes.add("6A5ACD");
        hexCodes.add("696969");
        hexCodes.add("66CDAA");
        hexCodes.add("6495ED");
        hexCodes.add("5F9EA0");
        hexCodes.add("556B2F");
        hexCodes.add("4B0082");
        hexCodes.add("48D1CC");
        hexCodes.add("483D8B");
        hexCodes.add("4682B4");
        hexCodes.add("4169E1");
        hexCodes.add("40E0D0");
        hexCodes.add("3CB371");
        hexCodes.add("32CD32");
        hexCodes.add("2F4F4F");
        hexCodes.add("2E8B57");
        hexCodes.add("228B22");
        hexCodes.add("20B2AA");
        hexCodes.add("1E90FF");
        hexCodes.add("191970");
        hexCodes.add("00FFFF");
        hexCodes.add("00FF7F");
        hexCodes.add("00FF00");
        hexCodes.add("00FA9A");
        hexCodes.add("00CED1");
        hexCodes.add("00BFFF");
        hexCodes.add("008B8B");
        hexCodes.add("008080");

        ArrayList<String> muscles = getMuscles();
        for (String muscle:
             muscles) {

            Log.d("Muscle->>>>>>", muscle);

        }
        databaseHelper.saveOrUpdate(hexCodes, muscles);
    }

    private void setupPagerAdapter(){

        viewPager = (ViewPager)findViewById(R.id.view_pager_main_activity);
        EndlessPagerAdapter adapter = new EndlessPagerAdapter(getSupportFragmentManager(), getApplicationContext());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(50000,false);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                DynamicFragment.hideFAB();
            }

            @Override
            public void onPageSelected(int position) {

                DynamicFragment.hideFAB();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

                //DynamicFragment.showFAB();
                switch (state){
                    case ViewPager.SCROLL_STATE_IDLE:
                        DynamicFragment.showFAB();
                        break;
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        DynamicFragment.hideFAB();
                        break;
                    case ViewPager.SCROLL_STATE_SETTLING:
                        DynamicFragment.hideFAB();
                        break;

                }
            }
        });

//        RecyclerTabLayout tabLayout = (RecyclerTabLayout) findViewById(R.id.recycler_tab_layout);
//        tabLayout.setUpWithViewPager(viewPager);
    }


    private void setupActionBar(){
        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);


        ActionBarDrawerToggle mDrawerToggle;
        Drawer = (DrawerLayout) findViewById(R.id.drawerLayout);        // Drawer object Assigned to the view
        mDrawerToggle = new ActionBarDrawerToggle(this,Drawer,toolbar,R.string.openDrawer,R.string.closeDrawer){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // code here will execute once the drawer is opened( As I dont want anything happened whe drawer is
                // open I am not going to put anything here)
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }
        }; // Drawer Toggle Object Made
        Drawer.addDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
        mDrawerToggle.syncState();               // Finally we set the drawer toggle sync State
    }
    private ArrayList<String> getMuscles(){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        SharedPreferences.Editor editor = preferences.edit();

        ArrayList<String> arralist = new ArrayList<>();

        if(preferences.contains(Constants.GROUPS)){

            arralist.addAll(preferences.getStringSet(Constants.GROUPS, new HashSet<String>()));

        }

        return  arralist;

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

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.calendarViewItem:

                Intent intentCalendar = new Intent(this, CaldroidCustomImplementation.class);
                startActivity(intentCalendar);
                break;
            case R.id.newExercise:

                DialogForAddingContent dialogForAddingContent = DialogForAddingContent.newInstance(Constants.GROUPS);

                dialogForAddingContent.show(getSupportFragmentManager(), "tag");

//                Intent intent = new Intent(this, InsertActivity.class);
//                startActivity(intent);
                break;


        }

        return true;
    }

}


