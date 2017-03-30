package com.simpleideas.gymmate;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by George Ciopei on 2/18/2017.
 */

public class CustomLayerDrawable {

    private ArrayList<String> muscleBasedOnDate;
    private Context context;
    public CustomLayerDrawable(ArrayList<String> muscleBasedOnDAte, Context context){
        this.muscleBasedOnDate = muscleBasedOnDAte;
        this.context = context;
    }

    public LayerDrawable getLayerDrawable(){

        Drawable[] drawables = new Drawable[muscleBasedOnDate.size()];
        float maximumValue = 100;

        float difference = maximumValue / muscleBasedOnDate.size();
        float addition = difference;
        int[] colors = new int[muscleBasedOnDate.size()];
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        for (int index = 0; index<muscleBasedOnDate.size(); index++){

            colors[index] = databaseHelper.selectColorBasedOnMuscle(muscleBasedOnDate.get(index));

        }



        for (int i = 0; i< muscleBasedOnDate.size(); i++){

            ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
            shapeDrawable.setIntrinsicHeight((int)difference);
            shapeDrawable.setIntrinsicWidth((int)difference);
            shapeDrawable.setBounds(new Rect(0,0,(int)difference,(int)difference));
            shapeDrawable.getPaint().setColor(colors[i]);
            shapeDrawable.setPadding(10,10,10,10);
            drawables[i] = shapeDrawable;
            difference = difference + addition;
        }

        for (Drawable inseration:
             drawables) {
            Log.d("Drawable", inseration.toString());
            Log.d("Size", inseration.getBounds().toString());
            Log.d("Color", String.valueOf(inseration.getLevel()));

        }
        LayerDrawable resultDrawable = new LayerDrawable(drawables);
        Log.d("LayerDrawable", String.valueOf(resultDrawable.getNumberOfLayers()));
        return resultDrawable;
    }
}
