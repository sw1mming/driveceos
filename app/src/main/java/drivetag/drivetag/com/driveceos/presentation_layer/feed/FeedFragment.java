package drivetag.drivetag.com.driveceos.presentation_layer.feed;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import drivetag.drivetag.com.driveceos.R;
import drivetag.drivetag.com.driveceos.data_layer.UserStorage;
import drivetag.drivetag.com.driveceos.data_layer.data_sources.DataSource;
import drivetag.drivetag.com.driveceos.data_layer.data_sources.FeedDataSource;

public class FeedFragment extends Fragment {

    private FeedDataSource feedDataSource;

    private UserStorage userStorage;

    public FeedFragment() {
    }

    public void setEventsDataSource(FeedDataSource feedDataSource) {
        this.feedDataSource = feedDataSource;
        this.feedDataSource.addListener(new DataSource.DataSourceListener() {
            @Override
            public void notifyWillLoadItems() {
            }

            @Override
            public void notifyDidLoadItems(List<List> sections) {
//                adapter.notifyDataSetChanged();
            }

            @Override
            public void notifyDidLoadItemsWithError(String error) {
            }
        });

        this.feedDataSource.shouldShowWelcomeMessageHandler = new FeedDataSource.ShouldShowWelcomeMessageHandler() {
            @Override
            public Boolean completionHandler(FeedDataSource dataSource) {
                return true;
            }
        };
    }

    public static FeedFragment newInstance(String userId) {
        FeedFragment viewPreferenceFragment = new FeedFragment();
        Bundle bundle = new Bundle();
        bundle.putString("userId", userId);
        viewPreferenceFragment.setArguments(bundle);
        return viewPreferenceFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final String userId = getArguments().getString("userId");
        Toast.makeText(getActivity(), userId, Toast.LENGTH_SHORT).show();

    }

//    public void feedDataSourceShouldShowWelcomeMessage(FeedDataSource.ShouldShowWelcomeMessage handler) {
//        handler.completionHandler(userStorage.shouldShowWelcomeMessage);
//    }
}
