package com.simpleideas.gymmate;

import java.util.ArrayList;

/**
 * Created by Geprge on 1/14/2017.
 */

public class ExerciseTagInformation {

    private ArrayList<String> weight;
    private ArrayList<String> repetition;
    private String TAG;

    public ArrayList<String> getRepetition() {
        return repetition;
    }

    public void setRepetition(ArrayList<String> repetition) {
        this.repetition = repetition;
    }

    public String getTAG() {
        return TAG;
    }

    public void setTAG(String TAG) {
        this.TAG = TAG;
    }

    public ArrayList<String> getWeight() {
        return weight;
    }

    public void setWeight(ArrayList<String> weight) {
        this.weight = weight;
    }

    public ExerciseTagInformation(){}


}
