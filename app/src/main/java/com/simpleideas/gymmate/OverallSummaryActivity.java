package com.simpleideas.gymmate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class OverallSummaryActivity extends AppCompatActivity {

    private PieChart pieChart;
    private DatabaseHelper databaseHelper;
    private DatabaseHelper databaseOperator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overall_summary);

        databaseHelper = new DatabaseHelper(this);


        PieData pieData = new PieData();

        setupPieChart();


    }

    private void setupPieChart() {



        List<PieEntry> pieDataCollection = databaseHelper.getMonthMapedWithNumbers();

        PieDataSet pieDataSet = new PieDataSet(pieDataCollection,"Month Based");
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        PieData pieData = new PieData(pieDataSet);

        pieChart = (PieChart) findViewById(R.id.overallPieChart);
        pieChart.setEntryLabelTextSize(10);
        pieChart.setUsePercentValues(true);
        pieChart.animateY(1000);
        pieChart.setData(pieData);



    }

}
