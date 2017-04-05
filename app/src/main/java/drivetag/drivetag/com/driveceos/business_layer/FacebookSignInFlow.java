package drivetag.drivetag.com.driveceos.business_layer;

import drivetag.drivetag.com.driveceos.DTApplication;
import drivetag.drivetag.com.driveceos.data_layer.models.User;
import drivetag.drivetag.com.driveceos.data_layer.requests.FacebookLoginRequest;
import drivetag.drivetag.com.driveceos.data_layer.requests.MergeAccountsRequest;
import drivetag.drivetag.com.driveceos.data_layer.requests.ServerRequest;
import drivetag.drivetag.com.driveceos.data_layer.requests.SocialNetworkEnableRequest;

import static drivetag.drivetag.com.driveceos.data_layer.requests.SocialNetworkEnableRequest.SocialNetwork.SocialNetworkFacebook;

/**
 * Created by artem on 4/4/17.
 */

public class FacebookSignInFlow extends LoginFlow {

    private FacebookLoginRequest facebookLoginRequest;
    private MergeAccountsRequest mergeAccountsRequest;
    private SocialNetworkEnableRequest socialNetworkEnableRequest;

    private Boolean shouldMerge = false;

    private FlowCompletionHandler completionHandler;


    /** Interface. */

    public FacebookSignInFlow(DTApplication context) {
        super(context);
    }

    public void resumeWithCompletionHandler(final FacebookSignInFlow.FlowCompletionHandler handler) {
        if (userStorage.user != null && userStorage.user.facebookID != null) {
            if (userStorage.user.facebookOn) {
                userStorage.user.facebookOn = false;
            } else {
                userStorage.user.facebookOn = true;
            }

            handler.switchCompletionHandler(getFacebookSignInFlow(), userStorage.user);
        } else {
            completionHandler = handler;

            if (userStorage.isLoggedIn) {
                shouldMerge = true;
            }

            if (shouldMerge) {
                mergeFacebookAccount();
            } else {
                loginViaFacebook();
            }
        }
    }

    private void mergeFacebookAccount() {
        // todo: self.facebookNetwork loadUserWithCompletionBlock

        mergeAccountsRequest = new MergeAccountsRequest();
        mergeAccountsRequest.userId = ""; // todo: facebookUser.facebookID
        mergeAccountsRequest.accessToken = ""; // todo: [FacebookNetwork accessToken]
        mergeAccountsRequest.provider = "facebook";
        mergeAccountsRequest.dtToken = ""; // todo: self.userStorage.accessToken

        mergeAccountsRequest.resumeWithCompletionHandler(new ServerRequest.ServerCompletionHandler<User>() {
            @Override
            public void completionHandler(ServerRequest<User> request) {

                if (request.isFailure || request.error != null) {
                    finishFLowWithUser(null, request.error);
                } else {
                    User loggedInUser = request.serverResponse;
                    handleLoggedInUser(loggedInUser);
                }
            }

            @Override
            public void completionHandlerWithError(String error) {
                finishFLowWithUser(null, error);
            }
        });
    }

    private void loginViaFacebook() {
        // todo: self.facebookNetwork loadUserWithCompletionBlock

//        [self.facebookNetwork loadUserWithCompletionBlock: ^(NSError *error, User *facebookUser) {
//
//            FacebookSignInFlow *strongSelf = weakSelf;
//
//            if (!facebookUser && strongSelf.completionBlock) {
//            [strongSelf finishFlowWithUser: nil error: error];
//                return;
//            }

        facebookLoginRequest = new FacebookLoginRequest("EAAUmQyOW8JMBAJxHRkX54ouzDhaNwAqqX83fAYBBj61GsMJXNteZCKTWZBZAp80FyBeT0CIv2ZAyIr71sIcExnKIZBoKkUZBl7RKcmDjPuYLgaRopqlBGOrU5YmZCIB9TnU0e9aM9NHpKONrvmL3zYveHJOQHQl2kZAfE65ZClN1gmT9Ksal2FtZCSgNbotMwT4ZAIZD", "906160376139339");
        facebookLoginRequest.resumeWithCompletionHandler(new ServerRequest.ServerCompletionHandler<User>() {
            @Override
            public void completionHandler(ServerRequest<User> request) {
                if (request.isFailure) {
                    finishFLowWithUser(null, request.error);
                    return;
                }

                FacebookLoginRequest fbRequest = (FacebookLoginRequest)request;
                userStorage.accessToken = fbRequest.accessToken;

                // todo: [strongSelf.networkSession setAccessToken: facebookRequest.accessToken];

                User loggedInUser = request.serverResponse;
                handleLoggedInUser(loggedInUser);
            }

            @Override
            public void completionHandlerWithError(String error) {
                finishFLowWithUser(null, error);
            }
        });

    }

    private void handleLoggedInUser(User user) {
        if (user == null) {
            finishFLowWithUser(null, "Something went wrong.");

            return;
        }

//        socialNetworkEnableRequestWithCompletionHandler(user, new CompletionHandler() {
//            @Override
//            public void completionHandler(User user, String error) {
//                if (error != null) {
//                    finishFLowWithUser(user, error);
//                } else {
//                    loadUserWithCompletionHandler(new CompletionHandler() {
//                        @Override
//                        public void completionHandler(User user, String error) {
//                            saveUser(user);
//                            finishFLowWithUser(user, error);
//                        }
//                    });
//                }
//            }
//        });

    }

    private void socialNetworkEnableRequestWithCompletionHandler (final User user, final CompletionHandler handler) {
        socialNetworkEnableRequest = new SocialNetworkEnableRequest(SocialNetworkFacebook);
        socialNetworkEnableRequest.resumeWithCompletionHandler(new ServerRequest.ServerCompletionHandler() {
            @Override
            public void completionHandler(ServerRequest request) {
                handler.completionHandler(user, request.error);
            }

            @Override
            public void completionHandlerWithError(String error) {
                finishFLowWithUser(null, error);
            }
        });
    }

    private void saveUser(User user) {
        if (user != null) {
            userStorage.saveUserAfterFacebookLogin(user);
        }
    }

    private void finishFLowWithUser(User user, String error) {
        String title;
        String message = "Something went wrong";

        if (error != null || user == null) {
            title = "Error";

            if (error != null) {
                message = error;
            }
        } else {
            title = "Success";
            message = "You were successfully logged in to Facebook.";
        }

//        new AlertDialog.Builder(context)
//                .setTitle(title)
//                .setMessage(message)
//                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        System.out.println();
//                    }
//                })
//                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        // do nothing
//                    }
//                })
//                .setIcon(android.R.drawable.ic_dialog_alert)
//                .show();

        if (completionHandler != null) {
            completionHandler.completionHandler(getFacebookSignInFlow(), user, error);
        }
    }

    private FacebookSignInFlow getFacebookSignInFlow() {
        return this;
    }

    public interface FlowCompletionHandler<T> {
        void completionHandler(FacebookSignInFlow flow, User user, String error);
        void completionHandlerWithError(String error);
        void switchCompletionHandler(FacebookSignInFlow flow, User user);
    }
}

