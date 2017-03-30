package adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.simpleideas.gymmate.InsertFragmentSequenceZero;
import com.simpleideas.gymmate.PlotProgressForExercise;

import Fragments.HistoryExerciceFragment;

/**
 * Created by Geprge on 3/17/2017.
 */

public class TabFragmentAdapter extends FragmentPagerAdapter{


    private Context context;
    private String[] titles = {"Track", "History", "Progress"};
    public TabFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){

            case 0:
                return InsertFragmentSequenceZero.newInstance();
            case 1:
                return HistoryExerciceFragment.newInstance();
            case 2:
                return PlotProgressForExercise.newInstance();


            default:
                return
                        null;

        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return titles.length;
    }
}
