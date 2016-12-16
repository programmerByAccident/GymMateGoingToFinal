package com.simpleideas.gymmate;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by programmerByAccident on 8/28/2016.
 */

public class DynamicFragment extends android.support.v4.app.Fragment {

    public DataSenderBetweenFragments senderBetweenFragments;
    TextView textView;
    private String dateString;
    private int difference;
    DatabaseHelper databaseManager;
    private long currentDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("OnCreate", "OnCreate");
        makeConnectionToTheInterface();

        dateString = getArguments().getString("DATEString");
        Toast.makeText(getActivity().getApplicationContext(), dateString, Toast.LENGTH_SHORT).show();
        //triggerOfExistance = getArguments().getBoolean(Constants.TRIGGER_EXISTANCE);
        difference = getArguments().getInt("Difference");
        currentDate = getArguments().getLong("DATE");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i("OnCreate", "OnCreate");
    }

    @Override
    public void onResume() {
        super.onResume();

        ArrayList<ExerciseTemplate> arrayList = databaseManager.getAllExercises(dateString);

        if(arrayList.isEmpty() == false){

            ListView listView = (ListView)getView().findViewById(R.id.listViewWithItems);

            LetsMakeAnAdapter adapter = new LetsMakeAnAdapter(getActivity(), arrayList);

            listView.setAdapter(adapter);


        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        databaseManager = new DatabaseHelper(getActivity().getApplicationContext());
        Date date = new Date();
        date.setTime(currentDate);
        Log.i("onCreateView", "onCreateView");

        ArrayList<ExerciseTemplate> arrayList;

        arrayList=databaseManager.getAllExercises(dateString);

        Toast.makeText(getActivity().getApplicationContext(), String.valueOf(arrayList.size()), Toast.LENGTH_SHORT).show();

        if(arrayList.isEmpty()){

//            Toast toast1 = Toast.makeText(getActivity().getApplicationContext(),"ELSE", Toast.LENGTH_SHORT);
//            toast1.show();

            view = inflater.inflate(R.layout.layout_empty, container, false);
            createBehaviourForTriggerOfExistanceFalse(view, difference);
            return view;

        }
        else{
            populateWithData(view,inflater, container, savedInstanceState, difference);

        }



        return view;
        }

    private void populateWithData(View view, LayoutInflater inflater, ViewGroup container, Bundle savedState, int difference  ){

        view = inflater.inflate(R.layout.dynamic_fragment_with_listview, container, false);
        ListView listView = (ListView) view.findViewById(R.id.listViewWithItems);
        //initializeFloatingButton(view);
        //listenerInsertFragmentClick(view);

        ArrayList<ExerciseTemplate> adapterValues;
        adapterValues = databaseManager.getAllExercises(dateString);


        LetsMakeAnAdapter adapter = new LetsMakeAnAdapter(getActivity(), adapterValues);

        listView.setAdapter(adapter);



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




    public void createBehaviourForTriggerOfExistanceFalse(View view, final int difference){

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_trigger_of_existance_false);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFabActions(difference);
            }
        });


    }

    private void setFabActions(int difference){

        Intent intent = new Intent(getActivity(), ListViewWithMuscleGroups.class);
        intent.putExtra("date", dateString);
        startActivity(intent);

    }


    private ArrayList<ExerciseTemplate> getExerciseFromDatabase(){

        ArrayList<ExerciseTemplate> exercises = new ArrayList<>();







        return exercises;
    }

    private void listenerInsertFragmentClick(View view){

        Button insertButton = (Button)view.findViewById(R.id.ifb);

        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ArrayList<String> listSomething = new ArrayList<String>();
                listSomething.add(0,"exercise1");
                listSomething.add(1, "exercise2");
                listSomething.add(2, "exercise3");


                insertFragmentOnClick("SomeName", listSomething);
            }
        });

    }

    private void insertFragmentOnClick(String name, ArrayList<String> exerciseNames){

        Log.d("CACALAU", name);
        Log.d("LISTA", exerciseNames.get(0));

        Exercise exercise = Exercise.newInstance(name, exerciseNames);

        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.internalLinearLayout, exercise);
        fragmentTransaction.commit();

    }

    private void insertFragments(View view){

        List<Exercise> someExerciseList = Exercise.newInstance(120);

        for(int index = 0; index<someExerciseList.size();index++){

            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Exercise exerciseFragment = someExerciseList.get(index);
            exerciseFragment.getView().setPadding(10,10,10,10);
            if(index % 2 != 1){
                exerciseFragment.getView().setBackgroundColor(2);
            }
            else{
                exerciseFragment.getView().setBackgroundColor(3);
            }
            fragmentTransaction.add(R.id.internalLinearLayout,someExerciseList.get(index));

            fragmentTransaction.commit();

        }
    }
    private void initializeFloatingButton(View view){
        FloatingActionButton insert_floating_button;
        insert_floating_button = (FloatingActionButton) view.findViewById(R.id.insert_floating_button);
        insert_floating_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingButtonActions();
            }
        });
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

//    private void setCentralText(View view){
//        textView = (TextView) view.findViewById(R.id.textView);
//        textView.setText(date);
//    }

    private void floatingButtonActions(){

        Log.d("DynamicFragment","floating button actions");
        AccesRemoteDatabase start_background_thread = new AccesRemoteDatabase();
        start_background_thread.execute(Constants.PHP_SCRIPT_LOCAL_HOST);
        Log.d("FBActions","after");

//        Intent intent = new Intent(getActivity(), CalendarArea.class);
//        startActivity(intent);



    }

    public void insertFragment(){

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();



    }

}




