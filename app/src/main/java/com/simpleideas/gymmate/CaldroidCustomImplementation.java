package com.simpleideas.gymmate;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.ReadableInstant;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by George Ciopei on 2/17/2017.
 */

public class CaldroidCustomImplementation extends AppCompatActivity{

    DatabaseHelper databaseHelper;
    CaldroidFragment caldroidFragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.caldroid);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_for_whities);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        caldroidFragment = new CaldroidFragment();
        databaseHelper = new DatabaseHelper(CaldroidCustomImplementation.this);
        new BackgroundReadForCaldroid().execute();
    }

    private class BackgroundReadForCaldroid extends AsyncTask<Void, Void, HashMap<Date, ArrayList<Integer>>>{


        @Override
        protected void onPostExecute(HashMap<Date, ArrayList<Integer>> hashMaps) {
            super.onPostExecute(hashMaps);

            for (Map.Entry<Date, ArrayList<Integer>> inseration:hashMaps.entrySet()){
                caldroidFragment.setBackgroundDrawableForDate(new ShapeDrawable(new TestShapeClass(inseration.getValue())), inseration.getKey());
            }

            caldroidFragment.setCaldroidListener(new CaldroidListener() {
                @Override
                public void onCaldroidViewCreated() {
                    super.onCaldroidViewCreated();
                    caldroidFragment.getLeftArrowButton().setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_chevron_left));
                    caldroidFragment.getRightArrowButton().setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_chevron_right));
                }

                @Override
                public void onSelectDate(Date date, View view) {

                    DateFormat dateFormat = new SimpleDateFormat("E - d - MMMM - yyyy");

                    if (databaseHelper.getAllExercises(dateFormat.format(date)).size() == 0){

                        Toast.makeText(CaldroidCustomImplementation.this, "No records for this day!", Toast.LENGTH_SHORT).show();

                    }else{
                        CustomDayDialog.newInstance(dateFormat.format(date),getPositionOfSelectedCalendarDay(date)).show(getSupportFragmentManager(), "TAG");
                    }



                }
            });
            Bundle args = new Bundle();
            Calendar cal = Calendar.getInstance();
            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
            caldroidFragment.setArguments(args);
            caldroidFragment.refreshView();
            FragmentTransaction t = getSupportFragmentManager().beginTransaction();
            t.replace(R.id.caldroid_object, caldroidFragment);
            t.commit();

        }

        @Override
        protected HashMap<Date, ArrayList<Integer>> doInBackground(Void... voids) {

            ArrayList<HashMap<Date, ArrayList<Integer>>> arrayList = new ArrayList<>();
            HashMap<Date, ArrayList<Integer>> entryToInsert = new HashMap<>();
            for (Map.Entry<String, ArrayList<String>>mapInseration:
                 databaseHelper.mapDateWithMuscles().entrySet()) {

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E - d - MMMM - yyyy", Locale.ENGLISH);

                Date date = new Date();
                try {
                    date = simpleDateFormat.parse(mapInseration.getKey());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                ArrayList<Integer> colors = new ArrayList<>();
                for (int i=0;i<mapInseration.getValue().size(); i++){
                    Log.d("EachIterationValue", mapInseration.getValue().get(i));
                    colors.add(databaseHelper.selectColorBasedOnMuscle(mapInseration.getValue().get(i)));
                }
                entryToInsert.put(date,colors);
            }

            return entryToInsert;
        }
    }



    private int getPositionOfSelectedCalendarDay(Date targetDay){

        Date currentDate = new Date();

        return daysBetween(currentDate, targetDay);
    }

    public int daysBetween(Date d1, Date d2){
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }

}
