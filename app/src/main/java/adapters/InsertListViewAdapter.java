package adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.simpleideas.gymmate.DatabaseHelper;
import com.simpleideas.gymmate.ExerciseTemplate;
import com.simpleideas.gymmate.InsertActivity;
import com.simpleideas.gymmate.R;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Geprge on 3/19/2017.
 */

public class InsertListViewAdapter extends RecyclerView.Adapter<InsertListViewAdapter.HolderForInsertActivity> {

    private ArrayList<ExerciseTemplate> exerciseInfo;
    private DatabaseHelper databaseHelper;


    public void setExerciseInfo(ArrayList<ExerciseTemplate> exerciseInfo) {
        this.exerciseInfo = exerciseInfo;
    }

    private Context context;
    private LayoutInflater inflater;

    public InsertListViewAdapter(Context context, ArrayList<ExerciseTemplate> exerciseInfo){

        this.exerciseInfo = exerciseInfo;
        this.context = context;

    }

    @Override
    public HolderForInsertActivity onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_element_insert_activity, parent, false);
        databaseHelper = new DatabaseHelper(context);
        HolderForInsertActivity holderForInsertActivity = new HolderForInsertActivity(view);

        return holderForInsertActivity;
    }

    @Override
    public void onBindViewHolder(HolderForInsertActivity holder, int position) {

        holder.reps.setText(" " + String.valueOf(exerciseInfo.get(position).getRepetition()+" reps"));
        holder.weight.setText(" "+String.valueOf(exerciseInfo.get(position).getWeight()+" kg"));
        holder.position.setText(String.valueOf(position+1));
        InsertActivity insertActivity = new InsertActivity();
        String muscle = insertActivity.getMuscle_name();
        //holder.winner.setColorFilter(R.color.colorAccent);
    }


    @Override
    public int getItemCount() {
        return exerciseInfo.size();
    }

    public class HolderForInsertActivity extends RecyclerView.ViewHolder{

        TextView reps,weight, position;
        ImageView winner;

        public HolderForInsertActivity(View itemView) {
            super(itemView);
            reps = (TextView) itemView.findViewById(R.id.repetitions);
            weight = (TextView) itemView.findViewById(R.id.weight);
            position = (TextView)itemView.findViewById(R.id.position);
            //date = (TextView) itemView.findViewById(R.id.date);
            //winner = (ImageView) itemView.findViewById(R.id.smiley);
        }
    }
}
