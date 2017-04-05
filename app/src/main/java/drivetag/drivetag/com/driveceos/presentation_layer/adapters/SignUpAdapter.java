package drivetag.drivetag.com.driveceos.presentation_layer.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import drivetag.drivetag.com.driveceos.presentation_layer.on_boarding.rows.EditTextRow;
import drivetag.drivetag.com.driveceos.presentation_layer.on_boarding.rows.TableRow;

/**
 * Created by sergeymelnik on 2017-03-28.
 */

public class SignUpAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public List<TableRow> rows = new ArrayList<>();

    @Override
    public int getItemViewType(int position) {
        System.out.println();
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return rows.get(viewType).onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TableRow row = rows.get(position);
        row.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return rows.size();
    }
}
