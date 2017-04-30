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

    ColorAdapter(ArrayList<String> inputStringList) {
        colors= new ArrayList<>(inputStringList);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.color_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String color = colors.get(position);
        holder.titleTextView.setText(color);
        holder.colorImageView.setBackgroundColor(Color.parseColor(color));
    }

    @Override
    public int getItemCount() {
        return colors.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        ImageView colorImageView;

        ViewHolder(View v) {
            super(v);
            titleTextView = (TextView) v.findViewById(R.id.title_text_view);
            colorImageView = (ImageView) v.findViewById(R.id.color_image_view);
        }
    }
}
