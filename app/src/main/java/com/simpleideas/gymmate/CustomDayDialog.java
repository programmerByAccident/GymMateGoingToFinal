package com.simpleideas.gymmate;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import adapters.CustomDayInformationAdapter;

/**
 * Created by Geprge on 4/14/2017.
 */

public class CustomDayDialog extends DialogFragment implements View.OnClickListener{

    private RecyclerView dayData;
    private DatabaseHelper databaseHelper;
    private final static String DIFFERENCE_TAG = "days_difference_tag";
    private String dateToQueryDatabase;
    private int difference;
    public static CustomDayDialog newInstance(String dateToQueryDatabase, int differenceInDays){

        CustomDayDialog customDayDialog = new CustomDayDialog();
        Bundle arguments = new Bundle();
        arguments.putString("date", dateToQueryDatabase);
        arguments.putInt(DIFFERENCE_TAG, differenceInDays);
        customDayDialog.setArguments(arguments);


        return customDayDialog;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dateToQueryDatabase = getArguments().getString("date", "NothingReceived");
        difference = getArguments().getInt(DIFFERENCE_TAG);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.custom_dialog_fragment_caldroid, container,false);
        databaseHelper = new DatabaseHelper(getActivity().getBaseContext());
        dayData = (RecyclerView) view.findViewById(R.id.dayRecyclerView);
        Button goTo = (Button) view.findViewById(R.id.go_to);
        goTo.setOnClickListener(this);
        dayData.setAdapter(new CustomDayInformationAdapter(databaseHelper.getAllExercises(dateToQueryDatabase),getActivity().getBaseContext()));
        dayData.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));

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

    private void setBehaviourGoToButton(){

        startActivity(new Intent(getActivity(), StartActivity.class).putExtra(DIFFERENCE_TAG, difference));
//        Log.d("DynamicFragment","floating button actions");
//        AccesRemoteDatabase start_background_thread = new AccesRemoteDatabase();
//        start_background_thread.execute(Constants.PHP_SCRIPT_LOCAL_HOST);
//        Log.d("FBActions","after");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.go_to:

                setBehaviourGoToButton();
                //getActivity().finishAffinity();

        }
    }
}































