package com.simpleideas.gymmate;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Date;

/**
 * Created by George Ciopei on 1/4/2017.
 */

public class CurrentDayDecorator implements DayViewDecorator {

    Date currentDate;

    public CurrentDayDecorator(Date currentDate){

        this.currentDate = currentDate;

    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {

        return false;

    }

    @Override
    public void decorate(DayViewFacade view) {

    }
}
