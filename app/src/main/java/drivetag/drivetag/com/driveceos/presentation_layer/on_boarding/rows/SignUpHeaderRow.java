package drivetag.drivetag.com.driveceos.presentation_layer.on_boarding.rows;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import drivetag.drivetag.com.driveceos.R;

/**
 * Created by sergeymelnik on 2017-03-30.
 */

public class SignUpHeaderRow extends TableRow {

    public TableRowHandler handler;

    public Bitmap userImageBitmap;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View editTextView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sign_up_header_item, parent, false);
        SignUpHeaderViewHolder editTextViewHolder = new SignUpHeaderViewHolder(editTextView);

        return editTextViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SignUpHeaderViewHolder editTextViewHolder = (SignUpHeaderViewHolder)holder;

        editTextViewHolder.titleTextView.setText(title);

        if (userImageBitmap != null) {
            editTextViewHolder.userProfileImageView.setImageBitmap(userImageBitmap);
            editTextViewHolder.titleTextView.setVisibility(TextView.INVISIBLE);
        } else {
            editTextViewHolder.userProfileImageView.setImageBitmap(null);
            editTextViewHolder.titleTextView.setVisibility(TextView.VISIBLE);
        }

        editTextViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (handler != null) {
                    handler.didSelectRow();
                }
            }
        });
    }

    /**
     * ViewHolder class for "cover_photo_item" layout.
     */
    private static class SignUpHeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private ImageView userProfileImageView;

        private SignUpHeaderViewHolder(View itemView) {
            super(itemView);

            titleTextView = (TextView) itemView.findViewById(R.id.title_text_view);
            userProfileImageView = (ImageView) itemView.findViewById(R.id.user_image_view);
        }
    }
}
