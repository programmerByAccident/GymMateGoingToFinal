package com.simpleideas.gymmate;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.shapes.Shape;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;


/**
 * Created by Geprge on 3/6/2017.
 */

    public class TestShapeClass extends Shape {

        private static final float WIDTH = 12f;
        private ArrayList<Integer> colors;

        public TestShapeClass(ArrayList<Integer> colors){
            this.colors = colors;
        }

        @Override
        public void draw(Canvas canvas, Paint paint) {

            float cx = getWidth() / 2;
            float cy = getHeight() / 2;
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(10);
            float radius = Math.min(cx, cy) - WIDTH/2;

            for(int color:colors){
                paint.setColor(color);
                canvas.drawCircle(cx,cy,radius,paint);
                radius-=WIDTH;
            }

        }
    }



