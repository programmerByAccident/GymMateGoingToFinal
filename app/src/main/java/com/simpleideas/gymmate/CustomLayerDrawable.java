package com.simpleideas.gymmate;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.Shape;
import android.support.v4.content.ContextCompat;
import android.util.Log;

/**
 * Created by George Ciopei on 2/18/2017.
 */

public class CustomLayerDrawable {

    private int numberOfCircles;
    private Context context;
    public CustomLayerDrawable(int numberOfCircles, Context context){
        this.numberOfCircles = numberOfCircles;
        this.context = context;
    }

    public LayerDrawable getLayerDrawable(){

        Drawable[] drawables = new Drawable[numberOfCircles];
        float maximumValue = 100;

        float difference = maximumValue / numberOfCircles;
        float addition = difference;
        int[] colors = new int[numberOfCircles];

        colors[0] = ContextCompat.getColor(context, R.color.caldroid_holo_blue_dark);
        colors[1] = ContextCompat.getColor(context, R.color.caldroid_light_red);
        colors[2] = ContextCompat.getColor(context,R.color.caldroid_sky_blue);
        colors[3] = ContextCompat.getColor(context,R.color.textColorPrimary);



        for (int i=0; i<numberOfCircles; i++){

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
