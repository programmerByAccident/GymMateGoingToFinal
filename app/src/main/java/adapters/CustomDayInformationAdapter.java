package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.simpleideas.gymmate.ExerciseTemplate;
import com.simpleideas.gymmate.R;

import java.util.ArrayList;

/**
 * Created by Geprge on 4/15/2017.
 */

public class CustomDayInformationAdapter extends RecyclerView.Adapter<CustomDayInformationAdapter.CustomDayViewHolder> {


    private ArrayList<ExerciseTemplate> exerciseTemplateArrayList;
    private Context context;

    public CustomDayInformationAdapter(ArrayList<ExerciseTemplate> exerciseTemplateArrayList, Context context){

        this.exerciseTemplateArrayList = exerciseTemplateArrayList;
        this.context = context;
    }

    @Override
    public CustomDayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_day_dialog, parent, false);

        CustomDayViewHolder holder = new CustomDayViewHolder(view);

        return holder;

    }

    @Override
    public void onBindViewHolder(CustomDayViewHolder holder, int position) {

        holder.informationRegardingExercise.setText(exerciseTemplateArrayList.get(position).getExerciseName().toString() + " " + exerciseTemplateArrayList.get(position).getWeight() + "kg" +" " + String.valueOf(exerciseTemplateArrayList.get(position).getRepetition()) + "reps");

    }

    @Override
    public int getItemCount() {
        return exerciseTemplateArrayList.size();
    }

    public class CustomDayViewHolder extends RecyclerView.ViewHolder{


        private TextView informationRegardingExercise;

        public CustomDayViewHolder(View itemView) {
            super(itemView);

            informationRegardingExercise = (TextView) itemView.findViewById(R.id.exerciseInformation);

        }
    }
}
