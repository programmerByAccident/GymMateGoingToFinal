package com.simpleideas.gymmate;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.jobs.AnimatedMoveViewJob;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Geprge on 3/18/2017.
 */

public class PlotProgressForExercise extends Fragment implements View.OnClickListener{


//    private exercise_name;
//    private muscle_name;
//    private date;
//    private difference;
    private DatabaseHelper databaseHelper;
//    private XYPlot plot;
    private InsertActivity insertActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.plot_progress_for_exercise, container, false);
        insertActivity = (InsertActivity)getActivity();
        databaseHelper = new DatabaseHelper(insertActivity.getApplicationContext());
        LineChart lineChart = (LineChart) view.findViewById(R.id.chart);
        manipulateChart(lineChart);

        //esignPlot(plot);
        return view;
    }

    public static PlotProgressForExercise newInstance() {

        Bundle args = new Bundle();

        PlotProgressForExercise fragment = new PlotProgressForExercise();
        fragment.setArguments(args);
        return fragment;
    }

    private void manipulateChart(LineChart lineChart){

        List<Entry> lineChartArrayListforWeight = new ArrayList<>();
        List<Entry> lineChartArrayListForReps = new ArrayList<>();

        if (databaseHelper.getLast10WeightRecords(insertActivity.getExercise_name()).size() == 0){
            return;
        }

        ArrayList<Float> weight = databaseHelper.getLast10WeightRecords(insertActivity.getExercise_name());
        Collections.reverse(weight);
        ArrayList<Integer> reps = databaseHelper.getLast10RepsRecords(insertActivity.getExercise_name());
        Collections.reverse(reps);
        for (int index = 0; index<weight.size(); index++){

            Entry element = new Entry(index, weight.get(index));
            Entry repElement = new Entry(index, reps.get(index));
            lineChartArrayListforWeight.add(index, element);
            lineChartArrayListForReps.add(index, repElement);

        }

        LineDataSet weightDataSet = new LineDataSet(lineChartArrayListforWeight, "weight");
        weightDataSet.setColor(Color.RED);
        weightDataSet.setLineWidth(3f);
        LineDataSet repDataSet = new LineDataSet(lineChartArrayListForReps, "repetitions");
        repDataSet.setColor(Color.GREEN);
        repDataSet.setLineWidth(3f);
        List<ILineDataSet> dataCollection = new ArrayList<>();
        LineData lineData = new LineData(weightDataSet);
        LineData lineDatareps = new LineData(repDataSet);

        dataCollection.add(repDataSet);
        dataCollection.add(weightDataSet);

        lineChart.setDrawGridBackground(true);

        lineChart.setData(new LineData(dataCollection));

    }

//    private void designPlot(XYPlot plot){
//
//        Number[] domainLabels = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
//        ArrayList<Float> weightLabels = databaseHelper.getLast10WeightRecords(insertActivity.getExercise_name());
//        Number[] test1 = {2,3,5,1,2,5,6,7,8,1};
//        Number[] test2 = {5,7,8,10,2,3,4,5,6,7};
//        ArrayList<Integer> repsLabels = databaseHelper.getLast10RepsRecords(insertActivity.getExercise_name());
//        LineAndPointFormatter series1Format = new LineAndPointFormatter(Color.RED, Color.GREEN, Color.BLUE, null);
//        XYSeries series1 = new SimpleXYSeries(weightLabels, repsLabels, "progress");
//
//        XYSeries series2 = new SimpleXYSeries(Arrays.asList(test1), Arrays.asList(test2), "wtf");
//
//        plot.addSeries(series1, series1Format);
//    }

    @Override
    public void onClick(View view) {

    }
}
