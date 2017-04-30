package drivetag.drivetag.com.driveceos.business_layer;

import java.net.MalformedURLException;
import java.net.URL;

import drivetag.drivetag.com.driveceos.DTApplication;
import drivetag.drivetag.com.driveceos.data_layer.models.User;
import drivetag.drivetag.com.driveceos.data_layer.requests.ServerRequest;
import drivetag.drivetag.com.driveceos.data_layer.requests.SocialNetworkEnableRequest;

/**
 * Created by sergeymelnik on 2017-04-13.
 */

public class LoginLinkedInFlow extends LoginFlow {

    private SocialNetworkEnableRequest socialNetworkEnableRequest;

    private LoginLinkedInFlowCompletionHandler flowCompletionHandler;


    /**
     * Interface.
     *
     * @param dtApplication
     */
    public LoginLinkedInFlow(DTApplication dtApplication) {
        super(dtApplication);
    }

    public void resumeWithCompletionBlock(LoginLinkedInFlowCompletionHandler handler) {
        if (userStorage.user.linkedInID != null) {
            if (userStorage.user.linkedInOn) {
                userStorage.user.linkedInOn = false;
            } else {
                userStorage.user.linkedInOn = true;
            }

            if (handler != null) {
                handler.switchCompletionHandler(getLoginLinkedInFlow(), userStorage.user);
            }
        } else {
            flowCompletionHandler = handler;

            URL url;
            if (userStorage.isLoggedIn) {
                try {
                    url = new URL("https://dev.drivetagdev.com/api/auth/linkedin/mergepopup");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    url = new URL("https://dev.drivetagdev.com/api/auth/linkedin/loginpopup");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }

            //TODO: Show LinkedInLoginFragment and handle response
//            LinkedInLoginViewController *viewController = [LinkedInLoginViewController new];
//        [ObjectFactory performInjectionOn: viewController];
//            viewController.url = url;
//
//            __weak LoginLinkedInFlow *weakSelf = self;
//
//            viewController.completionBlock = ^(LinkedInLoginViewController *linkedInLoginViewController, NSString *accessToken) {
//                LoginLinkedInFlow *strongSelf = weakSelf;
//
//                strongSelf.hud = [MBProgressHUD hudWithView: strongSelf.viewController.view];
//            [strongSelf.viewController.view.window addSubview: strongSelf.hud];
//            [strongSelf.hud show: YES];
//
//                strongSelf.userStorage.accessToken = accessToken;
//            [strongSelf.networkSession setAccessToken: strongSelf.userStorage.accessToken];
//
//            [strongSelf handleLoggedInUser: [User new]];
//            };
//
//            UINavigationController *navigationController = [[UINavigationController alloc] initWithRootViewController: viewController];
//
//        [self.viewController presentViewController: navigationController animated: YES completion: nil];
        }
    }

    public void handleLoggedInUser(User user) {
        if (user == null) {
            finishFLowWithUser(user, null);
            return;
        }

        socialNetworkEnableRequest(user, new LoginLinkedInFlowCompletionHandler() {
            @Override
            public void completionHandler(LoginLinkedInFlow flow, final User user, String error) {
                if (error != null) {
                    finishFLowWithUser(null, error);
                } else {
                    loadUserWithCompletionHandler(new CompletionHandler<User>() {
                        @Override
                        public void completionHandler(User value, String error) {
                            if (value != null) {
                                userStorage.saveUserAfterLinkedInLogin(value);
                            }

                            finishFLowWithUser(user, error);
                        }
                    });
                }
            }

            @Override
            public void completionHandlerWithError(String error) {
                finishFLowWithUser(null, error);
            }

            @Override
            public void switchCompletionHandler(LoginLinkedInFlow flow, User user) {
            }
        });
    }

    private void socialNetworkEnableRequest(final User user, final LoginLinkedInFlowCompletionHandler handler) {
        socialNetworkEnableRequest = new SocialNetworkEnableRequest(SocialNetworkEnableRequest.SocialNetwork.SocialNetworkLinkedIn);
        socialNetworkEnableRequest.resumeWithCompletionHandler(new ServerRequest.ServerCompletionHandler() {
            @Override
            public void completionHandler(ServerRequest request) {
                if (handler != null) {
                    handler.completionHandler(getLoginLinkedInFlow(), user, null);
                }
            }

            @Override
            public void completionHandlerWithError(String error) {
                if (handler != null) {
                    handler.completionHandlerWithError(error);
                }
            }
        });
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

        //TODO: Should be handle alert(toast)
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

        if (flowCompletionHandler != null) {
            flowCompletionHandler.completionHandler(getLoginLinkedInFlow(), user, error);
        }
    }

    private LoginLinkedInFlow getLoginLinkedInFlow() {
        return this;
    }

    public interface LoginLinkedInFlowCompletionHandler {
        void completionHandler(LoginLinkedInFlow flow, User user, String error);
        void completionHandlerWithError(String error);
        void switchCompletionHandler(LoginLinkedInFlow flow, User user);
    }

}

