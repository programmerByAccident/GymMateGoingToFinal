package adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.simpleideas.gymmate.Constants;
import com.simpleideas.gymmate.InsertActivity;
import com.simpleideas.gymmate.R;

import java.util.ArrayList;

/**
 * Created by Geprge on 12/23/2016.
 */

public class ExerciseRecyclerViewAdapter extends RecyclerView.Adapter<ExerciseRecyclerViewAdapter.ExerciseViewHolder> {


    ArrayList<String> muscleGroups;
    String muscleName;
    String date;
    Context context;

    public ExerciseRecyclerViewAdapter(Context context, ArrayList muscleGroups, String muscleName, String date){

        this.muscleGroups = muscleGroups;
        this.muscleName = muscleName;
        this.date = date;
        this.context = context;

    }


    @Override
    public ExerciseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.muscle_item, parent, false);

        ExerciseViewHolder exerciseViewHolder = new ExerciseViewHolder(view);



        return exerciseViewHolder;
    }

    @Override
    public void onBindViewHolder(ExerciseViewHolder holder, int position) {

        holder.exerciseName.setText(muscleGroups.get(position));

    }

    @Override
    public int getItemCount() {
        return this.muscleGroups.size();
    }

    class ExerciseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView exerciseName;

        public ExerciseViewHolder(View itemView) {
            super(itemView);
            exerciseName = (TextView) itemView.findViewById(R.id.muscleItem);
            exerciseName.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            switch (view.getId()){

                case R.id.muscleItem:

                    Intent intent = new Intent(context, InsertActivity.class);

                    intent.putExtra(Constants.EXERCISE_NAME, exerciseName.getText().toString());
                    intent.putExtra(Constants.MUSCLE_NAME, muscleName);
                    //intent.putExtra("Difference", difference);
                    intent.putExtra("date", date);
                    context.startActivity(intent);

            }

        }
    }
}
