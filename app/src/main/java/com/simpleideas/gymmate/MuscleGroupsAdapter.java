package com.simpleideas.gymmate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Geprge on 12/5/2016.
 */

public class MuscleGroupsAdapter extends BaseAdapter {

    private ArrayList<String> muscleGroups;
    private Context context;

    public MuscleGroupsAdapter(ArrayList<String> muscleGroups, Context context){

        this.context = context;
        this.muscleGroups = muscleGroups;


    }


    @Override
    public int getCount() {
        return muscleGroups.size();
    }

    @Override
    public Object getItem(int i) {

        return muscleGroups.get(i);

    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View eachPositionView = view;

        if(eachPositionView == null){

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            eachPositionView = layoutInflater.inflate(R.layout.muscle_item, null);

        }

        TextView textView = (TextView) eachPositionView.findViewById(R.id.muscleItem);

        textView.setText(muscleGroups.get(i));




        return eachPositionView;

    }
}
