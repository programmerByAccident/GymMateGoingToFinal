package com.simpleideas.gymmate;

import android.content.Context;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by George Ciopei on 1/4/2017.
 */

public class DayWithWorkout implements DayViewDecorator {

    ArrayList<String> parseToDate;
    private int width, height;
    private Context context;

    ArrayList<String> muscleGroups = new ArrayList<>();

    public DayWithWorkout(Context context,ArrayList<String> parseToDate, int width, int height){

        this.parseToDate = parseToDate;
        this.width = width;
        this.context = context;
        this.height = height;

    }


    @Override
    public boolean shouldDecorate(CalendarDay day) {

        ArrayList<CalendarDay> calendarArrayListToReturnTrueOrFalse = new ArrayList<>();

        Date dateExistance = day.getDate();

        DateFormat dateFormatTwo = new SimpleDateFormat("E - d - MMMM - yyyy", Locale.ENGLISH);

        String interogation = dateFormatTwo.format(dateExistance);
        Log.d("Interogation", interogation);
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        muscleGroups = databaseHelper.getExerciseName(interogation);
       //Toast.makeText(context, "SizeOfArrayList " + interogation, Toast.LENGTH_SHORT).show();
        Log.d("Muscle Groups", muscleGroups.toString());

        for (String element:
             parseToDate) {

            DateFormat dateFormat = new SimpleDateFormat("E - d - MMMM - yyyy", Locale.ENGLISH);

            try {
                Date date = dateFormat.parse(element);

                CalendarDay calendarDay = CalendarDay.from(date);

                calendarArrayListToReturnTrueOrFalse.add(calendarDay);

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        return calendarArrayListToReturnTrueOrFalse.contains(day);

    }

    @Override
    public void decorate(DayViewFacade view) {

        LayerDrawable layerDrawable = (LayerDrawable)
                ContextCompat.getDrawable(context,R.drawable.oval_shape);

//        GradientDrawable gradientDrawabletop = (GradientDrawable) layerDrawable
//                .findDrawableByLayerId(R.id.topLayer);
//        GradientDrawable gradientDrawablesecond = (GradientDrawable) layerDrawable
//                .findDrawableByLayerId(R.id.secondLayer);
//        GradientDrawable gradientDrawablethird = (GradientDrawable) layerDrawable
//                .findDrawableByLayerId(R.id.thirdLayer);
////        GradientDrawable gradientDrawableforth = (GradientDrawable) layerDrawable
////                .findDrawableByLayerId(R.id.forthLayer);
////        GradientDrawable gradientDrawablefifth = (GradientDrawable) layerDrawable
////                .findDrawableByLayerId(R.id.fifthLayer);
//
//        ArrayList<GradientDrawable> myLayersList = new ArrayList<>();
//        Toast.makeText(context, "MuscleGroupsSize " + String.valueOf(muscleGroups.size()), Toast.LENGTH_SHORT).show();
//        myLayersList.add(gradientDrawabletop);
//        myLayersList.add(gradientDrawablesecond);
//        myLayersList.add(gradientDrawablethird);
//       // myLayersList.add(gradientDrawableforth);
//        //myLayersList.add(gradientDrawablefifth);
//
//        if(myLayersList.size() != muscleGroups.size()){
//            int difference = myLayersList.size() - muscleGroups.size();
//
//            difference = Math.abs(difference);
//
//            for (int index = 0; index < difference; index++){
//                myLayersList.get(index).setStroke(7, R.color.colorPrimary);
//            }
//        }
//        else{
//            for (int index = 0; index < muscleGroups.size(); index++){
//                myLayersList.get(index).setStroke(7, R.color.colorPrimary);
//            }
//        }




        view.setBackgroundDrawable(layerDrawable);

        //ShapeDrawable circle = new ShapeDrawable(  new OvalShape() );
//        Drawable circle = ContextCompat.getDrawable(context,R.drawable.oval_shape);
//
////        view.setBackgroundDrawable(circle);
//
//        LayerDrawable drawableLayered = (LayerDrawable) circle;
//
//        for(int index = 0; index< 5; index++){
//            drawableLayered.getDrawable(index).setColorFilter(R.color.colorPrimary, PorterDuff.Mode.MULTIPLY);
//        }
//        view.setBackgroundDrawable(drawableLayered);
//        ArrayList<ExerciseTagInformation> exerciseTagInformations = new ArrayList<>();
//        for(int index=0; index<3;index++){
//            ExerciseTagInformation exerciseTagInformation = new ExerciseTagInformation();
//            exerciseTagInformations.add(exerciseTagInformation);
//        }


//        LayerDrawableImplementationForDay layerDrawableImplementationForDay = new LayerDrawableImplementationForDay(context,exerciseTagInformations);
//
//        view.setBackgroundDrawable(layerDrawableImplementationForDay.getCircleBasedOnMuscleGroups(width, height));

//        GradientDrawable gradientDrawable = (GradientDrawable) circle;
//        int colorPrimary = context.getResources().getColor(R.color.colorPrimary);
//        gradientDrawable.setColor(colorPrimary);
//        //gradientDrawable.setColor(R.color.colorPrimary);
//        view.setBackgroundDrawable(gradientDrawable);
    }
}
