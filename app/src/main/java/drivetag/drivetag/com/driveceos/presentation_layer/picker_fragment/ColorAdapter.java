package drivetag.drivetag.com.driveceos.presentation_layer.picker_fragment;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import drivetag.drivetag.com.driveceos.R;

class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ViewHolder> {

    private ArrayList<String> colors;
    private ColorDialogFragment.ColorClickListener colorClickListener;

    ColorAdapter(ArrayList<String> inputStringList, ColorDialogFragment.ColorClickListener pColorClickListener) {
        colors= new ArrayList<>(inputStringList);
        this.colorClickListener = pColorClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_color, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String color = colors.get(position);
        holder.bind(color);
    }

    @Override
    public int getItemCount() {
        return colors.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private ImageView colorImageView;

        ViewHolder(View v) {
            super(v);
            titleTextView = (TextView) v.findViewById(R.id.title_text_view);
            colorImageView = (ImageView) v.findViewById(R.id.color_image_view);
        }

        void bind(String color) {
            titleTextView.setText(color);
            colorImageView.setBackgroundColor(Color.parseColor(color));
            itemView.setTag(color);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Object tag = v.getTag();
                    if(tag != null) {
                        colorClickListener.onColorClick(tag.toString());
                    }
                }
            });
        }
    }
}
