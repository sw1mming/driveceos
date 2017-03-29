package drivetag.drivetag.com.driveceos.presentation_layer.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import drivetag.drivetag.com.driveceos.R;

/**
 * Created by sergeymelnik on 2017-03-28.
 */

public class SuggectionsAdapter extends RecyclerView.Adapter<SuggectionsAdapter.ViewHolder> {

    public List<String> suggestionsArray;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.suggestion_item, parent, false);
        SuggectionsAdapter.ViewHolder viewHolder = new SuggectionsAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String suggestion = suggestionsArray.get(position);
        holder.titleTextView.setText(suggestion);
    }

    @Override
    public int getItemCount() {
        if (suggestionsArray != null) {
            return suggestionsArray.size();
        } else {
            return 0;
        }
    }

    public void setSuggestionsArray(List<String> suggestionsArray) {
        this.suggestionsArray = new ArrayList<>(suggestionsArray);
    }

    /**
     * ViewHolder class for "suggestion_item" layout.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            titleTextView = (TextView) itemView.findViewById(R.id.suggestion_title_text_view);
        }
    }

}
