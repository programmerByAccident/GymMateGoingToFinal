package com.simpleideas.gymmate;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Geprge on 4/14/2017.
 */

public class CustomDayDialog extends DialogFragment {

    private RecyclerView dayData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.custom_dialog_fragment_caldroid, container,false);

        //dayData = (RecyclerView) view.findViewById(R.id.dayRecyclerView);
        Button testView = (Button) view.findViewById(R.id.testView);

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = (int) ((int)displaymetrics.widthPixels * 0.9);
        int height = (int) ((int)displaymetrics.heightPixels * 0.9);


        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = width;
        params.height = height;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

        //getDialog().getWindow().setLayout(width,height);

    }


}
