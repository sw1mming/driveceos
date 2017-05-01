package drivetag.drivetag.com.driveceos.presentation_layer.picker_fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import java.util.ArrayList;

import drivetag.drivetag.com.driveceos.R;

public class ColorDialogFragment extends DialogFragment {

    private static String ARG = "COLOR_LIST";
    private ColorClickListener colorClickListener;

    public static ColorDialogFragment getInstance(ArrayList<String> colors) {
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(ARG, colors);
        ColorDialogFragment colorDialogFragment = new ColorDialogFragment();
        colorDialogFragment.setArguments(bundle);
        return colorDialogFragment;
    }

    public void setListener(ColorClickListener pColorClickListener) {
        colorClickListener = pColorClickListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_color_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<String> colors =  getArguments().getStringArrayList(ARG);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.info_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        ColorAdapter infoAdapter = new ColorAdapter(colors, colorClickListener);
        recyclerView.setAdapter(infoAdapter);
    }

    /* lifecycle */

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = dialog.getWindow();
        if (window == null)
            return dialog;
        window.setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        window.setGravity(Gravity.CENTER);
        return dialog;
    }

    public interface ColorClickListener {
        void onColorClick(String color);
    }
}
