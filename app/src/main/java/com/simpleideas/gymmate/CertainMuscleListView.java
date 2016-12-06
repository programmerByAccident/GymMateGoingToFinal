package com.simpleideas.gymmate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Geprge on 12/6/2016.
 */

public class CertainMuscleListView extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.certain_muscle_group);
        TextView muscle_text_view = (TextView) findViewById(R.id.muscle_group_text_view);
        String muscle_name;

        Intent intent = getIntent();

        muscle_name = intent.getExtras().getString(Constants.MUSCLE_NAME);

        if(muscle_name != null){
            muscle_text_view.setText(muscle_name);
        }

        else{muscle_text_view.setText("Nothing here");}


    }
}
