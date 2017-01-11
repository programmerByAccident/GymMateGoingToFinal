package com.simpleideas.gymmate;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
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

    private List<ExerciseTemplate> exercises = Collections.emptyList();
    private LayoutInflater inflater;
    DatabaseHelper databaseHelper;
    private String dateString;

    public CustomRecyclerViewAdapter(Context context, ArrayList<ExerciseTemplate> exercises, String dateString){

        this.exercises = exercises;
        this.dateString = dateString;
        databaseHelper = new DatabaseHelper(context);

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
        holder.exercicseName.setText(String.valueOf(exercises.size()));
        holder.repetitions.setText(exercises.size() + " reps" + "\n" + exercises.size() + " reps" + "\n"+exercises.size() + " reps");
        holder.weight.setText(exercises.size() +" weight" +"\n" + exercises.size() +" weight" + "\n" + exercises.size() +" weight");

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

        public CustomViewHolder(View itemView) {
            super(itemView);

            exercicseName = (TextView) itemView.findViewById(R.id.exerciseNameViewWTF);
            repetitions = (TextView) itemView.findViewById(R.id.exerciseNameViewWTFReps);
            weight = (TextView) itemView.findViewById(R.id.exerciseNameViewWTFWeights);

            imageButton = (ImageButton) itemView.findViewById(R.id.deleteButton);
            imageButton.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            switch(view.getId()){

                case R.id.deleteButton:
                    exercises.remove(getAdapterPosition());
                    //databaseHelper.deleteRecord(dateString, exercicseName.getText().toString(), repetitions.getText().toString(), weight.getText().toString());
                    notifyItemRemoved(getAdapterPosition());

            }
        }
    }
}
