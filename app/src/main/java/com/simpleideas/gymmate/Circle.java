package com.simpleideas.gymmate;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by George on 20/05/2017.
 */

public class Circle extends View {

    //private static final int START_ANGLE_POINT = 90;

    private final Paint paint;

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    private int width, height;
    private final RectF rect;

    private float angle;

    public float getStart_angle() {
        return start_angle;
    }

    public void setStart_angle(float start_angle) {
        this.start_angle = start_angle;
    }

    private float start_angle;

    public Circle(Context context, AttributeSet attrs) {
        super(context, attrs);
        final int strokeWidth = 5;

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        //Circle color
        paint.setColor(Color.RED);

        //size 200x200 example
        rect = new RectF(strokeWidth, strokeWidth, 400 + strokeWidth, 400 + strokeWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(rect, 270, angle, false, paint);
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }
}
