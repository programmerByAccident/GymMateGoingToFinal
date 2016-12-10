package com.simpleideas.gymmate;

import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;

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

                int valueReps = Integer.valueOf(editTextRepetitions.getText().toString());

                valueReps++;

                editTextRepetitions.setText(String.valueOf(valueReps));

            case R.id.substraction_repetitions:

                EditText editTextRepetitionsSubstract = (EditText) findViewById(R.id.editTextRepetitions);
                int valueRepsSubstract = Integer.valueOf(editTextRepetitionsSubstract.getText().toString());

                valueRepsSubstract--;

                editTextRepetitionsSubstract.setText(String.valueOf(valueRepsSubstract));



        }


    }
}
