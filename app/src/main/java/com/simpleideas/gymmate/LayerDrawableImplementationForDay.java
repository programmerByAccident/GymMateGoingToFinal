package com.simpleideas.gymmate;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Geprge on 2/11/2017.
 */

public class LayerDrawableImplementationForDay {


    private ArrayList<ExerciseTagInformation> muscleGroups;
    private Context context;
    public LayerDrawableImplementationForDay(Context context, ArrayList<ExerciseTagInformation> muscleGroups){

        this.muscleGroups = muscleGroups;
        this.context = context;
    }

    public Drawable getCircleBasedOnMuscleGroups(int tileWidth, int tileHeight){
        Toast.makeText(context, "Width ->" + String.valueOf(tileWidth) + "Height -> " + String.valueOf(tileHeight), Toast.LENGTH_SHORT).show();
        Drawable[] listOfCircles = new Drawable[muscleGroups.size()];
        int w = tileWidth;
        int h = tileHeight;
        int dynamic = 0;

        int bound = 20;

        int percentage = (int) tileWidth / muscleGroups.size();
        int value = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                5, context.getResources().getDisplayMetrics());
        for (ExerciseTagInformation exercise:muscleGroups){
            GradientDrawable gradientDrawable = new GradientDrawable();

            OvalShape oval = new OvalShape();
            gradientDrawable.setShape(GradientDrawable.OVAL);
            gradientDrawable.setColor(R.color.colorPrimary);

            gradientDrawable.setStroke(value, Color.WHITE);

            listOfCircles[dynamic] = gradientDrawable;
            dynamic++;
        }

        LayerDrawable circle = new LayerDrawable(listOfCircles);
        int width = tileWidth;
        int part = tileWidth / listOfCircles.length;
        for(int index = 0; index<listOfCircles.length;index++){
            int widthinner = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, context.getResources().getDisplayMetrics());
            circle.setLayerInset(index, width,width,width,width);
            width-=part;

        }

//        for(int index = 0; index<listOfCircles.length; index++){
//
//
//            circle.setLayerInset(index, bound,bound,bound,bound);
//            w-=percentage;
//            bound+=bound;
//            if(index+1 == listOfCircles.length){
//                circle.setLayerInset(index, 0,0,0,0);
//                break;
//            }
//
//        }



        return  circle;

    }


}
