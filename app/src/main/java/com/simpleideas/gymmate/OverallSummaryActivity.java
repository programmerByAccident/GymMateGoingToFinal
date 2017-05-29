package com.simpleideas.gymmate;

import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class OverallSummaryActivity extends AppCompatActivity {

    private PieChart pieChart;
    private DatabaseHelper databaseHelper;
    private DatabaseHelper databaseOperator;
    private RadioButton month, quarter, half, year;

    private final int monthLimit = 1;
    private final int quarterLimit = 3;
    private final int halfLimit = 6;

    private String[] monthMap = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overall_summary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.start_activity_toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_for_whities);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        databaseHelper = new DatabaseHelper(this);
        pieChart = (PieChart) findViewById(R.id.overallPieChart);
        pieChart.setEntryLabelTextSize(10);
        pieChart.setUsePercentValues(true);
        pieChart.animateY(1000);

        setupRadioButton(pieChart);

    }

    private void setupRadioButton(final PieChart pieChart){

        RadioGroup radioSession = (RadioGroup) findViewById(R.id.radioSession);
        List<PieEntry > pieDataCollection = databaseHelper.getMonthMappedWithNumbers(getRelevantMonthsOnly(monthLimit));
        PieDataSet pieDataSet = new PieDataSet(pieDataCollection,"Month Based");
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        radioSession.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                List<PieEntry> pieDataCollection = new ArrayList<PieEntry>();

                if (i == R.id.month){
                    pieDataCollection = databaseHelper.getMonthMappedWithNumbers(getRelevantMonthsOnly(monthLimit));
                }
                else if (i == R.id.quarter){
                    pieDataCollection = databaseHelper.getMonthMappedWithNumbers(getRelevantMonthsOnly(quarterLimit));
                }
                else if (i == R.id.half){
                    pieDataCollection = databaseHelper.getMonthMappedWithNumbers(getRelevantMonthsOnly(halfLimit));
                }
                else if (i == R.id.year){
                    pieDataCollection = databaseHelper.getMonthMappedWithNumbers(monthMap);
                }

                PieDataSet pieDataSet = new PieDataSet(pieDataCollection,"Month Based");
                pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                PieData pieData = new PieData(pieDataSet);
                pieChart.setData(pieData);
                pieChart.animateY(1000);
                pieChart.notifyDataSetChanged();

            }
        });
    }

    private String[] getRelevantMonthsOnly(int limit){

        String[] relevantMonths = new String[limit];

        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int month = cal.get(Calendar.MONTH);

        int counter = 0;

        for (int index = month; index>=-1; index--){

            if (counter == limit) break;

            if (index != -1){
                relevantMonths[counter] = monthMap[index];
            }
            else {
                relevantMonths[counter] = monthMap[monthMap.length - 1];
            }
            counter++;
        }

        return relevantMonths;
    }

}








































