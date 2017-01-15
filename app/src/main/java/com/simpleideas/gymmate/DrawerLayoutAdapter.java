package com.simpleideas.gymmate;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Geprge on 1/15/2017.
 */

public class DrawerLayoutAdapter extends RecyclerView.Adapter<DrawerLayoutAdapter.DrawerViewHolder> {

    ArrayList<String> itemNames;
    //ArrayList<Integer> resourcesId;
    Context context;

    public DrawerLayoutAdapter(Context context, ArrayList<String> itemNames){
        this.context = context;
        this.itemNames = itemNames;
        //this.resourcesId = resourcesId;
    }

    @Override
    public DrawerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.muscle_item, parent,false);

        DrawerViewHolder drawerViewHolder = new DrawerViewHolder(view);

        return drawerViewHolder;
    }

    @Override
    public void onBindViewHolder(DrawerViewHolder holder, int position) {

        holder.textView.setText(itemNames.get(position));

    }


    @Override
    public int getItemCount() {
        return itemNames.size();
    }


    class DrawerViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public DrawerViewHolder(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.muscleItem);

        }


    }
}
