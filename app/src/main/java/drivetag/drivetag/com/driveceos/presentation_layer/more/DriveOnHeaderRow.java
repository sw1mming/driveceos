package drivetag.drivetag.com.driveceos.presentation_layer.more;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import drivetag.drivetag.com.driveceos.R;
import drivetag.drivetag.com.driveceos.presentation_layer.on_boarding.rows.TableRow;

/**
 * Created by artem on 4/4/17.
 */

public class DriveOnHeaderRow extends TableRow {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View driveOnView = LayoutInflater.from(parent.getContext()).inflate(R.layout.drive_on_header_item, parent, false);
        return new DriveOnHeaderHolder(driveOnView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    }


    /**
     * ViewHolder class for "drive_on_header_item" layout.
     */
    private static class DriveOnHeaderHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private ImageView imageView;

        private DriveOnHeaderHolder(View itemView) {
            super(itemView);

            titleTextView = (TextView) itemView.findViewById(R.id.title_text_view);
            imageView = (ImageView) itemView.findViewById(R.id.drive_image_view);
        }
    }
}
