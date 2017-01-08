package com.simpleideas.gymmate;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Geprge on 1/8/2017.
 */

public class DrawerAdapter extends RecyclerView.Adapter {

    ArrayList<String> itemNames;
    ArrayList<Bitmap> itemImages;

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

    public class DrawerHolder extends RecyclerView.ViewHolder{

        public DrawerHolder(View itemView) {
            super(itemView);
        }
    }
}
