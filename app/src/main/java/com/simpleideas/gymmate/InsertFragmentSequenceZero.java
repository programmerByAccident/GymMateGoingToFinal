package com.simpleideas.gymmate;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.Date;
import java.util.TimeZone;

import adapters.InsertListViewAdapter;

/**
 * Created by Geprge on 3/17/2017.
 */

public class InsertFragmentSequenceZero extends Fragment implements View.OnClickListener{

    private Context context;

    private String exercise_name;
    private String muscle_name;
    private String date;
    private int difference;
    private SQLiteDatabase sqLiteDatabase;
    private InsertActivity insertActivity;
    private RecyclerView listView;
    private InsertListViewAdapter listViewAdapter;
    private View globalViewReference;
    public static InsertFragmentSequenceZero newInstance(){

        InsertFragmentSequenceZero insertFragmentSequenceZero = new InsertFragmentSequenceZero();

        return insertFragmentSequenceZero;

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.insert_activity, container, false);
        globalViewReference = view;
        getInformationFromIntent(view);

        declareSubstractionAdditionButtons(view);
        listView = (RecyclerView) view.findViewById(R.id.list_view_insert_fragment_activity);
        listViewAdapter = new InsertListViewAdapter(insertActivity.getApplicationContext(), insertActivity.getArrayForInsertFragment());
        listView.setLayoutManager(new LinearLayoutManager(insertActivity.getApplicationContext()));
        listView.setAdapter(listViewAdapter);
        return view;
    }

    private void getInformationFromIntent(View view){

        insertActivity = (InsertActivity)getActivity();

        exercise_name = insertActivity.getExercise_name();
        muscle_name = insertActivity.getMuscle_name();
        difference = insertActivity.getDifference();
        date = insertActivity.getDate();

        //toolbar_actions(view,exercise_name);

    }


    private void declareSubstractionAdditionButtons(View view){

        ImageButton additionReps = (ImageButton) view.findViewById(R.id.addition_repetitions);
        additionReps.setOnClickListener(this);
        ImageButton additionsWeight = (ImageButton) view.findViewById(R.id.addition_weight);
        additionsWeight.setOnClickListener(this);
        ImageButton substractionReps = (ImageButton) view.findViewById(R.id.substraction_repetitions);
        substractionReps.setOnClickListener(this);
        ImageButton substractionWeights = (ImageButton) view.findViewById(R.id.substraction_weight);
        substractionWeights.setOnClickListener(this);
        Button save_button = (Button) view.findViewById(R.id.save_button);
        save_button.setOnClickListener(this);
        Button clear_button = (Button) view.findViewById(R.id.clear_button);

    }
    private void toolbar_actions(View view, String exercise_name){
        TextView textView = (TextView) view.findViewById(R.id.titleTextView);
        textView.setVisibility(View.VISIBLE);
        textView.setText(exercise_name);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.addition_repetitions:

                EditText editTextRepetitions = (EditText) globalViewReference.findViewById(R.id.editTextRepetitions);
                String valueRepsS = editTextRepetitions.getText().toString();
                if(valueRepsS.matches("")){

                    editTextRepetitions.setText("0");
                }
                else{
                    int valueReps = Integer.valueOf(valueRepsS);
                    valueReps++;
                    editTextRepetitions.setText(String.valueOf(valueReps));
                }
                break;

            case R.id.substraction_repetitions:
                EditText editTextRepetitionsSubstract = (EditText) globalViewReference.findViewById(R.id.editTextRepetitions);
                String valueS = editTextRepetitionsSubstract.getText().toString();
                if(valueS.matches("")){
                    editTextRepetitionsSubstract.setText("0");
                }
                else{
                    int valueRepsSubstract = Integer.valueOf(editTextRepetitionsSubstract.getText().toString());
                    valueRepsSubstract--;
                    editTextRepetitionsSubstract.setText(String.valueOf(valueRepsSubstract));
                }
                break;

            case R.id.addition_weight:
                EditText editTextWeightAddition = (EditText) globalViewReference.findViewById(R.id.editTextWeights);
                String valueWeightS = editTextWeightAddition.getText().toString();
                if(valueWeightS.matches("")){
                    editTextWeightAddition.setText("0.0");
                }
                else{
                    double value = Double.valueOf(valueWeightS);
                    value++;
                    editTextWeightAddition.setText(String.valueOf(value));
                }
                break;

            case R.id.substraction_weight:
                EditText editTextWeightSubstract = (EditText) globalViewReference.findViewById(R.id.editTextWeights);
                String valueSS = editTextWeightSubstract.getText().toString();
                if(valueSS.matches("")){

                    editTextWeightSubstract.setText("0");
                }
                else{
                    double valueRepsSubstract = Double.valueOf(valueSS);
                    valueRepsSubstract--;
                    editTextWeightSubstract.setText(String.valueOf(valueRepsSubstract));
                }
                break;

            case R.id.save_button:
                DateTime dateTime = DateTime.now(DateTimeZone.forTimeZone(TimeZone.getDefault()));
                DateTime current_date = dateTime.plusDays(difference);
                Date normalDate = current_date.toDate();
                DatabaseHelper databaseHelper = new DatabaseHelper(insertActivity.getApplicationContext());
                EditText weights = (EditText) globalViewReference.findViewById(R.id.editTextWeights);
                EditText reps =(EditText) globalViewReference.findViewById(R.id.editTextRepetitions);
                String w,r;
                float weight=0;
                int rep = 0;
                w = weights.getText().toString();
                r = reps.getText().toString();
                Log.d("Values of w and r", w + r);
                if(w.length() < 1 || r.length() < 1){

                    Toast.makeText(insertActivity.getApplicationContext(), "No values inserted", Toast.LENGTH_SHORT).show();

                }
                else {
                    weight = Float.valueOf(weights.getText().toString());
                    rep = Integer.valueOf(reps.getText().toString());
                    databaseHelper.insertExerciseIntoDatabase(date,muscle_name, exercise_name, rep, weight);
                }
                listViewAdapter.setExerciseInfo(databaseHelper.getInformationForExercise(insertActivity.getExercise_name()));
                listViewAdapter.notifyDataSetChanged();


                break;
            case R.id.clear_button:
                EditText weight_clear = (EditText) globalViewReference.findViewById(R.id.editTextWeights);
                EditText reps_clear =(EditText) globalViewReference.findViewById(R.id.editTextRepetitions);
                weight_clear.setText("");
                reps_clear.setText("");
                break;

        }
    }

