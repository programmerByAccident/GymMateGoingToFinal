package com.simpleideas.gymmate;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Geprge on 12/18/2016.
 */

public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<CustomRecyclerViewAdapter.CustomViewHolder>{

    private List<ExerciseTemplate> exercises = Collections.emptyList();
    private LayoutInflater inflater;
    public CustomRecyclerViewAdapter(Context context, ArrayList<ExerciseTemplate> exercises){

        this.exercises = exercises;


    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent,false);

        CustomViewHolder customViewHolder = new CustomViewHolder(view);

        return customViewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {

        String one = exercises.get(position).getExerciseName();
        String two = String.valueOf(exercises.get(position).getWeight());
        String three = String.valueOf(exercises.get(position).getRepetition());
        holder.exercicseName.setText(one.toString());
        holder.repetitions.setText(two);
        holder.weight.setText(three);

    }

    @Override
    public int getItemCount() {
        return this.exercises.size();
    }




    class CustomViewHolder extends RecyclerView.ViewHolder{


        private TextView exercicseName;
        private TextView weight;
        private TextView repetitions;

        public CustomViewHolder(View itemView) {
            super(itemView);

            exercicseName = (TextView) itemView.findViewById(R.id.exerciseNameViewWTF);
            repetitions = (TextView) itemView.findViewById(R.id.exerciseNameViewWTFReps);
            weight = (TextView) itemView.findViewById(R.id.exerciseNameViewWTFWeights);



        }
    }
}
