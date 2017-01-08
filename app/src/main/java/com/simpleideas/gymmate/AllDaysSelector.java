package com.simpleideas.gymmate;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

/**
 * Created by Geprge on 1/6/2017.
 */

public class AllDaysSelector implements DayViewDecorator {

    Context context;

    public AllDaysSelector(Context context){

        this.context= context;

    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return true;
    }

    @Override
    public void decorate(DayViewFacade view) {

        Drawable circle = ContextCompat.getDrawable(context,R.drawable.circle);
        GradientDrawable gradientDrawable = (GradientDrawable) circle;

        gradientDrawable.setColor(Color.LTGRAY);

        view.setSelectionDrawable(gradientDrawable);
        gradientDrawable.setColor(Color.GREEN);
        view.setBackgroundDrawable(gradientDrawable);

    }
}
