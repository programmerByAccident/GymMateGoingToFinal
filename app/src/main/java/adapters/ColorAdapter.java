package adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.simpleideas.gymmate.R;

import java.util.ArrayList;

/**
 * Created by Geprge on 3/11/2017.
 */

public class ColorAdapter extends ArrayAdapter<String> {
    private ArrayList<String> spinnerElements;
    private LayoutInflater inflater;
    private String flag;

    public ColorAdapter(Context context, String flag, ArrayList<String> spinnerElements) {
        super(context, R.layout.spinner_item, spinnerElements);
        this.flag = flag;
        this.spinnerElements = spinnerElements;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View v = super.getView(position, convertView, parent);
        v.setBackgroundColor(Color.parseColor("#" + spinnerElements.get(position).toString()));
        return v;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View customViewToInflate = convertView;

        if(customViewToInflate == null){

            inflater = LayoutInflater.from(getContext());

            customViewToInflate = inflater.inflate(R.layout.spinner_item, null);
        }

        customViewToInflate.setBackgroundColor(Color.parseColor("#" + spinnerElements.get(position).toString()));

        return customViewToInflate;
    }
}
