package com.simpleideas.gymmate;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Geprge on 12/5/2016.
 */

public class MuscleGroupsAdapter extends RecyclerView.Adapter<MuscleGroupsAdapter.MuscleViewHolder>{

    public ArrayList<String> getMuscleGroups() {
        return muscleGroups;
    }

    public void setMuscleGroups(ArrayList<String> muscleGroups) {
        this.muscleGroups = muscleGroups;
    }

    private ArrayList<String> muscleGroups;
    private Context context;
    private String date;
    private int difference;

    LayoutInflater layoutInflater;

    public MuscleGroupsAdapter(ArrayList<String> muscleGroups, Context context, String date){

        this.context = context;
        this.muscleGroups = muscleGroups;
        this.date = date;
        this.difference = difference;


//    }
//
//
//    @Override
//    public int getCount() {
//        return muscleGroups.size();
//    }
//
//    @Override
//    public Object getItem(int i) {
//
//        return muscleGroups.get(i);
//
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return 0;
//    }
//
//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//
//        View eachPositionView = view;
//
//        if(eachPositionView == null){
//
//            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//            eachPositionView = layoutInflater.inflate(R.layout.muscle_item, null);
//
//        }
//
//        TextView textView = (TextView) eachPositionView.findViewById(R.id.muscleItem);
//
//        textView.setText(muscleGroups.get(i));
//
//
//
//
//        return eachPositionView;

    }

    @Override
    public MuscleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.muscle_item, parent,false);

        MuscleViewHolder muscleViewHolder = new MuscleViewHolder(view);

        return muscleViewHolder;
    }

    @Override
    public void onBindViewHolder(MuscleViewHolder holder, int position) {

        holder.textView.setText(muscleGroups.get(position));

    }

    @Override
    public int getItemCount() {
        return this.muscleGroups.size();
    }

    class MuscleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textView;
        public MuscleViewHolder(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.muscleItem);
            textView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch(view.getId()){

                case R.id.muscleItem:
                    Intent intent = new Intent(context, CertainMuscleListView.class);

                    intent.putExtra(Constants.MUSCLE_NAME, textView.getText().toString());
                    intent.putExtra("Difference", difference);
                    intent.putExtra("date", date);
                    context.startActivity(intent);


            }
        }
    }
}
