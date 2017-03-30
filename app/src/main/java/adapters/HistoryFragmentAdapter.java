package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.simpleideas.gymmate.InsertActivity;
import com.simpleideas.gymmate.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Geprge on 3/25/2017.
 */

public class HistoryFragmentAdapter extends RecyclerView.Adapter <HistoryFragmentAdapter.HistoryFragmentHolder>{

    HashMap<Integer, ArrayList<String>> informationMap;

    public void setInformationMap(HashMap<Integer, ArrayList<String>> informationMap) {
        this.informationMap = informationMap;
    }

    public HashMap<Integer, ArrayList<String>> getInformationMap() {
        return informationMap;
    }

    Context context;
    private HashMap<Integer, String> monthMap;

    public HistoryFragmentAdapter(Context context, HashMap<Integer, ArrayList<String>> informationMap, HashMap<Integer, String> monthMap) {

        this.informationMap = informationMap;
        this.context = context;
        this.monthMap = monthMap;

    }

    @Override
    public HistoryFragmentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_recycler_view_item, parent,false);

        HistoryFragmentAdapter.HistoryFragmentHolder historyFragmentHolder = new HistoryFragmentAdapter.HistoryFragmentHolder(view);

        return historyFragmentHolder;
    }

    @Override
    public void onBindViewHolder(HistoryFragmentHolder holder, int position) {

        StringBuilder days = new StringBuilder();

        for (String dayRecord:
             informationMap.get(position)) {
            days.append(dayRecord + "\n");
        }
        holder.content.setText(days.toString());
        holder.title.setText(monthMap.get(position) + " - " + String.valueOf(informationMap.get(position).size()) + " workouts");


    }

    @Override
    public int getItemCount() {
        return informationMap.size();
    }

    public class HistoryFragmentHolder extends RecyclerView.ViewHolder {

        private TextView title, content;

        public HistoryFragmentHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.titleHistory);
            content = (TextView) itemView.findViewById(R.id.contentHistory);
        }
    }
}
