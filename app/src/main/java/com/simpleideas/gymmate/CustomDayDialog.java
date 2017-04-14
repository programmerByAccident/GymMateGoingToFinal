package com.simpleideas.gymmate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Geprge on 4/14/2017.
 */

public class CustomDayDialog extends DialogFragment {

    private RecyclerView dayData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view  = inflater.inflate(R.layout.dialog_box_for_metrics, container);

        dayData = (RecyclerView) view.findViewById(R.id.dayRecyclerView);




        return view;

    }
}
