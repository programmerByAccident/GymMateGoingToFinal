package com.simpleideas.gymmate;

import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageView;

import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.events.DecoEvent;

/**
 * Created by George on 04/05/2017.
 */

public class CardioArea extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cardio_area);

        DecoView time_elaped = (DecoView) findViewById(R.id.time_elapsed);
        //ImageView chrono = (ImageView) findViewById(R.id.clock_sketch);

        perform_changes_to_decoviewsize(time_elaped);
    }



    private void perform_changes_to_decoviewsize(DecoView time_elapsed) {

//        int width = chrono.getHeight();
//        int height = chrono.getWidth();
//
//        ViewGroup.LayoutParams params = new ViewPager.LayoutParams();
//
//        int percent = width / 10;


//        time_elapsed.getLayoutParams().width = width - percent;
//        time_elapsed.getLayoutParams().height = height - percent;
        time_elapsed.requestLayout();
        time_elapsed
                .addSeries(new SeriesItem.Builder(Color.argb(255, 64, 196, 0))
                .setRange(0,100,0)
                .setInitialVisibility(true)
                .setLineWidth(2f)
                .build());
        SeriesItem seriesItem1 = new SeriesItem.Builder(Color.argb(255, 64, 196, 0))
                .setRange(0, 100, 0)
                .setLineWidth(35f)
                .build();
        int series1Index = time_elapsed.addSeries(seriesItem1);

//        time_elapsed.addEvent(new DecoEvent.Builder(DecoEvent.EventType.EVENT_SHOW, true)
//                .setDelay(1000)
//                .setDuration(2000)
//                .build());



        time_elapsed.addEvent(new DecoEvent.Builder(75)
                .setDuration(3600)
                .setDelay(4000)
                .setIndex(series1Index)
                .build());
        time_elapsed.addEvent(new DecoEvent.Builder(50)
                .setDuration(3600)
                .setDelay(8000)
                .setIndex(series1Index)
                .build());
        time_elapsed.addEvent(new DecoEvent.Builder(99)
                .setDuration(3600)
                .setDelay(11600)
                .setIndex(series1Index)
                .build());
//        time_elapsed.addEvent(new DecoEvent.Builder(3)
//                .setDuration(3600)
//                .setIndex(series1Index)
//                .build());
//        time_elapsed.addEvent(new DecoEvent.Builder(100).setIndex(series1Index).setDelay(8000).build());
//        time_elapsed.addEvent(new DecoEvent.Builder(10).setIndex(series1Index).setDelay(12000).build());
//        time_elapsed.addEvent(new DecoEvent.Builder(50).setIndex(series1Index).setDelay(8000).build());


    }

    @Override
    protected void onResume() {
        super.onResume();
    }


}
