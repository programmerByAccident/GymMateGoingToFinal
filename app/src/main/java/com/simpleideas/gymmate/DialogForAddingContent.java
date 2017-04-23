package com.simpleideas.gymmate;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jrummyapps.android.colorpicker.ColorPickerDialog;
import com.jrummyapps.android.colorpicker.ColorPickerDialogListener;
import com.jrummyapps.android.colorpicker.ColorPickerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import adapters.ColorAdapter;

/**
 * Created by Geprge on 12/27/2016.
 */

public class DialogForAddingContent extends AppCompatActivity implements View.OnClickListener, ColorPickerDialogListener{

    private ImageButton addCategories, color_picker;
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

    private String COLOR_POPULATED = "";

    private final int DIALOG_ID = 0;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_fragment);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_for_whities);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        databaseHelper = new DatabaseHelper(this);
        manipulate = (LinearLayout) findViewById(R.id.manipulate);
        innerLayout = (LinearLayout) findViewById(R.id.innerLayout);
        newCategory = (EditText) findViewById(R.id.muscle_group);
        chooseCategory = (Spinner) findViewById(R.id.categorySpinner);
        //colorPick = (Spinner) findViewById(R.id.colorSpinner);
        //colorOption = (TextView) findViewById(R.id.colorOption);
        insert_category = (Button) findViewById(R.id.insert_new_category_button);
        addNewCategory = (ImageButton) findViewById(R.id.addNewCategory);
        color_picker = (ImageButton) findViewById(R.id.color_flicker);
        addNewCategory.setOnClickListener(this);
        insert_category.setOnClickListener(this);
        color_picker.setOnClickListener(this);

        ArrayList<String> elementsForSpinner = getInformation(Constants.GROUPS);
        ArrayList<String> colorElements = databaseHelper.getAllHexCodes();
        elementsForSpinner.add(0, "");
        Collections.sort(elementsForSpinner, new Comparator<String>() {
            @Override
            public int compare(String s, String t1) {
                return s.compareTo(t1);
            }
        });
        spinnerAdapter = new SpinnerAdapter(this,elementsForSpinner);
        colorAdapter = new ColorAdapter(this, "color", colorElements);
        //colorPick.setAdapter(colorAdapter);
        //ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(),R.layout.spinner_item, elementsForSpinner);
        chooseCategory.setAdapter(spinnerAdapter);
        //manageColorPickerDialog();
        //preferences = getArguments().getString(Constants.SHARED_PREFERENCES);
        //preferences = getIntent().getExtras().get()
        int style = DialogFragment.STYLE_NORMAL;
        //setStyle(style, R.style.AppTheme);
        //manageColorPickerDialog();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_dialog_for_adding_content, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.insert_new_category_button:
                View viewToManage = findViewById(R.id.categorySpinner);

                if(viewToManage instanceof Spinner){
                    String checkExistance = ((Spinner) viewToManage).getSelectedItem().toString();
                    if(checkExistance.equals("") || newCategory.getText().toString().equals("")){
                        Toast.makeText(this, "Insert exercise and category!", Toast.LENGTH_SHORT).show();
                    }else {
                        addExerciseToCategory(((Spinner) viewToManage).getSelectedItem().toString(), newCategory.getText().toString());
                        Toast.makeText(this, "Succesfully inserted!", Toast.LENGTH_SHORT).show();
                    }
                }

                else if(viewToManage instanceof EditText){
                    String checkExistance = ((EditText) viewToManage).getText().toString();
                    if(checkExistance.equals("") || newCategory.getText().toString().equals("")){
                        Toast.makeText(this, "Insert exercise and category!", Toast.LENGTH_SHORT).show();
                    }else{
                        addExerciseToCategory(((EditText) viewToManage).getText().toString(), newCategory.getText().toString());
                        if (COLOR_POPULATED.equals(""))COLOR_POPULATED = "009688";
                        databaseHelper.insertColorBasedOnPositionPick(COLOR_POPULATED, checkExistance);
                        replaceBack(innerLayout, chooseCategory);
                        color_picker.setVisibility(View.INVISIBLE);

                        Toast.makeText(this, "Succesfully inserted!", Toast.LENGTH_SHORT).show();
                    }
                }
                ArrayList<String> elementsForSpinner = getInformation(Constants.GROUPS);
                Collections.sort(elementsForSpinner, new Comparator<String>() {
                    @Override
                    public int compare(String s, String t1) {
                        return s.compareTo(t1);
                    }
                });
                elementsForSpinner.add(0 , "");
                chooseCategory.setAdapter(new SpinnerAdapter(this,elementsForSpinner));

                newCategory.setText("");


                break;

            case R.id.color_flicker:

                manageColorPickerDialog();

                break;

            case R.id.addNewCategory:

                //manageColorPickerDialog();

                View genericView = this.findViewById(R.id.categorySpinner);
                //manageColorPickerDialog();
                if(genericView instanceof Spinner){
                    replaceView(innerLayout, chooseCategory);
                    //manageColorPickerDialog();
                    //colorPick.setVisibility(View.VISIBLE);
                    //colorOption.setVisibility(View.VISIBLE);
                    color_picker.setVisibility(View.VISIBLE);
                    addNewCategory.setImageResource(R.drawable.clear_custom);
                    //addNewCategory.setBackgroundResource(R.drawable.clear_custom);
                }
                else
                if(genericView instanceof EditText){
                    replaceBack(innerLayout, chooseCategory);
                    //colorPick.setVisibility(View.INVISIBLE);
                    color_picker.setVisibility(View.INVISIBLE);
                   // colorOption.setVisibility(View.INVISIBLE);
                    //addNewCategory.setBackgroundResource(R.drawable.ic_launcher);
                    addNewCategory.setImageResource(R.drawable.ic_launcher);
                }
                break;
        }
    }


    private void manageColorPickerDialog(){

        ColorPickerDialog.newBuilder()
                .setDialogType(ColorPickerDialog.TYPE_PRESETS)
                .setShowAlphaSlider(false)
                .setDialogId(DIALOG_ID)
                .setAllowCustom(false)
                .setAllowPresets(true)
                .setDialogTitle(R.string.category_color)
                .show(this);
        }

    private void addExerciseToCategory(String category, String exercise){

        SharedPreferences preferences = getApplicationContext().getSharedPreferences(category, getApplicationContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        if(preferences.contains(category)){

            Set<String> exercises = preferences.getStringSet(category, null);

            if(exercises.contains(exercise)){
                Toast.makeText(getApplicationContext(), "Exercise already exists", Toast.LENGTH_SHORT).show();
            }
            else{
                exercises.add(exercise);
                editor.putStringSet(category, exercises);
                editor.clear();
                editor.apply();
            }
        }
        else{
            SharedPreferences defaultPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor defaultEditor = defaultPreferences.edit();
            Set<String> groups = defaultPreferences.getStringSet(Constants.GROUPS, null);

            if(!groups.contains(category)){

                groups.add(category);
                for (String muscle:
                     groups) {

                    Log.d("MARINAEEE", muscle);

                }
                defaultEditor.putStringSet(Constants.GROUPS, groups);
                defaultEditor.clear();
                defaultEditor.apply();

                SharedPreferences customPreferences = getApplicationContext().getSharedPreferences(category, getApplicationContext().MODE_PRIVATE);
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

        EditText editText = new EditText(getApplicationContext());
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

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        SharedPreferences.Editor editor = preferences.edit();

        ArrayList<String> arraylist = new ArrayList<>();

        if(preferences.contains(sharedPreference)){

            arraylist.addAll(preferences.getStringSet(sharedPreference, new HashSet<String>()));

        }
        return  arraylist;
    }


    @Override
    public void onColorSelected(int dialogId, @ColorInt int color) {
        switch (dialogId){
            case DIALOG_ID:
                String hexcode = Integer.toHexString(color);
                COLOR_POPULATED = hexcode;
                color_picker.setColorFilter(color);
                break;

        }
    }

    @Override
    public void onDialogDismissed(int dialogId) {

        switch (dialogId){

            case DIALOG_ID:

                break;

        }

    }

}
