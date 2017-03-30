package Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArraySet;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.simpleideas.gymmate.DatabaseHelper;
import com.simpleideas.gymmate.InsertActivity;
import com.simpleideas.gymmate.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import adapters.HistoryFragmentAdapter;

/**
 * Created by Geprge on 3/24/2017.
 */

public class HistoryExerciceFragment extends Fragment{

    private InsertActivity insertActivity;
    private DatabaseHelper databaseHelper;
    HistoryFragmentAdapter historyFragment;
    private static final String TAG = "HistoryFragment";

    public static HistoryExerciceFragment newInstance() {

        Bundle args = new Bundle();

        HistoryExerciceFragment fragment = new HistoryExerciceFragment();

        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_layout,container, false);
        HashMap<Integer, String> monthMap = new HashMap<>();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.history_recycler_view);
        monthMap.put(0, "January");
        monthMap.put(1, "February");
        monthMap.put(2, "March");
        monthMap.put(3, "April");
        monthMap.put(4, "May");
        monthMap.put(5, "June");
        monthMap.put(6, "July");
        monthMap.put(7, "August");
        monthMap.put(8, "September");
        monthMap.put(9, "October");
        monthMap.put(10, "November");
        monthMap.put(11, "December");
        insertActivity = (InsertActivity)getActivity();
        databaseHelper = new DatabaseHelper(insertActivity.getApplicationContext());
        HashMap<Integer, ArrayList<String>> mapString = databaseHelper.getInformationOnMonthlyBasis(insertActivity.getExercise_name());

        historyFragment = new HistoryFragmentAdapter(insertActivity.getApplicationContext(),mapString,monthMap);

        recyclerView.setLayoutManager(new LinearLayoutManager(insertActivity.getApplicationContext()));
        recyclerView.setAdapter(historyFragment);
        Log.d(TAG, "onCreateView: reached");

        return view;
    }
}
