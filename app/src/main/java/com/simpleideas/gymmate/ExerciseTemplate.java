package com.simpleideas.gymmate;

/**
 * Created by Geprge on 12/2/2016.
 */

public class ExerciseTemplate {

    private String exerciseName;
    private String dateOfExercise;
    private int repetition;
    private float weight;

    public String getMuscleName() {
        return muscleName;
    }

    public void setMuscleName(String muscleName) {
        this.muscleName = muscleName;
    }

    private String muscleName;

    public ExerciseTemplate(String muscleName, String exerciseName, String dateOfExercise, int repetition, float weight){

        this.exerciseName = exerciseName;
        this.dateOfExercise = dateOfExercise;
        this.repetition = repetition;
        this.weight = weight;
        this.muscleName = muscleName;

    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getDifference() {
        return dateOfExercise;
    }

    public void setDifference(int difference) {
        this.dateOfExercise = dateOfExercise;
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
