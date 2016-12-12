package com.simpleideas.gymmate;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import java.util.zip.Inflater;

/**
 * Created by programmerByAccident on 8/28/2016.
 */

public class DynamicFragment extends android.support.v4.app.Fragment {

    public DataSenderBetweenFragments senderBetweenFragments;
    TextView textView;
    private String date;
    private int difference;
    private static Context context;
    private static ExerciseIODatabase exerciseIODatabase;
    DatabaseManager databaseManager;
    private boolean triggerOfExistance;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();

        exerciseIODatabase = new ExerciseIODatabase(context);
        makeConnectionToTheInterface();

        date = getArguments().getString(Constants.DATE);
        triggerOfExistance = getArguments().getBoolean(Constants.TRIGGER_EXISTANCE);
        difference = getArguments().getInt("Difference");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        exerciseIODatabase = new ExerciseIODatabase(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;

        if(triggerOfExistance == true){
            //insertFragments(view);
            //setCentralText(view);
            noExerciseRecordForThatDay(view,inflater, container, savedInstanceState);
        }
        else{

            DatabaseManager databaseManager = new DatabaseManager(getActivity().getApplicationContext());

            view = inflater.inflate(R.layout.layout_empty, container, false);
            createBehaviourForTriggerOfExistanceFalse(view, difference);
            return view;
        }


        return view;
        }

    private void noExerciseRecordForThatDay(View view, LayoutInflater inflater, ViewGroup container, Bundle savedState){

        view = inflater.inflate(R.layout.dynamic_fragment_with_listview, container, false);
        ListView listView = (ListView) view.findViewById(R.id.listViewWithItems);
        //initializeFloatingButton(view);
        //listenerInsertFragmentClick(view);

        ArrayList<ExerciseTemplate> adapterValues = new ArrayList<>();

        LetsMakeAnAdapter adapter = new LetsMakeAnAdapter(getActivity(), adapterValues);

        listView.setAdapter(adapter);

    }

    private void createBehaviourForFABWhenEmptyScreen(FloatingActionButton floatingActionButton){

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CertainMuscleListView.class);
                startActivity(intent);
            }
        });

    }

    private void exerciseRecordFoundForThatDay(View view, LayoutInflater layoutInflater, ViewGroup container, Bundle savedState){

        DatabaseManager databaseManager = new DatabaseManager(getActivity().getApplicationContext());

        SQLiteDatabase sqLiteDatabase = databaseManager.getReadableDatabase();



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
        intent.putExtra("Difference", difference);
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


    public static DynamicFragment createInstanceBasedOnDifferenceBetweenCurrentDateAndPosition(int difference, Context context){

        Bundle arguments = new Bundle();
        String str;
        DynamicFragment dynamicFragment = new DynamicFragment();
        DateTime dateTime = DateTime.now(DateTimeZone.forTimeZone(TimeZone.getDefault()));
        DateTime current_date = dateTime.plusDays(difference);

        if(difference == 0)
            str = "TODAY";
        else if(difference == 1)
            str = "TOMORROW";
        else if(difference == -1)
            str = "YESTERDAY";
        else {
            DateTimeFormatter fmt = DateTimeFormat.forPattern("E - d - MMMM - yyyy");
            str = fmt.print(current_date);
        }
        exerciseIODatabase = new ExerciseIODatabase(context);
        arguments.putString(Constants.DATE, str);
        arguments.putInt("Difference", difference);

        if(exerciseIODatabase.checkIfExerciseExists(difference)){

            arguments.putBoolean(Constants.TRIGGER_EXISTANCE, true);

        }
        else{arguments.putBoolean(Constants.TRIGGER_EXISTANCE, false);}


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




