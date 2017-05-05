package drivetag.drivetag.com.driveceos.presentation_layer.more;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import drivetag.drivetag.com.driveceos.R;
import drivetag.drivetag.com.driveceos.presentation_layer.on_boarding.rows.TableRow;

/**
 * Created by artem on 4/4/17.
 */

public class MyAccountHeaderRow extends TableRow {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View myAccountView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_account_header, parent, false);
        MyAccountHeaderHolder myAccountHeaderHolder = new MyAccountHeaderHolder(myAccountView);
        return myAccountHeaderHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        
    }


    /**
     * ViewHolder class for "my_account_header_item" layout.
     */
    private static class MyAccountHeaderHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;

        private MyAccountHeaderHolder(View itemView) {
            super(itemView);

            titleTextView = (TextView) itemView.findViewById(R.id.title_text_view);
        }
    }
}
