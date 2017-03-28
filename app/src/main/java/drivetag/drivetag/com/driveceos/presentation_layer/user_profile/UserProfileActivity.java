package drivetag.drivetag.com.driveceos.presentation_layer.user_profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import org.w3c.dom.Text;

import drivetag.drivetag.com.driveceos.BaseActivity;
import drivetag.drivetag.com.driveceos.R;
import drivetag.drivetag.com.driveceos.presentation_layer.adapters.UserProfileAdapter;

/**
 * Created by sergeymelnik on 2017-03-27.
 */

public class UserProfileActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        UserProfileAdapter adapter = new UserProfileAdapter();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);

        setupActionBar();
    }

    private void setupActionBar() {
        TextView titleActionBarTextView = (TextView) findViewById(R.id.action_bar_title_text_view);
        titleActionBarTextView.setText("User Profile");
    }
}
