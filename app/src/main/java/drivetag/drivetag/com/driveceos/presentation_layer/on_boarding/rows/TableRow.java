package drivetag.drivetag.com.driveceos.presentation_layer.on_boarding.rows;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by sergeymelnik on 2017-03-30.
 */

public class TableRow {

    public String title;

    public String placeholder;

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    public interface TableRowHandler {
        void didSelectRow();
    }
}
