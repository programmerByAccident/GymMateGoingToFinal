package com.simpleideas.gymmate;

/**
 * Created by Geprge on 12/2/2016.
 */

public class ExerciseTemplate {


    private String exerciseName;
    private String[] weights;
    private String[] repetitions;



    public ExerciseTemplate(String Name, String[] weights, String[] reps){

        this.exerciseName = Name;
        this.weights = weights;
        this.repetitions = reps;

    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String[] getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(String[] repetitions) {
        this.repetitions = repetitions;
    }

    public String[] getWeights() {
        return weights;
    }

    public void setWeights(String[] weights) {
        this.weights = weights;
    }

}
