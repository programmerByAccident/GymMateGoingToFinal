package com.simpleideas.gymmate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;

public class OverallSummaryActivity extends AppCompatActivity {

    private PieChart pieChart;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overall_summary);

        databaseHelper = new DatabaseHelper(this);

        pieChart = (PieChart) findViewById(R.id.overallPieChart);
        PieData pieData = new PieData();


    }
}
