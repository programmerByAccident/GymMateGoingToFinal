package com.simpleideas.gymmate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Geprge on 12/18/2016.
 */

public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<CustomRecyclerViewAdapter.CustomViewHolder>{

    private List<ExerciseTagInformation> exercises = Collections.emptyList();
    private LayoutInflater inflater;
    DatabaseHelper databaseHelper;
    private String dateString;
    Activity activity;

    public CustomRecyclerViewAdapter(Activity activity, ArrayList<ExerciseTagInformation> exercises, String dateString){

        this.exercises = exercises;
        this.dateString = dateString;
        databaseHelper = new DatabaseHelper(activity.getBaseContext());
        this.activity = activity;

    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent,false);

        CustomViewHolder customViewHolder = new CustomViewHolder(view);



        return customViewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {






//        String one = exercises.get(position).getExerciseName();
//        String two = String.valueOf(exercises.get(position).getWeight());
//        String three = String.valueOf(exercises.get(position).getRepetition());
//        SpannableString content = new SpannableString(one);
//        content.setSpan(new UnderlineSpan(), 0, one.length(), 0);
        holder.exercicseName.setText(exercises.get(position).getTAG());

        ArrayList<String> reps = exercises.get(position).getRepetition();
        ArrayList<String> weight = exercises.get(position).getWeight();
        StringBuilder stringBuilderReps = new StringBuilder();
        StringBuilder stringBuilderWeight = new StringBuilder();
        for(int index=0; index < reps.size(); index++){
            if(index <= 4) {
                stringBuilderReps.append(reps.get(index) + " reps" + "\n");
                stringBuilderWeight.append(weight.get(index)+ " kg" + "\n");
            }
            else{break;}


        }

        holder.repetitions.setText(stringBuilderReps);
        holder.weight.setText(stringBuilderWeight);

    }

    @Override
    public int getItemCount() {
        return this.exercises.size();
    }




    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        private TextView exercicseName;
        private TextView weight;
        private TextView repetitions;
        private ImageButton imageButton;
        private CardView cardView;

        public CustomViewHolder(View itemView) {
            super(itemView);

            exercicseName = (TextView) itemView.findViewById(R.id.exerciseNameViewWTF);
            repetitions = (TextView) itemView.findViewById(R.id.exerciseNameViewWTFReps);
            weight = (TextView) itemView.findViewById(R.id.exerciseNameViewWTFWeights);
            cardView = (CardView) itemView.findViewById(R.id.card);
            cardView.setOnClickListener(this);
            //imageButton = (ImageButton) itemView.findViewById(R.id.deleteButton);
            //imageButton.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            switch(view.getId()){
//                case R.id.card:
//
//                    Intent intent = new Intent(activity.getApplication(), InsertActivity.class);
//
//                    Bundle bundle = new Bundle();
//                    int position = getAdapterPosition();
//
//                    bundle.putString("");
//
//                    activity.startActivity(intent);

//                case R.id.deleteButton:
//                    exercises.remove(getAdapterPosition());
//                    //databaseHelper.deleteRecord(dateString, exercicseName.getText().toString(), repetitions.getText().toString(), weight.getText().toString());
//                    notifyItemRemoved(getAdapterPosition());

            }
        }
    }
}
