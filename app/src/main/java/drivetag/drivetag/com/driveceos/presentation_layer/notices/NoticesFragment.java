package drivetag.drivetag.com.driveceos.presentation_layer.notices;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import drivetag.drivetag.com.driveceos.R;

public class NoticesFragment extends Fragment {

    public NoticesFragment() {
    }

    public static NoticesFragment newInstance(String userId) {
        NoticesFragment viewPreferenceFragment = new NoticesFragment();
        Bundle bundle = new Bundle();
        bundle.putString("userId", userId);
        viewPreferenceFragment.setArguments(bundle);
        return viewPreferenceFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notices, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final String userId = getArguments().getString("userId");
        Toast.makeText(getActivity(), userId, Toast.LENGTH_SHORT).show();
    }
}
