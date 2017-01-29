package com.simpleideas.gymmate;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static android.R.attr.button;

/**
 * Created by Geprge on 12/27/2016.
 */

public class DialogForAddingContent extends DialogFragment implements View.OnClickListener{

    private ImageButton addCategories;
    private String preferences;
    private EditText newCategory;
    private Spinner chooseCategory;
    private SpinnerAdapter spinnerAdapter;
    private View globalView, customSpinnerWidth;
    private Button insert_category, replaceAgain, getText;
    private ImageButton addNewCategory;
    LinearLayout manipulate, innerLayout;

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
        manipulate = (LinearLayout) view.findViewById(R.id.manipulate);
        innerLayout = (LinearLayout) view.findViewById(R.id.innerLayout);
        Log.d("View 0", innerLayout.getChildAt(0).toString());
        Log.d("View 1", innerLayout.getChildAt(1).toString());
//        Log.d("View 0", manipulate.getChildAt(0).toString());
//        Log.d("View 1", manipulate.getChildAt(1).toString());
//        Log.d("View 2", manipulate.getChildAt(2).toString());
//        Log.d("View 3", manipulate.getChildAt(3).toString());
//        Log.d("View 4", manipulate.getChildAt(4).toString());
//        Log.d("View 5", manipulate.getChildAt(5).toString());
//        Log.d("View 6", manipulate.getChildAt(6).toString());
//        Log.d("View 7", manipulate.getChildAt(7).toString());
//        Log.d("View 8", manipulate.getChildAt(8).toString());
//        Log.d("View 9", manipulate.getChildAt(9).toString());
//        Log.d("View 10", manipulate.getChildAt(10).toString());
//        Log.d("View 11", manipulate.getChildAt(11).toString());



        newCategory = (EditText) view.findViewById(R.id.muscle_group);
        chooseCategory = (Spinner) view.findViewById(R.id.categorySpinner);
        insert_category = (Button) view.findViewById(R.id.insert_new_category_button);
        replaceAgain = (Button) view.findViewById(R.id.replaceBack);
        getText = (Button) view.findViewById(R.id.buttonGetText);
        addNewCategory = (ImageButton) view.findViewById(R.id.addNewCategory);
        addNewCategory.setOnClickListener(this);

        replaceAgain.setOnClickListener(this);
        insert_category.setOnClickListener(this);
        getText.setOnClickListener(this);
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
            case R.id.insert_new_category_button:
                replaceView(innerLayout, chooseCategory);
                break;

            case R.id.replaceBack:
                replaceBack(innerLayout, chooseCategory);
                break;

            case R.id.addNewCategory:

                View viewText2 = getView().findViewById(R.id.categorySpinner);

                if(viewText2 instanceof Spinner){
                    replaceView(innerLayout, chooseCategory);

                }
                else
                if(viewText2 instanceof EditText){
                    replaceBack(innerLayout, chooseCategory);
                }

                Toast.makeText(getContext(), toShow, Toast.LENGTH_SHORT).show();

                break;

            case R.id.buttonGetText:

                View viewText = getView().findViewById(R.id.categorySpinner);

                if(viewText instanceof Spinner){
                    Spinner spinner = (Spinner)viewText;
                    toShow = spinner.getSelectedItem().toString();
                }
                else
                if(viewText instanceof EditText){
                    EditText editText = (EditText)viewText;
                    toShow = editText.getText().toString();
                }

                Toast.makeText(getContext(), toShow, Toast.LENGTH_SHORT).show();

                break;

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
        editText.setBackgroundColor(Color.TRANSPARENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            editText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }
        
        editText.setWidth(width);
        //22 sp text size
        editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        editText.setId(viewToReplace.getId());
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
