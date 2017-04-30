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
 * Created by artem on 4/3/17.
 */

public class MyTeammatesRow extends TableRow {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View teammatesView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_teammates_item, parent, false);
        MyTeammatesHolder myTeammatesHolder = new MyTeammatesHolder(teammatesView);
        return myTeammatesHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyTeammatesHolder myTeammatesViewHolder = (MyTeammatesHolder)holder;
        myTeammatesViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectionHandler != null) {
                    selectionHandler.didSelectRow();
                }
            }
        });
    }


    /**
     * ViewHolder class for "my_teammates_item" layout.
     */
    private static class MyTeammatesHolder extends RecyclerView.ViewHolder {
        private ImageView teammatesImageView;
        private TextView teammatesTextView;

        private MyTeammatesHolder(View itemView) {
            super(itemView);

            teammatesImageView = (ImageView) itemView.findViewById(R.id.my_teammates_image_view);
            teammatesTextView = (TextView) itemView.findViewById(R.id.title_text_view);
        }
    }
}
