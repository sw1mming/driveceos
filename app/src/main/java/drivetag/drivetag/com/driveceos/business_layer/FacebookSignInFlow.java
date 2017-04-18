package drivetag.drivetag.com.driveceos.business_layer;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import drivetag.drivetag.com.driveceos.DTApplication;
import drivetag.drivetag.com.driveceos.data_layer.models.User;
import drivetag.drivetag.com.driveceos.data_layer.requests.FacebookLoginRequest;
import drivetag.drivetag.com.driveceos.data_layer.requests.MergeAccountsRequest;
import drivetag.drivetag.com.driveceos.data_layer.requests.ServerRequest;
import drivetag.drivetag.com.driveceos.data_layer.requests.SocialNetworkEnableRequest;

import static drivetag.drivetag.com.driveceos.data_layer.requests.SocialNetworkEnableRequest.SocialNetwork.SocialNetworkFacebook;

/**
 * Created by artem.
 */

public class FacebookSignInFlow extends LoginFlow {

    private SocialNetworkEnableRequest socialNetworkEnableRequest;

    private Boolean shouldMerge = false;

    private FlowCompletionHandler completionHandler;

    private CallbackManager callbackManager;


    /** Interface. */

    public FacebookSignInFlow(final DTApplication context) {
        super(context);
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                resumeWithCompletionHandler(loginResult, new FlowCompletionHandler() {
                    @Override
                    public void completionHandler(User user, String error) {
                    }

                    @Override
                    public void completionHandlerWithError(String error) {

                    }

                    @Override
                    public void switchCompletionHandler(User user) {

                    }
                });
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(context, "error to Login Facebook", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void resumeWithCompletionHandler(LoginResult loginResult, final FacebookSignInFlow.FlowCompletionHandler handler) {
        if (userStorage.user != null && userStorage.user.facebookID != null) {
            userStorage.user.facebookOn = !userStorage.user.facebookOn;

            handler.switchCompletionHandler(userStorage.user);
        } else {
            completionHandler = handler;

            if (userStorage.isLoggedIn) {
                shouldMerge = true;
            }

            if (shouldMerge) {
                mergeFacebookAccount(loginResult);
            } else {
                loginViaFacebook(loginResult);
            }
        }
    }

    private void mergeFacebookAccount(LoginResult loginResult) {
        MergeAccountsRequest mergeAccountsRequest = new MergeAccountsRequest();
        mergeAccountsRequest.userId = loginResult.getAccessToken().getUserId();
        mergeAccountsRequest.accessToken = loginResult.getAccessToken().getToken();
        mergeAccountsRequest.provider = "facebook";
        mergeAccountsRequest.dtToken = userStorage.accessToken;

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

    private void loginViaFacebook(LoginResult loginResult) {
        FacebookLoginRequest facebookLoginRequest = new FacebookLoginRequest(loginResult.getAccessToken().getToken(), loginResult.getAccessToken().getUserId());
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

    private void handleLoggedInUser(final User user) {
        if (user == null) {
            finishFLowWithUser(null, "Something went wrong.");
        }

        socialNetworkEnableRequestWithCompletionHandler(user, new CompletionHandler<User>() {
            @Override
            public void completionHandler(User value, String error) {
                if (error != null) {
                    finishFLowWithUser(user, error);
                } else {
                    loadUserWithCompletionHandler(new CompletionHandler<User>() {
                        @Override
                        public void completionHandler(User user, String error) {
                            saveUser(user);
                            finishFLowWithUser(user, error);
                        }
                    });
                }
            }
        });

    }

    private void socialNetworkEnableRequestWithCompletionHandler (final User user, final CompletionHandler<User> handler) {
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

        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

        if (completionHandler != null) {
            completionHandler.completionHandler(user, error);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode,resultCode, data );
    }

    public interface FlowCompletionHandler {
        void completionHandler(User user, String error);
        void completionHandlerWithError(String error);
        void switchCompletionHandler(User user);
    }
}

