package drivetag.drivetag.com.driveceos.presentation_layer.linked_in_login_fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import drivetag.drivetag.com.driveceos.R;
import drivetag.drivetag.com.driveceos.presentation_layer.BaseActivity;
import drivetag.drivetag.com.driveceos.presentation_layer.feed.FeedFragment;

/**
 * Created by sergeymelnik on 2017-04-14.
 */

public class LinkedInLoginFragment extends Fragment {

    public LinkedInLoginFragment() {
    }

    public static LinkedInLoginFragment newInstance(String userId) {
        LinkedInLoginFragment viewPreferenceFragment = new LinkedInLoginFragment();
        Bundle bundle = new Bundle();
        bundle.putString("userId", userId);
        viewPreferenceFragment.setArguments(bundle);
        return viewPreferenceFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_linked_in_login, container);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
