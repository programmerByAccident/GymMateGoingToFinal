package com.simpleideas.gymmate;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by George Ciopei on 2/15/2017.
 */

public class GenericDecorator implements DayViewDecorator {

    private ArrayList<CalendarViewMapDaysWithWorkouts> dayMapping;
    private CalendarViewMapDaysWithWorkouts dayMap;
    private Context context;
    private int width, height;

    private int color;

    public GenericDecorator(Context context,ArrayList<CalendarViewMapDaysWithWorkouts> dayMapping,CalendarViewMapDaysWithWorkouts calendarViewMapDaysWithWorkouts, int color, int width, int height){
        this.context = context;
        this.dayMapping = dayMapping;
        this.color = color;
        this.width = width;
        this.height = height;
        this.dayMap = calendarViewMapDaysWithWorkouts;
    }

    public GenericDecorator(Context context, CalendarViewMapDaysWithWorkouts dayMap, int width, int height){
        this.context = context;
        this.dayMap = dayMap;
        this.width = width;
        this.height = height;
    }

    public GenericDecorator(Context context, ArrayList<CalendarViewMapDaysWithWorkouts> dayMapping, int width, int height){
        this.context = context;
        this.dayMapping = dayMapping;
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {

        Boolean answer = false;
        Date date = day.getDate();
        DateFormat dateFormatTwo = new SimpleDateFormat("E - d - MMMM - yyyy", Locale.ENGLISH);

        String dateToCheck = dateFormatTwo.format(date);

        for (CalendarViewMapDaysWithWorkouts parser:
             dayMapping) {
            if(parser.getDate() == dateToCheck){
                answer = true;
            }
        }

        return answer;
    }

    @Override
    public void decorate(DayViewFacade view) {

        LayerDrawable drawable = (LayerDrawable) context.getDrawable(R.drawable.oval_shape);

        if (dayMap.getMuscles().isEmpty()){

            view.setBackgroundDrawable(drawable);

        }else {


            float shortenPercentage = width/dayMap.getMuscles().size();

            shortenPercentage = Math.abs(shortenPercentage);

            Drawable[] drawables = new Drawable[dayMap.getMuscles().size()];

            for (int index = 0; index < dayMap.getMuscles().size(); index++){

                GradientDrawable gradientDrawable = new GradientDrawable();
                gradientDrawable.setShape(GradientDrawable.OVAL);
                gradientDrawable.setSize(width, height);
                gradientDrawable.setColor(ContextCompat.getColor(context, color));


                gradientDrawable.setStroke(7, ContextCompat.getColor(context, color));

                width = (int) (width - shortenPercentage);
                height = (int) (height - shortenPercentage);

                drawables[index] = gradientDrawable;
            }
            LayerDrawable layerDrawable = new LayerDrawable(drawables);



            view.setBackgroundDrawable(layerDrawable);

        }





    }
}





























































