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

public class ColorRow extends TableRow {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View colorView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_color, parent, false);
        return new ColorHolder(colorView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ColorHolder colorHolder = (ColorHolder)holder;
        colorHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectionHandler != null) {
                    selectionHandler.didSelectRow();
                }
            }
        });
    }


    /**
     * ViewHolder class for "color_item" layout.
     */
    private static class ColorHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private ImageView colorImageView;
        private ImageView driveTagImageView;

        private ColorHolder(View itemView) {
            super(itemView);

            titleTextView = (TextView) itemView.findViewById(R.id.title_text_view);
            colorImageView = (ImageView) itemView.findViewById(R.id.color_image_view);
            driveTagImageView = (ImageView) itemView.findViewById(R.id.drivetag_icon_image_view);
        }
    }
}
