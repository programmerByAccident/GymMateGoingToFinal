package com.simpleideas.gymmate;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.ColorUtils;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by George Ciopei on 1/4/2017.
 */

public class DayWithWorkout  implements DayViewDecorator {

    ArrayList<String> parseToDate;
    private Drawable color;

    public DayWithWorkout(ArrayList<String> parseToDate){

        this.parseToDate = parseToDate;
        color = new ColorDrawable(Color.BLUE);

    }


    @Override
    public boolean shouldDecorate(CalendarDay day) {

        ArrayList<CalendarDay> calendarArrayListToReturnTrueOrFalse = new ArrayList<>();

        for (String element:
             parseToDate) {

            DateFormat dateFormat = new SimpleDateFormat("E - d - MMMM - yyyy", Locale.ENGLISH);

            try {
                Date date = dateFormat.parse(element);

                CalendarDay calendarDay = new CalendarDay(date);

                calendarArrayListToReturnTrueOrFalse.add(calendarDay);

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        return calendarArrayListToReturnTrueOrFalse.contains(day);

    }

    @Override
    public void decorate(DayViewFacade view) {

        view.setBackgroundDrawable(color);
    }
}
