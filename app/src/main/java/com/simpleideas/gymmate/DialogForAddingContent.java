package com.simpleideas.gymmate;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import adapters.ColorAdapter;

/**
 * Created by Geprge on 12/27/2016.
 */

public class DialogForAddingContent extends DialogFragment implements View.OnClickListener{

    private ImageButton addCategories;
    private String preferences;
    private TextView colorOption;
    private EditText newCategory, newExercise;
    private Spinner chooseCategory;
    private SpinnerAdapter spinnerAdapter;
    private ColorAdapter colorAdapter;
    private Spinner colorPick;
    private View globalView, customSpinnerWidth;
    private Button insert_category, replaceAgain, getText;
    private ImageButton addNewCategory;
    LinearLayout manipulate, innerLayout;
    private DatabaseHelper databaseHelper;

    String toShow = "Something";

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
        globalView = view;
        databaseHelper = new DatabaseHelper(getContext());
        manipulate = (LinearLayout) view.findViewById(R.id.manipulate);
        innerLayout = (LinearLayout) view.findViewById(R.id.innerLayout);
        newCategory = (EditText) view.findViewById(R.id.muscle_group);
        chooseCategory = (Spinner) view.findViewById(R.id.categorySpinner);
        colorPick = (Spinner)view.findViewById(R.id.colorSpinner);
        colorOption = (TextView)view.findViewById(R.id.colorOption);
        insert_category = (Button) view.findViewById(R.id.insert_new_category_button);
        addNewCategory = (ImageButton) view.findViewById(R.id.addNewCategory);
        addNewCategory.setOnClickListener(this);
        insert_category.setOnClickListener(this);

        ArrayList<String> elementsForSpinner = getInformation(Constants.GROUPS);
        ArrayList<String> colorElements = databaseHelper.getAllHexCodes();
        elementsForSpinner.add(0, "");
        spinnerAdapter = new SpinnerAdapter(getContext(),elementsForSpinner);
        colorAdapter = new ColorAdapter(getContext(), "color", colorElements);
        colorPick.setAdapter(colorAdapter);
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
            case R.id.insert_new_category_button:
                View viewToManage = getView().findViewById(R.id.categorySpinner);

                if(viewToManage instanceof Spinner){
                    String checkExistance = ((Spinner) viewToManage).getSelectedItem().toString();
                    if(checkExistance==""){
                        Toast.makeText(getContext(), "Choose category", Toast.LENGTH_SHORT).show();
                    }else {
                        addExerciseToCategory(((Spinner) viewToManage).getSelectedItem().toString(), newCategory.getText().toString());
                    }
                }
                else if(viewToManage instanceof EditText){
                    String checkExistance = ((EditText) viewToManage).getText().toString();
                    if(checkExistance==""){
                        Toast.makeText(getContext(), "Insert category", Toast.LENGTH_SHORT).show();
                    }else{
                        addExerciseToCategory(((EditText) viewToManage).getText().toString(), newCategory.getText().toString());
                        databaseHelper.insertColorBasedOnPositionPick(colorPick.getSelectedItem().toString(), checkExistance);
                    }
                }
                break;

            case R.id.addNewCategory:

                View genericView = getView().findViewById(R.id.categorySpinner);

                if(genericView instanceof Spinner){
                    replaceView(innerLayout, chooseCategory);
                    colorPick.setVisibility(View.VISIBLE);
                    colorOption.setVisibility(View.VISIBLE);
                    addNewCategory.setImageResource(R.drawable.clear_custom);
                    //addNewCategory.setBackgroundResource(R.drawable.clear_custom);
                }
                else
                if(genericView instanceof EditText){
                    replaceBack(innerLayout, chooseCategory);
                    colorPick.setVisibility(View.INVISIBLE);
                    colorOption.setVisibility(View.INVISIBLE);
                    //addNewCategory.setBackgroundResource(R.drawable.ic_launcher);
                    addNewCategory.setImageResource(R.drawable.ic_launcher);
                }
                break;
        }
    }



    private void addExerciseToCategory(String category, String exercise){

        SharedPreferences preferences = getActivity().getApplicationContext().getSharedPreferences(category, getActivity().getApplicationContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        if(preferences.contains(category)){

            Set<String> exercises = preferences.getStringSet(category, null);

            if(exercises.contains(exercise)){
                Toast.makeText(getActivity().getApplicationContext(), "Exercise already exists", Toast.LENGTH_SHORT).show();
            }
            else{
                exercises.add(exercise);
                editor.putStringSet(category, exercises);
                editor.clear();
                editor.apply();
            }
        }
        else{
            SharedPreferences defaultPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
            SharedPreferences.Editor defaultEditor = defaultPreferences.edit();
            Set<String> groups = defaultPreferences.getStringSet(Constants.GROUPS, null);
            if(groups.contains(category) == false){

                groups.add(category);
                for (String muscle:
                     groups) {

                    Log.d("MARINAEEE", muscle);

                }
                defaultEditor.putStringSet(Constants.GROUPS, groups);
                defaultEditor.clear();
                defaultEditor.apply();

                SharedPreferences customPreferences = getActivity().getApplicationContext().getSharedPreferences(category, getActivity().getApplicationContext().MODE_PRIVATE);
                SharedPreferences.Editor customEditor = customPreferences.edit();

                Set<String> stringSet = new HashSet<>();
                stringSet.add(exercise);
                customEditor.putStringSet(category, stringSet);
                customEditor.clear();
                customEditor.apply();
            }
        }
    }

    private void replaceBack(LinearLayout linearLayout, View viewToInsert){
        linearLayout.removeViewAt(0);
        //linearLayout.removeView(viewToInsert);
        linearLayout.addView(viewToInsert, 0);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void replaceView(LinearLayout linearLayout, View viewToReplace){
        int width = viewToReplace.getWidth();

        EditText editText = new EditText(getActivity().getApplicationContext());
        editText.setTextColor(Color.BLACK);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            editText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }
        
        editText.setWidth(width);
        editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        editText.setId(viewToReplace.getId());
        editText.setBackgroundResource(R.drawable.apptheme_textfield_activated_holo_light);

        linearLayout.removeViewAt(0);
        linearLayout.removeView(viewToReplace);
        linearLayout.addView(editText, 0);
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
