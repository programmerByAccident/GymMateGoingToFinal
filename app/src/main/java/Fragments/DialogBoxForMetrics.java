package Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.simpleideas.gymmate.R;
import com.simpleideas.gymmate.SpinnerAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Geprge on 3/26/2017.
 */

public class DialogBoxForMetrics extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String[] metrics = {"KG - Metric System", "LBS - Imperial System"};
        builder.setMessage("Greetings!")
                .setMessage("Choose measurement system")
                .setItems(metrics, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

                switch (i){

                    case 0:

                        break;

                    case 1:

                        break;

                }
            }
        });
        return builder.create();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_box_for_metrics, container,false);

        Spinner metricsSpinner = (Spinner) view.findViewById(R.id.metricsSpinner);
        ArrayList<String> metricsElements = new ArrayList<>();

        metricsElements.add("KG - Metric System");
        metricsElements.add("LBS - Imperial System");
        metricsSpinner.setAdapter(new SpinnerAdapter(getActivity(),metricsElements));

        return view;
    }
}
