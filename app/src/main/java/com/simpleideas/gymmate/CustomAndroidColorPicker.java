package com.simpleideas.gymmate;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;

import com.jrummyapps.android.colorpicker.ColorPickerDialogListener;

/**
 * Created by George Ciopei on 2/24/2017.
 */

public class CustomAndroidColorPicker extends AppCompatActivity implements ColorPickerDialogListener{


    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);



    }

    @Override
    public void onColorSelected(int dialogId, @ColorInt int color) {

    }

    @Override
    public void onDialogDismissed(int dialogId) {

    }
}
