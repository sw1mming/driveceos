package drivetag.drivetag.com.driveceos.presentation_layer.more;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import drivetag.drivetag.com.driveceos.R;
import drivetag.drivetag.com.driveceos.presentation_layer.on_boarding.rows.TableRow;

/**
 * Created by artem on 4/4/17.
 */

public class SocialSwitchRow extends TableRow {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View socialSwitchView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_social_switch, parent, false);
        return new SocialSwitchHolder(socialSwitchView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final SocialSwitchHolder socialSwitchHolder = (SocialSwitchHolder)holder;
        socialSwitchHolder.titleTextView.setText(this.title);

        socialSwitchHolder.switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (socialSwitchHolder.stateTextView.getText().equals("OFF")) {
                    socialSwitchHolder.stateTextView.setText("ON");
                } else {
                    socialSwitchHolder.stateTextView.setText("OFF");
                }

                if (selectionHandler != null) {
                    selectionHandler.didSelectRow();
                }
            }
        });
    }


    /**
     * ViewHolder class for "social_switch_item" layout.
     */
    private static class SocialSwitchHolder extends RecyclerView.ViewHolder {
        ImageView socialImageView;
        TextView titleTextView;
        TextView stateTextView;
        Switch switchButton;


        private SocialSwitchHolder(View itemView) {
            super(itemView);

            socialImageView = (ImageView) itemView.findViewById(R.id.social_icon_image_view);
            titleTextView = (TextView) itemView.findViewById(R.id.title_text_view);
            stateTextView = (TextView) itemView.findViewById(R.id.state_text_view);
            switchButton = (Switch) itemView.findViewById(R.id.switch_button);
        }
    }
}
