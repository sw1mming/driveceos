package drivetag.drivetag.com.driveceos.presentation_layer.more;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import drivetag.drivetag.com.driveceos.R;
import drivetag.drivetag.com.driveceos.business_layer.FacebookSignInFlow;
import drivetag.drivetag.com.driveceos.presentation_layer.adapters.SignUpAdapter;
import drivetag.drivetag.com.driveceos.presentation_layer.on_boarding.rows.TableRow;
import drivetag.drivetag.com.driveceos.presentation_layer.on_boarding.rows.UserRow;

public class MoreFragment extends Fragment {


    SignUpAdapter adapter;

    private UserRow userRow;

    private MyTeammatesRow myTeammatesRow;

    private CheckMarkRow checkMarkRow;

    private MyAccountHeaderRow myAccountHeaderRow;

    private ColorRow colorRow;

    private SocialSwitchRow socialSwitchRow;

    private List<TableRow> rows;

    private FacebookSignInFlow facebookSignInFlow;

    public MoreFragment() {
    }

    public static MoreFragment newInstance(String userId) {
        MoreFragment viewPreferenceFragment = new MoreFragment();
        Bundle bundle = new Bundle();
        bundle.putString("userId", userId);
        viewPreferenceFragment.setArguments(bundle);
        return viewPreferenceFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_more, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final String userId = getArguments().getString("userId");
        Toast.makeText(getActivity(), userId, Toast.LENGTH_SHORT).show();

        setupRows();
        setupRecyclerView();
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
//            public void di1dSelectRow() {
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
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());

        adapter = new SignUpAdapter();
        adapter.rows = rows;

        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.more_recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
    }
}
