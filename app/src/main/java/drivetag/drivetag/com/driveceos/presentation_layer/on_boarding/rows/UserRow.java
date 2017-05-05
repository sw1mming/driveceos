package drivetag.drivetag.com.driveceos.presentation_layer.on_boarding.rows;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import drivetag.drivetag.com.driveceos.R;

/**
 * Created by artem.
 */
public class UserRow extends TableRow {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View userView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(userView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        UserViewHolder userViewHolder = (UserViewHolder)holder;

        userViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectionHandler != null) {
                    selectionHandler.didSelectRow();
                }
            }
        });
    }


    /**
     * ViewHolder class for "user_item" layout.
     */
    private static class UserViewHolder extends RecyclerView.ViewHolder {
        private ImageView userImageView;
        private TextView userNameTextView;
        private TextView positionTextView;
        private ImageView driveIconImageView;

        private UserViewHolder(View itemView) {
            super(itemView);

            userImageView = (ImageView) itemView.findViewById(R.id.user_avatar_image_view);
            userNameTextView = (TextView) itemView.findViewById(R.id.user_name_text_view);
            positionTextView = (TextView) itemView.findViewById(R.id.position_text_view);
            driveIconImageView = (ImageView) itemView.findViewById(R.id.drive_icon_image_view);
        }
    }
}



