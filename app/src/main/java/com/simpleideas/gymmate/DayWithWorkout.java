package com.simpleideas.gymmate;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.support.v4.content.ContextCompat;
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

public class DayWithWorkout implements DayViewDecorator {

    ArrayList<String> parseToDate;
    private int width, height;
    private Context context;

    public DayWithWorkout(Context context,ArrayList<String> parseToDate, int width, int height){

        this.parseToDate = parseToDate;
        this.width = width;
        this.context = context;
        this.height = height;
    }


    @Override
    public boolean shouldDecorate(CalendarDay day) {

        ArrayList<CalendarDay> calendarArrayListToReturnTrueOrFalse = new ArrayList<>();

        for (String element:
             parseToDate) {

            DateFormat dateFormat = new SimpleDateFormat("E - d - MMMM - yyyy", Locale.ENGLISH);

            try {
                Date date = dateFormat.parse(element);

                CalendarDay calendarDay = CalendarDay.from(date);

                calendarArrayListToReturnTrueOrFalse.add(calendarDay);

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        return calendarArrayListToReturnTrueOrFalse.contains(day);

    }

    @Override
    public void decorate(DayViewFacade view) {

        Drawable circle = ContextCompat.getDrawable(context,R.drawable.circle);
        GradientDrawable gradientDrawable = (GradientDrawable) circle;
        gradientDrawable.setColor(R.color.colorPrimary);
        view.setBackgroundDrawable(gradientDrawable);
    }
}
