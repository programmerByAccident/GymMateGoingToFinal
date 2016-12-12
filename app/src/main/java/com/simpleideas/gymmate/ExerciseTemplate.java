package com.simpleideas.gymmate;

/**
 * Created by Geprge on 12/2/2016.
 */

public class ExerciseTemplate {

    private String exerciseName;
    private int difference;
    private int repetition;
    private float weight;

    public ExerciseTemplate(String exerciseName, int difference, int repetition, float weight){

        this.exerciseName = exerciseName;
        this.difference = difference;
        this.repetition = repetition;
        this.weight = weight;

    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public int getDifference() {
        return difference;
    }

    public void setDifference(int difference) {
        this.difference = difference;
    }

    public int getRepetition() {
        return repetition;
    }

    public void setRepetition(int repetition) {
        this.repetition = repetition;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }



}
