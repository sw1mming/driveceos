package drivetag.drivetag.com.driveceos.presentation_layer.more;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import drivetag.drivetag.com.driveceos.DTApplication;
import drivetag.drivetag.com.driveceos.R;
import drivetag.drivetag.com.driveceos.business_layer.FacebookSignInFlow;
import drivetag.drivetag.com.driveceos.data_layer.UserStorage;
import drivetag.drivetag.com.driveceos.data_layer.requests.PublicPostToMeRequest;
import drivetag.drivetag.com.driveceos.data_layer.requests.ServerRequest;
import drivetag.drivetag.com.driveceos.presentation_layer.BaseActivity;
import drivetag.drivetag.com.driveceos.presentation_layer.adapters.SignUpAdapter;
import drivetag.drivetag.com.driveceos.presentation_layer.on_boarding.rows.TableRow;
import drivetag.drivetag.com.driveceos.presentation_layer.on_boarding.rows.UserRow;

/**
 * Created by artem on 4/3/17.
 */

public class MoreActivity extends BaseActivity {

    SignUpAdapter adapter;

    private UserRow userRow;

    private MyTeammatesRow myTeammatesRow;

    private CheckMarkRow checkMarkRow;

    private MyAccountHeaderRow myAccountHeaderRow;

    private ColorRow colorRow;

    private SocialSwitchRow socialSwitchRow;

    private List<TableRow> rows;

    private FacebookSignInFlow facebookSignInFlow;


