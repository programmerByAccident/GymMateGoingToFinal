package com.simpleideas.gymmate;

import java.util.ArrayList;

/**
 * Created by George Ciopei on 2/15/2017.
 */

public class CalendarViewMapDaysWithWorkouts {

    private String date;
    private ArrayList<String> muscles;

    public CalendarViewMapDaysWithWorkouts(String date, ArrayList<String> muscles){

        this.date = date;
        this.muscles = muscles;

    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<String> getMuscles() {
        return muscles;
    }

    public void setMuscles(ArrayList<String> muscles) {
        this.muscles = muscles;
    }


}
