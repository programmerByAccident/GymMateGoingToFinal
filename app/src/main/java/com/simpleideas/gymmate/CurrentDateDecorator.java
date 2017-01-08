package com.simpleideas.gymmate;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import org.joda.time.DateTime;

import java.util.Date;

/**
 * Created by Geprge on 1/8/2017.
 */

public class CurrentDateDecorator implements DayViewDecorator {


    Context context;

    public CurrentDateDecorator(Context context){

        this.context = context;

    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {

        Boolean trueDate = false;

        DateTime dateTime = new DateTime();

        dateTime.getDayOfYear();

        Date newDate = dateTime.toDate();

        CalendarDay calendarDay = new CalendarDay(newDate);

        if(calendarDay == day){

            trueDate = true;

        }

        return trueDate;
    }

    @Override
    public void decorate(DayViewFacade view) {

        Drawable circle = ContextCompat.getDrawable(context,R.drawable.circle);
        GradientDrawable gradientDrawable = (GradientDrawable) circle;

        gradientDrawable.setColor(Color.BLUE);

        view.setBackgroundDrawable(gradientDrawable);

    }
}
