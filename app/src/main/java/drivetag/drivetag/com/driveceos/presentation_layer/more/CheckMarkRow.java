package drivetag.drivetag.com.driveceos.presentation_layer.more;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import drivetag.drivetag.com.driveceos.R;
import drivetag.drivetag.com.driveceos.data_layer.requests.PublicPostToMeRequest;
import drivetag.drivetag.com.driveceos.data_layer.requests.ServerRequest;
import drivetag.drivetag.com.driveceos.presentation_layer.on_boarding.rows.TableRow;

/**
 * Created by artem on 4/3/17.
 */

public class CheckMarkRow extends TableRow {

    Boolean state;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View checkMarkView = LayoutInflater.from(parent.getContext()).inflate(R.layout.check_mark_item, parent, false);
        CheckMarkHolder checkMarkHolder = new CheckMarkHolder(checkMarkView);
        return checkMarkHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final CheckMarkHolder checkMarkHolder = (CheckMarkHolder)holder;
        checkMarkHolder.titleTextView.setText(title);

        // todo: check to "strongSelf.userStorage.allowPublicPostsToMe"
        if (state != null) {
            checkMarkHolder.checkMarkImageView.setVisibility(View.VISIBLE);
        } else {
            checkMarkHolder.checkMarkImageView.setVisibility(View.INVISIBLE);
        }

        checkMarkHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (state == null) {
                    checkMarkHolder.checkMarkImageView.setVisibility(View.VISIBLE);
                } else {
                    checkMarkHolder.checkMarkImageView.setVisibility(View.INVISIBLE);
                }

                if (selectionHandler != null) {
                    selectionHandler.didSelectRow();
                }
            }
        });
    }


    /**
     * ViewHolder class for "check_mark_item" layout.
     */
    private static class CheckMarkHolder extends RecyclerView.ViewHolder {
        private ImageView checkMarkImageView;
        private TextView titleTextView;

        private CheckMarkHolder(View itemView) {
            super(itemView);

            checkMarkImageView = (ImageView) itemView.findViewById(R.id.check_mark_image_view);
            titleTextView = (TextView) itemView.findViewById(R.id.title_text_view);
        }
    }
}
