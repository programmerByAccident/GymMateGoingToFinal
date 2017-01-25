package com.simpleideas.gymmate;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Geprge on 12/27/2016.
 */

public class DialogForAddingContent extends DialogFragment implements View.OnClickListener{

    private ImageButton addCategories;
    private String preferences;
    private EditText newCategory;
    private Spinner chooseCategory;
    private SpinnerAdapter spinnerAdapter;

    public static DialogForAddingContent newInstance(String sharedPreferencesType){

        DialogForAddingContent dialogForAddingContent = new DialogForAddingContent();

        Bundle arguments = new Bundle();

        arguments.putString(Constants.SHARED_PREFERENCES, sharedPreferencesType);

        dialogForAddingContent.setArguments(arguments);

        return dialogForAddingContent;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.dialog_fragment, container, false);

        Toolbar toolbar = (Toolbar)view.findViewById(R.id.dinamic);

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        //addCategories = (ImageButton) view.findViewById(R.id.insert_muscle_group);
        newCategory = (EditText) view.findViewById(R.id.muscle_group);
        chooseCategory = (Spinner) view.findViewById(R.id.categorySpinner);
        ArrayList<String> elementsForSpinner = getInformation(Constants.GROUPS);
        elementsForSpinner.add(0, "");
        spinnerAdapter = new SpinnerAdapter(getContext(),elementsForSpinner);
        //ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(),R.layout.spinner_item, elementsForSpinner);
        chooseCategory.setAdapter(spinnerAdapter);
        //addCategories.setOnClickListener(this);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = getArguments().getString(Constants.SHARED_PREFERENCES);

        int style = DialogFragment.STYLE_NORMAL;

        setStyle(style, R.style.AppTheme);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

//            case R.id.insert_muscle_group:
//
//                modifySharedPreferenes(newCategory.getText().toString());
//
//                break;


        }
    }


    private void modifySharedPreferenes(String category){

        ArrayList<String> sharedInformation = new ArrayList();

        sharedInformation = getInformation(preferences);

        sharedInformation.add(category);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        SharedPreferences.Editor editor = preferences.edit();

        String[] muscles = new String[sharedInformation.size()];

        muscles = sharedInformation.toArray(muscles);

        Set<String> muscleSet = new HashSet<>(Arrays.asList(muscles));

        editor.putStringSet(Constants.GROUPS, muscleSet);

        editor.apply();

        SharedPreferences newCategory = getActivity().getApplicationContext().getSharedPreferences(category, getActivity().getApplicationContext().MODE_PRIVATE);

        SharedPreferences.Editor newCategoryEditor = newCategory.edit();

        String[] categoryArray = new String[0];

        Set<String> stringSet = new HashSet<String>(Arrays.asList(categoryArray));

        newCategoryEditor.putStringSet(category, stringSet);

        newCategoryEditor.apply();

    }

    private ArrayList<String> getInformation(String sharedPreference){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        SharedPreferences.Editor editor = preferences.edit();

        ArrayList<String> arraylist = new ArrayList<>();

        if(preferences.contains(sharedPreference)){

            arraylist.addAll(preferences.getStringSet(sharedPreference, new HashSet<String>()));

        }

        return  arraylist;

    }
}
