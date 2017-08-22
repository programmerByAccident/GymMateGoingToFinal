package adapters;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.simpleideas.gymmate.DatabaseHelper;
import com.simpleideas.gymmate.ExerciseTagInformation;
import com.simpleideas.gymmate.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Geprge on 12/18/2016.
 */

public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<CustomRecyclerViewAdapter.CustomViewHolder>{

    private List<ExerciseTagInformation> exercises = Collections.emptyList();

    public List<ExerciseTagInformation> getExercises() {
        return exercises;
    }

    private LayoutInflater inflater;
    DatabaseHelper databaseHelper;
    private String dateString;
    Activity activity;

    public CustomRecyclerViewAdapter(Activity activity, ArrayList<ExerciseTagInformation> exercises, String dateString){

        this.exercises = exercises;
        this.dateString = dateString;
        databaseHelper = new DatabaseHelper(activity.getBaseContext());
        this.activity = activity;

    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent,false);

        CustomViewHolder customViewHolder = new CustomViewHolder(view);



        return customViewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {

        holder.exercicseName.setText(exercises.get(position).getTAG());

        String something = databaseHelper.getColorBaseOnMuscleName(databaseHelper.getMuscleName(exercises.get(position).getTAG()));

       // holder.exercicseName.setBackgroundColor(databaseHelper.getColorBaseOnMuscleName(databaseHelper.getMuscleName(exercises.get(position).getTAG())));
        ArrayList<String> reps = exercises.get(position).getRepetition();
        ArrayList<String> weight = exercises.get(position).getWeight();
        StringBuilder stringBuilderReps = new StringBuilder();
        StringBuilder stringBuilderWeight = new StringBuilder();
        for(int index=0; index < reps.size(); index++){
            if(index <= 4) {
                stringBuilderReps.append(reps.get(index) + " reps" + "\n");
                stringBuilderWeight.append(weight.get(index)+ " kg" + "\n");
            }
            else{break;}


        }

        holder.repetitions.setText(stringBuilderReps);
        holder.weight.setText(stringBuilderWeight);

    }

    @Override
    public int getItemCount() {
        return this.exercises.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        private TextView exercicseName;
        private TextView weight;
        private TextView repetitions;
        private ImageButton imageButton;
        private CardView cardView;

        public CustomViewHolder(View itemView) {
            super(itemView);

            exercicseName = (TextView) itemView.findViewById(R.id.exerciseNameViewWTF);
            repetitions = (TextView) itemView.findViewById(R.id.exerciseNameViewWTFReps);
            weight = (TextView) itemView.findViewById(R.id.exerciseNameViewWTFWeights);
            cardView = (CardView) itemView.findViewById(R.id.card);
            cardView.setOnClickListener(this);
            //imageButton = (ImageButton) itemView.findViewById(R.id.deleteButton);
            //imageButton.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            switch(view.getId()){
//                case R.id.card:
//
//                    Intent intent = new Intent(activity.getApplication(), InsertActivity.class);
//
//                    Bundle bundle = new Bundle();
//                    int position = getAdapterPosition();
//
//                    bundle.putString("");
//
//                    activity.startActivity(intent);

//                case R.id.deleteButton:
//                    exercises.remove(getAdapterPosition());
//                    //databaseHelper.deleteRecord(dateString, exercicseName.getText().toString(), repetitions.getText().toString(), weight.getText().toString());
//                    notifyItemRemoved(getAdapterPosition());

            }
        }
    }
}
