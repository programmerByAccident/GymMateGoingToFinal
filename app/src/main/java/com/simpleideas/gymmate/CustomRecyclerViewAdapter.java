package com.simpleideas.gymmate;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Geprge on 12/18/2016.
 */

public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<CustomRecyclerViewAdapter.CustomViewHolder>{
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder{


        public CustomViewHolder(View itemView) {
            super(itemView);
        }
    }
}
