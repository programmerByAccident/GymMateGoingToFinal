package com.simpleideas.gymmate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stacktips.view.CalendarListener;
import com.stacktips.view.CustomCalendarView;
import com.stacktips.view.DayDecorator;
import com.stacktips.view.DayView;


import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * Created by programmerByAccident on 9/26/2016.
 */
public class CalendarArea extends AppCompatActivity {

    private CustomCalendarView customCalendarView;
    private DayDecorator dayDecorator;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_calendar_view);
        customCalendarView = (CustomCalendarView) findViewById(R.id.customCalendarView);

        Calendar currentCalendar = Calendar.getInstance(Locale.getDefault());
        customCalendarView.setCalendarListener(new CalendarListener() {
            @Override
            public void onDateSelected(Date date) {



            }

            @Override
            public void onMonthChanged(Date date) {

            }
        });
    }

}
