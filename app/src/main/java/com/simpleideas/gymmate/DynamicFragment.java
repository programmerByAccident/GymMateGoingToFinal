package com.simpleideas.gymmate;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import adapters.CustomRecyclerViewAdapter;

/**
 * Created by programmerByAccident on 8/28/2016.
 */

public class DynamicFragment extends android.support.v4.app.Fragment{

    public DataSenderBetweenFragments senderBetweenFragments;
    TextView textView;
    private static String dateString;
    private int difference;
    DatabaseHelper databaseManager;
    private long currentDate;
    private TextView no_data_text_view;
    RelativeLayout no_data_available;
    private LetsMakeAnAdapter adapter;
    private  RecyclerView recyclerViewOne;

    private CustomRecyclerViewAdapter customRecyclerViewAdapter;
    private static FloatingActionButton refreshButton;
    //ArrayList<ExerciseTemplate> arrayList;
    ArrayList<ExerciseTagInformation> arrayList;
    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        makeConnectionToTheInterface();

        dateString = getArguments().getString("DATEString");

        //Toast.makeText(getActivity().getApplicationContext(), dateString, Toast.LENGTH_SHORT).show();
        //triggerOfExistance = getArguments().getBoolean(Constants.TRIGGER_EXISTANCE);
        difference = getArguments().getInt("Difference");
        currentDate = getArguments().getLong("DATE");
        databaseManager = new DatabaseHelper(getContext());
        //rrayList=databaseManager.getAllExercises(dateString);
        arrayList=databaseManager.getAllExercisesMapped(dateString);
        customRecyclerViewAdapter = new CustomRecyclerViewAdapter(getActivity(),arrayList, dateString);

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i("OnCreate", "OnCreate");
    }

    public static String getDateString(){

        return dateString;

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("OnResume", "OnResume");

        Log.d("Here", "Before transaction");

        databaseManager = new DatabaseHelper(getContext());
        ArrayList<ExerciseTemplate> onResumeData = databaseManager.getAllExercises(dateString);

        CustomRecyclerViewAdapter customRecyclerViewAdapter2;
        customRecyclerViewAdapter2= new CustomRecyclerViewAdapter(getActivity(), databaseManager.getAllExercisesMapped(dateString),dateString);
        if (customRecyclerViewAdapter2.getItemCount() == 0) {
            recyclerViewOne.setVisibility(View.INVISIBLE);
        }

        Log.d("Here", "After transaction");
    }

    public static void hideFAB(){

        refreshButton.hide();

    }



    public static void showFAB(){

        refreshButton.show();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = null;
        Date date = new Date();
        date.setTime(currentDate);

        view = inflater.inflate(R.layout.dynamic_fragment_with_listview, container, false);
        setTextWithCurrentItem();
        recyclerViewOne = (RecyclerView) view.findViewById(R.id.recyclerViewOne);
//        refreshButton = (FloatingActionButton) view.findViewById(R.id.fab_trigger_of_existance_false);
//        refreshButton.setOnClickListener(new View.OnClickListener() {
//            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//            @Override
//            public void onClick(View view) {
//
//                setFabActions(difference);
//            }
//        });


        no_data_available = (RelativeLayout) view.findViewById(R.id.relative_layout_no_data);
        recyclerViewOne.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewOne.setAdapter(customRecyclerViewAdapter);
        if (customRecyclerViewAdapter.getItemCount() == 0){
            recyclerViewOne.setVisibility(View.INVISIBLE);

            no_data_available.setVisibility(View.VISIBLE);
            no_data_text_view = (TextView) view.findViewById(R.id.no_data_text_view);
            no_data_text_view.setTypeface(null, Typeface.BOLD);
            no_data_text_view.setText("Records are empty" + "\n" + dateString + "\n" + "Add a record with pen buttom from bottom right.");

        }
        return view;
        //dapter = new LetsMakeAnAdapter(getActivity(), arrayList);


    }



    public void setTextWithCurrentItem(){

       int difference = ((StartActivity)getActivity()).getViewPagerPosition();
        int result = difference - 50000;

        org.joda.time.DateTime dateTime = org.joda.time.DateTime.now(DateTimeZone.forTimeZone(TimeZone.getDefault()));
        org.joda.time.DateTime current_date = dateTime.plusDays(result);
        Date normalDate = current_date.toDate();

        DateTimeFormatter fmt = DateTimeFormat.forPattern("E d MMMM yyyy");
        String str;

        str = fmt.print(current_date);

        ((StartActivity)getActivity()).setTitle(str);

    }




    private void populateWithData(View view, LayoutInflater inflater, ViewGroup container, Bundle savedState, int difference  ){

        view = inflater.inflate(R.layout.dynamic_fragment_with_listview, container, false);
        //ListView listView = (ListView) view.findViewById(R.id.listViewWithItems);
        //initializeFloatingButton(view);
        //listenerInsertFragmentClick(view);

        ArrayList<ExerciseTemplate> adapterValues;
        adapterValues = databaseManager.getAllExercises(dateString);
        Log.i("SizeOfTheValues", String.valueOf(adapterValues.size()));
        adapter.setExercises(adapterValues);

       // listView.setAdapter(adapter);

    }


    public interface DataSenderBetweenFragments {

        public void sendPhoto(Bitmap picture);

    }

    private void makeConnectionToTheInterface(){
        /**
         *   @scope: Create connection with the Interface.
         *   @reason: onAttach method is deprecated since API23, the code from onAttach shall be in onCreate to be safely called.
         **/

        try {
            senderBetweenFragments = (DataSenderBetweenFragments) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Please implement DataSenderBetweenFragments");
        }
    }






    private void setFabActions(int difference){

        Intent intent = new Intent(getActivity(), ListViewWithMuscleGroups.class);
        intent.putExtra("date", dateString);
        startActivity(intent);

    }



    public static DynamicFragment createInstanceBasedOnDifferenceBetweenCurrentDateAndPosition(int difference){

        Bundle arguments = new Bundle();
        String str;
        DynamicFragment dynamicFragment = new DynamicFragment();
        DateTime dateTime = DateTime.now(DateTimeZone.forTimeZone(TimeZone.getDefault()));
        DateTime current_date = dateTime.plusDays(difference);
        Date normalDate = current_date.toDate();

        DateTimeFormatter fmt = DateTimeFormat.forPattern("E - d - MMMM - yyyy");
        str = fmt.print(current_date);

        arguments.putString("DATEString", str);
        arguments.putInt("Difference", difference);
        arguments.putLong("DATE", normalDate.getTime());


        dynamicFragment.setArguments(arguments);

        return dynamicFragment;
    }

    private void floatingButtonActions(){

        Log.d("DynamicFragment","floating button actions");
        AccesRemoteDatabase start_background_thread = new AccesRemoteDatabase();
        start_background_thread.execute(Constants.PHP_SCRIPT_LOCAL_HOST);
        Log.d("FBActions","after");
    }

    public void insertFragment(){

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();



    }



}




