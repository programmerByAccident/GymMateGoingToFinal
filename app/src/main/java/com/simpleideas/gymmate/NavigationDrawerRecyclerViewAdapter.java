package com.simpleideas.gymmate;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by George Ciopei on 1/8/2017.
 */

public class NavigationDrawerRecyclerViewAdapter extends RecyclerView.Adapter {

    String[] names;
    int[] imageResources;

    public NavigationDrawerRecyclerViewAdapter(String[] names, int[] imageResources){

        this.names = names;
        this.imageResources = imageResources;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class NavigationDrawerRecyclerViewHolder extends RecyclerView.ViewHolder{


        public NavigationDrawerRecyclerViewHolder(View itemView) {
            super(itemView);
        }
    }
}