    /** Interface. */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        setupRows();
        setupRecyclerView();



//        UserStorage userStorage = ((DTApplication)getApplicationContext()).getUserStorage();
//        String token = userStorage.getAccessToken();
//
//        PublicPostToMeRequest request = new PublicPostToMeRequest(true, token);
//        request.resumeWithCompletionHandler(new ServerRequest.ServerCompletionHandler() {
//            @Override
//            public void completionHandler(ServerRequest request) {
//
//            }
//
//            @Override
//            public void completionHandlerWithError(String error) {
//
//            }
//        });
    }

    private void setupRows() {
        rows = new ArrayList<>();

        rows.add(setupUserRow());
        rows.add(setupMyTeammatesRow());
        rows.add(setupMyAccountHeaderRow());
        rows.add(setupAllowPublicPostRow());
        rows.add(setupAllowTeamupRequestToMe());
        rows.add(setupColorRow());
        rows.add(setupDriveOnHeaderRow());
        rows.add(setupDrivetagSwitchRow());
        rows.add(setupFacebookSwitchRow());
        rows.add(setupLinkedInSwitchRow());
        rows.add(setupTwitterSwitchRow());
    }

    private UserRow setupUserRow() {
        userRow = new UserRow();
//        userRow.selectionHandler = new TableRow.SelectionHandler() {
//            @Override
//            public void didSelectRow() {
//                // TODO: handle selection on user row.
//            }
//        };

        return userRow;
    }

    private MyTeammatesRow setupMyTeammatesRow() {
        myTeammatesRow = new MyTeammatesRow();
//        myTeammatesRow.selectionHandler = new TableRow.SelectionHandler() {
//            @Override
//            public void didSelectRow() {
//                // TODO: show MyTeammates screen.
//            }
//        };

        return myTeammatesRow;
    }

    private MyAccountHeaderRow setupMyAccountHeaderRow() {
        myAccountHeaderRow = new MyAccountHeaderRow();
        return myAccountHeaderRow;
    }

    private CheckMarkRow setupAllowPublicPostRow() {
        checkMarkRow = new CheckMarkRow();
        checkMarkRow.title = "Allow public posts to me";
//        checkMarkRow.selectionHandler = new TableRow.SelectionHandler() {
//            @Override
//            public void didSelectRow() {
//                Number userState = 0; // todo: self.userStorage.allowPublicPostsToMe
//
//                Number selectedState = 1;
//                Number deselectedState = 0;
//                Boolean currentState;
//
//                if (userState.equals(deselectedState)) {
//                    currentState = Boolean.TRUE;
//                    checkMarkRow.state = Boolean.TRUE;
//                    // todo: change allowPublicPostsToMe state
//                } else {
//                    currentState = Boolean.FALSE;
//                    checkMarkRow.state = Boolean.FALSE;
//                    // todo: change allowPublicPostsToMe state
//                }
//
//                PublicPostToMeRequest request = new PublicPostToMeRequest(currentState);
//                request.resumeWithCompletionHandler(new ServerRequest.ServerCompletionHandler() {
//                    @Override
//                    public void completionHandler(ServerRequest request) {
//                        System.out.println();
//                    }
//
//                    @Override
//                    public void completionHandlerWithError(String error) {
//                        System.out.println();
//                    }
//                });
//            }
//        };

        return checkMarkRow;
    }

    private CheckMarkRow setupAllowTeamupRequestToMe() {
        checkMarkRow = new CheckMarkRow();
        checkMarkRow.title = "Allow teamup requests to me";
//        checkMarkRow.selectionHandler = new TableRow.SelectionHandler() {
//            @Override
//            public void didSelectRow() {
//                Number userState = 0; // todo: self.userStorage.allowPublicPostsToMe
//
//                Number deselectedState = 0;
//                Boolean currentState;
//
//                if (userState.equals(deselectedState)) {
//                    currentState = Boolean.TRUE;
//                    checkMarkRow.state = Boolean.TRUE;
//                    // todo: change allowPublicPostsToMe state
//                } else {
//                    currentState = Boolean.FALSE;
//                    checkMarkRow.state = Boolean.FALSE;
//                    // todo: change allowPublicPostsToMe state
//                }
//
//                TeamupRequestsToMeRequest request = new TeamupRequestsToMeRequest(currentState);
//                request.resumeWithCompletionHandler(new ServerRequest.ServerCompletionHandler() {
//                    @Override
//                    public void completionHandler(ServerRequest request) {
//                        System.out.println();
//                    }
//
//                    @Override
//                    public void completionHandlerWithError(String error) {
//                        System.out.println();
//                    }
//                });
//            }
//        };

        return checkMarkRow;
    }

    private ColorRow setupColorRow() {
        colorRow = new ColorRow();
//        colorRow.selectionHandler = new TableRow.SelectionHandler() {
//            @Override
//            public void didSelectRow() {
//                // TODO: show "Color" activity.
//            }
//        };

        return colorRow;
    }

    private SocialSwitchRow setupDrivetagSwitchRow() {
        socialSwitchRow = new SocialSwitchRow();
        socialSwitchRow.title = ">Drivetag";

//        socialSwitchRow.selectionHandler = new TableRow.SelectionHandler() {
//            @Override
//            public void didSelectRow() {
//                // TODO: login screen.
//            }
//        };

        return socialSwitchRow;
    }

    private SocialSwitchRow setupFacebookSwitchRow() {
        socialSwitchRow = new SocialSwitchRow();
        socialSwitchRow.title = "Facebook";

//        socialSwitchRow.selectionHandler = new TableRow.SelectionHandler() {
//            @Override
//            public void didSelectRow() {
//                DTApplication DTApplication = (DTApplication)getApplicationContext();
//
//                UserStorage userStorage = DTApplication.userStorage;
//
//                facebookSignInFlow = new FacebookSignInFlow(getApplicationContext(), userStorage);
//                facebookSignInFlow.resumeWithCompletionHandler(new FacebookSignInFlow.FlowCompletionHandler() {
//                    @Override
//                    public void completionHandler(FacebookSignInFlow flow, User user, String error) {
//                        System.out.println();
//                    }
//
//                    @Override
//                    public void completionHandlerWithError(String error) {
//                        System.out.println();
//                    }
//
//                    @Override
//                    public void switchCompletionHandler(FacebookSignInFlow flow, User user) {
//                        System.out.println();
//                    }
//                });
//            }
//        };

        return socialSwitchRow;
    }

    private SocialSwitchRow setupLinkedInSwitchRow() {
        socialSwitchRow = new SocialSwitchRow();
        socialSwitchRow.title = "LinkedIn";

//        socialSwitchRow.selectionHandler = new TableRow.SelectionHandler() {
//            @Override
//            public void didSelectRow() {
//                // TODO: LinkedIn flow.
//            }
//        };

        return socialSwitchRow;
    }

    private SocialSwitchRow setupTwitterSwitchRow() {
        socialSwitchRow = new SocialSwitchRow();
        socialSwitchRow.title = "Twitter";

//        socialSwitchRow.selectionHandler = new TableRow.SelectionHandler() {
//            @Override
//            public void didSelectRow() {
//                // TODO: Twitter flow.
//            }
//        };

        return socialSwitchRow;
    }

    private DriveOnHeaderRow setupDriveOnHeaderRow() {
        return new DriveOnHeaderRow();
    }

    private void setupRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);

        adapter = new SignUpAdapter();
        adapter.rows = rows;

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.more_recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
    }
}
