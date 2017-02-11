package com.simpleideas.gymmate;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Geprge on 2/11/2017.
 */

public class LayerDrawableImplementationForDay {

    private ArrayList<ExerciseTagInformation> muscleGroups;

    public LayerDrawableImplementationForDay(ArrayList<ExerciseTagInformation> muscleGroups){

        this.muscleGroups = muscleGroups;

    }

    public Drawable getCircleBasedOnMuscleGroups(){

        Drawable[] listOfCircles = new Drawable[muscleGroups.size()];
        int dynamic = 0;

        for (ExerciseTagInformation exercise:muscleGroups){

            ShapeDrawable ovalShape = new ShapeDrawable();
            OvalShape oval = new OvalShape();
            ovalShape.setShape(oval);
            ovalShape.setColorFilter(R.color.colorAccent, PorterDuff.Mode.DARKEN);
            listOfCircles[dynamic] = ovalShape;
            dynamic++;
        }

        LayerDrawable circle = new LayerDrawable(listOfCircles);

        for(int index = 0; index < muscleGroups.size();index++){

            circle.setLayerInset(index, 2, 2,2,2);

        }


        return  circle;

    }


}
