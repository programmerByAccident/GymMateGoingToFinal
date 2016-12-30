package com.simpleideas.gymmate;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Geprge on 11/7/2016.
 */

public class Exercise extends Fragment {


    String name;
    ArrayList<String> exercices;


    String stringNAme;
    private String exerciseName;

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }


    public static Exercise newInstance(String exerciseName){

        Exercise exercise = new Exercise();

        Bundle bundle = new Bundle();
        bundle.putString("lel", exerciseName);

        exercise.setArguments(bundle);

        return exercise;

    }

    public String getStringNAme() {
        return stringNAme;
    }

    public void setStringNAme(String stringNAme) {
        this.stringNAme = stringNAme;
    }

    public static  Exercise newInstance(int ID, String message){

        Exercise exercise = new Exercise();

        Bundle bundle = new Bundle();
        bundle.putInt("ID", ID);
        bundle.putString("Message", message);

        exercise.setArguments(bundle);

        return exercise;
    }

    public static List<Exercise> newInstance(int numberOfFragments){

        List<Exercise> exerciseList = new ArrayList<Exercise>();
        int id_counter = 100;
        for(int index = 0; index < numberOfFragments; index++){
            id_counter++;
            Exercise exerciseFragment = new Exercise();
            exerciseList.add(index, exerciseFragment);
        }

        
        return exerciseList;
    }

    public static Exercise newInstance(String name, ArrayList<String> someNames){

        Exercise exercise = new Exercise();

        Bundle bundle = new Bundle();
        bundle.putStringArrayList("names", someNames);
        bundle.putString("NAME", name);

        exercise.setArguments(bundle);

        return exercise;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        name = getArguments().getString("NAME");
        exercices = getArguments().getStringArrayList("names");
        stringNAme = getArguments().getString("lel");


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;

        view = inflater.inflate(R.layout.list_element, container, false);
        TextView textView = (TextView) view.findViewById(R.id.exerciseNameViewWTF);

        textView.setText(stringNAme);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

//    private void attachDataIntoView(View view){
//
//        TextView date = (TextView) view.findViewById(R.id.date);
//        ListView informationLi
//
//    }
//







































}