//    @Override
//    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.addition_repetitions:
//
//                EditText editTextRepetitions = (EditText) view.findViewById(R.id.editTextRepetitions);
//                String valueRepsS = editTextRepetitions.getText().toString();
//                if(valueRepsS.matches("")){
//
//                    editTextRepetitions.setText("0");
//                }
//                else{
//                    int valueReps = Integer.valueOf(valueRepsS);
//                    valueReps++;
//                    editTextRepetitions.setText(String.valueOf(valueReps));
//                }
//                break;
//
//            case R.id.substraction_repetitions:
//                EditText editTextRepetitionsSubstract = (EditText) view.findViewById(R.id.editTextRepetitions);
//                String valueS = editTextRepetitionsSubstract.getText().toString();
//                if(valueS.matches("")){
//                    editTextRepetitionsSubstract.setText("0");
//                }
//                else{
//                    int valueRepsSubstract = Integer.valueOf(editTextRepetitionsSubstract.getText().toString());
//                    valueRepsSubstract--;
//                    editTextRepetitionsSubstract.setText(String.valueOf(valueRepsSubstract));
//                }
//                break;
//
//            case R.id.addition_weight:
//                EditText editTextWeightAddition = (EditText) view.findViewById(R.id.editTextWeights);
//                String valueWeightS = editTextWeightAddition.getText().toString();
//                if(valueWeightS.matches("")){
//                    editTextWeightAddition.setText("0.0");
//                }
//                else{
//                    double value = Double.valueOf(valueWeightS);
//                    value++;
//                    editTextWeightAddition.setText(String.valueOf(value));
//                }
//                break;
//
//            case R.id.substraction_weight:
//                EditText editTextWeightSubstract = (EditText) view.findViewById(R.id.editTextWeights);
//                String valueSS = editTextWeightSubstract.getText().toString();
//                if(valueSS.matches("")){
//
//                    editTextWeightSubstract.setText("0");
//                }
//                else{
//                    double valueRepsSubstract = Double.valueOf(valueSS);
//                    valueRepsSubstract--;
//                    editTextWeightSubstract.setText(String.valueOf(valueRepsSubstract));
//                }
//                break;
//
//            case R.id.save_button:
//                DateTime dateTime = DateTime.now(DateTimeZone.forTimeZone(TimeZone.getDefault()));
//                DateTime current_date = dateTime.plusDays(difference);
//                Date normalDate = current_date.toDate();
//                DatabaseHelper databaseHelper = new DatabaseHelper(insertActivity.getApplicationContext());
//                EditText weights = (EditText) view.findViewById(R.id.editTextWeights);
//                EditText reps =(EditText) view.findViewById(R.id.editTextRepetitions);
//                String w,r;
//                float weight=0;
//                int rep = 0;
//                w = weights.getText().toString();
//                r = reps.getText().toString();
//                Log.d("Values of w and r", w + r);
//                if(w.length() < 1 || r.length() < 1){
//
//                    Toast.makeText(insertActivity.getApplicationContext(), "No values inserted", Toast.LENGTH_SHORT).show();
//
//                }
//                else {
//                    weight = Float.valueOf(weights.getText().toString());
//                    rep = Integer.valueOf(reps.getText().toString());
//                    databaseHelper.insertExerciseIntoDatabase(date,muscle_name, exercise_name, rep, weight);
//                }
//
//                break;
//            case R.id.clear_button:
//                EditText weight_clear = (EditText) view.findViewById(R.id.editTextWeights);
//                EditText reps_clear =(EditText) view.findViewById(R.id.editTextRepetitions);
//                weight_clear.setText("");
//                reps_clear.setText("");
//                break;
//
//        }
//    }
}
