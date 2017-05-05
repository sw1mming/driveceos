package drivetag.drivetag.com.driveceos.presentation_layer.picker_fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.webianks.library.scroll_choice.ScrollChoice;

import java.util.List;

import drivetag.drivetag.com.driveceos.R;

/**
 * Created by sergeymelnik.
 */

public class PickerFragment extends Fragment {

    public PickerFragmentHandler handler;

    private ScrollChoice scrollChoice;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_picker, container);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getView() != null) {
            scrollChoice =  (ScrollChoice) getView().findViewById(R.id.scroll_choice);
        }

        scrollChoice.setOnItemSelectedListener(new ScrollChoice.OnItemSelectedListener() {
            @Override
            public void onItemSelected(ScrollChoice scrollChoice, int position, String name) {
                if (handler != null) {
                    handler.didSelectItemString(name);
                }
            }
        });
    }

    public void setContentArray(List<String> contentArray) {
        if (scrollChoice != null) {
            int initialSelectedItem = 1;
            List<String> dataArray = contentArray;
            scrollChoice.addItems(dataArray, initialSelectedItem);
        }
    }

    public interface PickerFragmentHandler {
        void didSelectItemString(String itemString);
    }
}
