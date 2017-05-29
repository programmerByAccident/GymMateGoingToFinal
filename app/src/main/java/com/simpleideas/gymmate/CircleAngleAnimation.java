package com.simpleideas.gymmate;

import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by George on 21/05/2017.
 */

public class CircleAngleAnimation extends Animation {

    private Circle circle;

    private float oldAngle;
    private float newAngle;

    public CircleAngleAnimation(Circle circle, float oldAngle, float newAngle) {
        //this.oldAngle = circle.getAngle();
        this.oldAngle = oldAngle;
        this.newAngle = newAngle;
        this.circle = circle;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation transformation) {
        float angle = oldAngle - ((newAngle - oldAngle) * interpolatedTime);


        circle.setAngle(angle);
        circle.requestLayout();
    }



}