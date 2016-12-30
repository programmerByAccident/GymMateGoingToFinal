package com.simpleideas.gymmate;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.Date;
import java.util.TimeZone;

/**
 * Created by George Ciopei on 12/10/2016.
 */

public class InsertActivity extends AppCompatActivity implements View.OnClickListener{


    private String exercise_name;
    private String muscle_name;
    private String date;
    private int difference;
    private SQLiteDatabase sqLiteDatabase;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_activity);

        getInformationFromIntent();

        declareSubstractionAdditionButtons();
    }

    private void getInformationFromIntent(){

        Intent intent = getIntent();

        exercise_name = intent.getExtras().getString(Constants.EXERCISE_NAME);
        muscle_name = intent.getExtras().getString(Constants.MUSCLE_NAME);
        difference = intent.getExtras().getInt("Difference");
        date = intent.getExtras().getString("date");

        toolbar_actions(exercise_name);

    }


    private void declareSubstractionAdditionButtons(){

        ImageButton additionReps = (ImageButton) findViewById(R.id.addition_repetitions);
        additionReps.setOnClickListener(this);
        ImageButton additionsWeight = (ImageButton) findViewById(R.id.addition_weight);
        additionsWeight.setOnClickListener(this);
        ImageButton substractionReps = (ImageButton) findViewById(R.id.substraction_repetitions);
        substractionReps.setOnClickListener(this);
        ImageButton substractionWeights = (ImageButton) findViewById(R.id.substraction_weight);
        substractionWeights.setOnClickListener(this);
        Button save_button = (Button) findViewById(R.id.save_button);
        save_button.setOnClickListener(this);
        Button clear_button = (Button) findViewById(R.id.clear_button);
        clear_button.setOnClickListener(this);

    }
    private void toolbar_actions(String exercise_name){
        TextView textView = (TextView) findViewById(R.id.titleTextView);
        textView.setVisibility(View.VISIBLE);
        textView.setText(exercise_name);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.addition_repetitions:

                EditText editTextRepetitions = (EditText) findViewById(R.id.editTextRepetitions);
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
                EditText editTextRepetitionsSubstract = (EditText) findViewById(R.id.editTextRepetitions);
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
                EditText editTextWeightAddition = (EditText) findViewById(R.id.editTextWeights);
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
                EditText editTextWeightSubstract = (EditText) findViewById(R.id.editTextWeights);
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
                DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
                EditText weights = (EditText) findViewById(R.id.editTextWeights);
                EditText reps =(EditText) findViewById(R.id.editTextRepetitions);
                String w,r;
                float weight=0;
                int rep = 0;
                w = weights.getText().toString();
                r = reps.getText().toString();
                Log.d("Values of w and r", w + r);
                if(w.length() < 1 || r.length() < 1){

                    Toast.makeText(getApplicationContext(), "No values inserted", Toast.LENGTH_SHORT).show();

                }
                else {
                    weight = Float.valueOf(weights.getText().toString());
                    rep = Integer.valueOf(reps.getText().toString());
                    databaseHelper.insertExerciseIntoDatabase(date, exercise_name, rep, weight);
                }

                break;
            case R.id.clear_button:
                EditText weight_clear = (EditText) findViewById(R.id.editTextWeights);
                EditText reps_clear =(EditText) findViewById(R.id.editTextRepetitions);
                weight_clear.setText("");
                reps_clear.setText("");
                break;

        }


    }
}
