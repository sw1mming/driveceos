package drivetag.drivetag.com.driveceos.presentation_layer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import drivetag.drivetag.com.driveceos.R;
import drivetag.drivetag.com.driveceos.presentation_layer.feed.FeedFragment;
import drivetag.drivetag.com.driveceos.presentation_layer.mdt.MDTFragment;
import drivetag.drivetag.com.driveceos.presentation_layer.linked_in_login_fragment.LinkedInLoginFragment;
import drivetag.drivetag.com.driveceos.presentation_layer.more.MoreFragment;
import drivetag.drivetag.com.driveceos.presentation_layer.notices.NoticesFragment;
import drivetag.drivetag.com.driveceos.presentation_layer.teamups.TeamUpsFragment;

public class MainActivity extends BaseActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_feed:
                    replaceFragment(FeedFragment.newInstance("Navigate to Feed screen"));
                    return true;
                case R.id.navigation_teamups:
                    replaceFragment(TeamUpsFragment.newInstance("Navigate to TeamUps screen"));
                    return true;
                case R.id.navigation_notices:
                    replaceFragment(NoticesFragment.newInstance("Navigate to Notice screen"));
                    return true;
                case R.id.navigation_mdt:
                    replaceFragment(MDTFragment.newInstance("Navigate to MDT screen"));
                    return true;
                case R.id.navigation_more:
                    replaceFragment(MoreFragment.newInstance("Navigate to More screen"));
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_Red);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        replaceFragment(LinkedInLoginFragment.newInstance("Navigate to Linked screen"));
//        replaceFragment(FeedFragment.newInstance("Navigate to Feed screen"));

    }
}
