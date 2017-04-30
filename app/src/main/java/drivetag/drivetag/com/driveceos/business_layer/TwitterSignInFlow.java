package drivetag.drivetag.com.driveceos.business_layer;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

import drivetag.drivetag.com.driveceos.DTApplication;
import drivetag.drivetag.com.driveceos.data_layer.models.User;
import retrofit2.Call;

public class TwitterSignInFlow extends LoginFlow {

    private TwitterSignInFlow.FlowCompletionHandler completionHandler;

    /** Interface. */

    public TwitterSignInFlow(final DTApplication context, TwitterSignInFlow.FlowCompletionHandler completionHandler) {
        super(context);
        this.completionHandler = completionHandler;
    }

    public void resumeWithCompletionHandler(Activity activity) {
        final TwitterSession session =
                Twitter.getSessionManager().getActiveSession();
        final Callback<com.twitter.sdk.android.core.models.User> callback = new Callback<com.twitter.sdk.android.core.models.User>() {
            @Override
            public void success(Result<com.twitter.sdk.android.core.models.User> result) {
                // TODO: 30.04.2017  login request -> save user
                Log.d(getClass().getSimpleName(), "Twitter response ok + user: " + result.data.email);
            }

            @Override
            public void failure(TwitterException exception) {
                Log.e(getClass().getSimpleName(), "Twitter response error + user: " , exception);
            }
        };
        if (session == null) {
            new TwitterAuthClient().authorize(activity, new Callback<TwitterSession>() {
                @Override
                public void success(Result<TwitterSession> result) {
                    final Call<com.twitter.sdk.android.core.models.User> userCall = Twitter.getApiClient(result.data).getAccountService().verifyCredentials(true, false);
                    userCall.enqueue(callback);
                }

                @Override
                public void failure(TwitterException exception) {
                    Log.e(getClass().getSimpleName(), "Twitter response error + user: " , exception);
                }
            });
        }
        else {
            final Call<com.twitter.sdk.android.core.models.User> userCall = Twitter.getApiClient(session).getAccountService().verifyCredentials(true, false);
            userCall.enqueue(callback);
        }
    }

    private void saveUser(User user) {
        if (user != null) {
            userStorage.saveUserAfterFacebookLogin(user);
        }
    }

    private void finishFLowWithUser(User user, String error) {
        String message = "Something went wrong";

        if (error != null || user == null) {
            if (error != null) {
                message = error;
            }
        } else {
            message = "You were successfully logged in to Facebook.";
        }

        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

        if (completionHandler != null) {
            completionHandler.completionHandler(user, error);
        }
    }

    public interface FlowCompletionHandler {
        void completionHandler(User user, String error);
        void completionHandlerWithError(String error);
        void switchCompletionHandler(User user);
    }
}

