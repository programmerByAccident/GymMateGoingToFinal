package com.simpleideas.gymmate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Geprge on 1/3/2017.
 */

public class SpinnerAdapter extends ArrayAdapter<String> {

    private ArrayList<String> spinnerElements;
    private LayoutInflater inflater;
    public SpinnerAdapter(Context context, ArrayList<String> spinnerElements) {
        super(context,R.layout.spinner_item, spinnerElements);

        this.spinnerElements = spinnerElements;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View customViewToInflate = convertView;

        if(customViewToInflate == null){

            inflater = LayoutInflater.from(getContext());

            customViewToInflate = inflater.inflate(R.layout.spinner_item, null);
        }

        TextView spinnerItem = (TextView) customViewToInflate.findViewById(R.id.spinnerItem);

        spinnerItem.setText(this.spinnerElements.get(position).toString());

        return customViewToInflate;
    }
}
