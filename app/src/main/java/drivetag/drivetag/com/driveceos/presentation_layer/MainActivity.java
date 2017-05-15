package drivetag.drivetag.com.driveceos.presentation_layer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import drivetag.drivetag.com.driveceos.R;
import drivetag.drivetag.com.driveceos.data_layer.models.User;
import drivetag.drivetag.com.driveceos.data_layer.requests.ServerRequest;
import drivetag.drivetag.com.driveceos.data_layer.requests.users.ClaimAccountRequest;
import drivetag.drivetag.com.driveceos.presentation_layer.feed.FeedFragment;
import drivetag.drivetag.com.driveceos.presentation_layer.mdt.MDTFragment;
import drivetag.drivetag.com.driveceos.presentation_layer.more.MoreFragment;
import drivetag.drivetag.com.driveceos.presentation_layer.notices.NoticesFragment;
import drivetag.drivetag.com.driveceos.presentation_layer.on_boarding.rows.UserRow;
import drivetag.drivetag.com.driveceos.presentation_layer.teamups.TeamUpsFragment;
import drivetag.drivetag.com.driveceos.presentation_layer.user_profile.ProfileFragment;

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

//                    User user = new User(null);
//                    user.email = "adfafad@naurugov.nr";
//                    user.password = "dimidrol4ik";
//                    user.driveTag = "BaronWaqa@naurugov.nr";
//                    user.verification = "1234567890";
//
//                    ClaimAccountRequest request = new ClaimAccountRequest(user);
//                    request.resumeWithCompletionHandler(new ServerRequest.ServerCompletionHandler() {
//                        @Override
//                        public void completionHandler(ServerRequest request) {
//                            System.out.println();
//                        }
//
//                        @Override
//                        public void completionHandlerWithError(String error) {
//                            System.out.println();
//                        }
//                    });


//                    UserStorage userStorage = ((DTApplication)getApplicationContext()).getUserStorage();
//                    String token = userStorage.getAccessToken();
//
//                    NoticesRequest request = new NoticesRequest("people", token);
//
//                    request.resumeWithCompletionHandler(new NoticesRequest.NoticesCompletionHandler() {
//                        @Override
//                        public void completionHandler(NoticesRequest request, int noticesCount, int noticesTeamCount, int noticesPeopleCount) {
//                            System.out.println();
//                        }
//
//                        @Override
//                        public void completionHandlerWithError(String error) {
//                            System.out.println();
//                        }
//                    });

//                    NoticesDataSource dataSource = new NoticesDataSource();
//                    dataSource.reloadData();

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

//        replaceFragment(MoreFragment.newInstance("Navigate to Feed screen"));
        replaceFragment(ProfileFragment.newInstance());
    }
    public void loginFacebook() {
        replaceFragment(FeedFragment.newInstance("Navigate to Feed screen"));
    }
}
