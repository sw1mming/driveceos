package drivetag.drivetag.com.driveceos.presentation_layer.user_profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import org.w3c.dom.Text;

import drivetag.drivetag.com.driveceos.BaseActivity;
import drivetag.drivetag.com.driveceos.R;
import drivetag.drivetag.com.driveceos.presentation_layer.adapters.UserProfileAdapter;
import drivetag.drivetag.com.driveceos.presentation_layer.user_profile.suggestions.SuggestionsActivity;

/**
 * Created by sergeymelnik on 2017-03-27.
 */

public class UserProfileActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        setupRecyclerView();
        setupActionBar();
    }

    private void setupRecyclerView() {
         LinearLayoutManager manager = new LinearLayoutManager(this);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setAdapter(setupUserProfileAdapter());
        recyclerView.setLayoutManager(manager);
    }

    private UserProfileAdapter setupUserProfileAdapter() {
        UserProfileAdapter adapter = new UserProfileAdapter();

        adapter.handler = new UserProfileAdapter.UserProfileAdapterHandler() {
            @Override
            public void didSelectCoverPhotoCompletionHandler(UserProfileAdapter adapter) {

            }

            @Override
            public void didSelectProfilePhotoCompletionHandler(UserProfileAdapter adapter) {

            }

            @Override
            public void didSelectWhatDrivesYouCompletionHandler(UserProfileAdapter adapter) {
                // handle new screen
//                Intent intent = new Intent(getApplicationContext(), SuggestionsActivity.class);
//                getApplicationContext().startActivity(intent);
            }

            @Override
            public void didSelectMyPageCompletionHandler(UserProfileAdapter adapter) {

            }
        };

        return adapter;
    }

    private UserProfileActivity getUserProfileActivity() {
        return this;
    }

    private void setupActionBar() {
        TextView titleActionBarTextView = (TextView) findViewById(R.id.action_bar_title_text_view);
        titleActionBarTextView.setText("User Profile");
    }
}
