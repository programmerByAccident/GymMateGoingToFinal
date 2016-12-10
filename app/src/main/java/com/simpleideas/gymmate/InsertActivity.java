package com.simpleideas.gymmate;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by George Ciopei on 12/10/2016.
 */

public class InsertActivity extends AppCompatActivity implements View.OnClickListener{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_activity);


        declareSubstractionAdditionButtons();
    }

    private void getInformationFromIntent(){

        Intent intent = getIntent();

        String exerciseName = intent.getExtras().getString(Constants.EXERCISE_NAME);

        String muscleName = intent.getExtras().getString(Constants.MUSCLE_NAME);



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

        }


    }
}
