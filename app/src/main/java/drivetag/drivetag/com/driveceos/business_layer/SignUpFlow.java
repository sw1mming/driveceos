package drivetag.drivetag.com.driveceos.business_layer;

import java.util.List;

import drivetag.drivetag.com.driveceos.DTApplication;
import drivetag.drivetag.com.driveceos.data_layer.models.User;
import drivetag.drivetag.com.driveceos.data_layer.requests.CheckEmailTypeRequest;
import drivetag.drivetag.com.driveceos.data_layer.requests.EmployeeTitlesRequest;
import drivetag.drivetag.com.driveceos.data_layer.requests.ServerRequest;
import drivetag.drivetag.com.driveceos.data_layer.requests.SuggestedIdentifierRequest;
import drivetag.drivetag.com.driveceos.data_layer.requests.VerifyRequest;
import drivetag.drivetag.com.driveceos.data_layer.requests.on_boarding.SignUpRequest;

/**
 * Created by sergeymelnik on 2017-03-31.
 */

public class SignUpFlow extends LoginFlow {

    public User user;

    public Boolean isPersonalEmail;

    public Boolean isEmailValidationSentAutomatically;

    //userStorage

    public String imagesUrlString;


    public SignUpFlow(DTApplication dtApplication) {
        super(dtApplication);
        this.user = dtApplication.getUserStorage().user();
    }

    public void isPersonalEmail(String email, final CompletionHandler<Boolean> handler) {
        CheckEmailTypeRequest checkEmailTypeRequest = new CheckEmailTypeRequest(email);
        checkEmailTypeRequest.resumeWithCompletionHandler(new ServerRequest.ServerCompletionHandler() {
            @Override
            public void completionHandler(ServerRequest request) {
                String emailType = ((CheckEmailTypeRequest) request).emailType;
                Boolean personal = emailType.equals(User.EmailTypePersonal);

                if (handler != null) {
                    handler.completionHandler(personal, request.error);
                }
            }

            @Override
            public void completionHandlerWithError(String error) {
                if (handler != null) {
                    handler.completionHandler(null, error);
                }
            }
        });
    }

    public void employeeTitlesForEmail(String email, final CompletionHandler<List<String>> handler) {
        EmployeeTitlesRequest employeeTitlesRequest = new EmployeeTitlesRequest(email);
        employeeTitlesRequest.resumeWithCompletionHandler(new ServerRequest.ServerCompletionHandler<List<String>>() {
            @Override
            public void completionHandler(ServerRequest<List<String>> request) {
                if (handler != null) {
                    handler.completionHandler(request.serverResponse, request.error);
                }
            }

            @Override
            public void completionHandlerWithError(String error) {
                if (handler != null) {
                    handler.completionHandler(null, error);
                }
            }
        });
    }

    public void freeDriveTagsForEmail(String email,
                                      String firstName,
                                      String lastName,
                                      final CompletionHandler<List<String>> handler) {

        SuggestedIdentifierRequest suggestedIdentifierRequest = new SuggestedIdentifierRequest(email, firstName, lastName);
        suggestedIdentifierRequest.resumeWithCompletionHandler(new ServerRequest.ServerCompletionHandler<List<String>>() {
            @Override
            public void completionHandler(ServerRequest<List<String>> request) {
                if (handler != null) {
                    handler.completionHandler(request.serverResponse, request.error);
                }
            }

            @Override
            public void completionHandlerWithError(String error) {
                if (handler != null) {
                    handler.completionHandler(null, error);
                }
            }
        });
    }

    public void verifyEmail(String email, final CompletionErrorHandler handler) {
        VerifyRequest verifyRequest = new VerifyRequest(email);
        verifyRequest.resumeWithCompletionHandler(new ServerRequest.ServerCompletionHandler() {
            @Override
            public void completionHandler(ServerRequest request) {
                if (request.isFailure) {
                    if (handler != null) {
                        handler.completionHandlerWithError(request.error);
                    }
                } else {
                    // checkemail type

                    if (handler != null) {
                        handler.completionHandlerWithError(null);
                    }
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

    public void checkEmailType(String email, final CompletionErrorHandler handler) {
        CheckEmailTypeRequest emailTypeRequest = new CheckEmailTypeRequest(email);
        emailTypeRequest.resumeWithCompletionHandler(new ServerRequest.ServerCompletionHandler() {
            @Override
            public void completionHandler(ServerRequest request) {
                user.emailType = ((CheckEmailTypeRequest)request).emailType;

                if (user.emailType.equals(User.EmailTypeWork)) {
                    isPersonalEmail = false;
                } else {
                    isPersonalEmail = true;
                }

                if (handler != null) {
                    handler.completionHandlerWithError(request.error);
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

    public void resumeWithCompletionHandler(final CompletionHandler<User> handler) {
        final SignUpRequest signUpRequest = new SignUpRequest(user);
        signUpRequest.imageUrlString = imagesUrlString;

        signUpRequest.resumeWithCompletionHandler(new ServerRequest.ServerCompletionHandler() {
            @Override
            public void completionHandler(ServerRequest request) {

                if (request.isFailure) {
                    if (handler != null) {
                        handler.completionHandler(null, signUpRequest.error);
                    }
                } else {
                    user = ((SignUpRequest)request).user;
//                    userStorage accessToken
//
//                    loadUserWithCompletionHandler(new CompletionHandler<User>() {
//                        @Override
//                        public void completionHandler(User completionObject, String error) {
//
//                        }
//                    });
                }
            }

            @Override
            public void completionHandlerWithError(String error) {
                if (handler != null) {
                    handler.completionHandler(null, error);
                }
            }
        });
    }

//    - (void)resumeWithUserPhotoUrlString: (NSString *)photoString
//    completionBlock: (void(^)(SignUpFlow *signUpFlow, User *user, NSError *error))completionBlock {
//        __weak SignUpFlow *weakSelf = self;
//
//        self.signUpRequest = [[SignUpRequest alloc] initWithSession: self.networkSession user: self.user];
//    [ObjectFactory performInjectionOn: self.signUpRequest];
//        self.signUpRequest.imageUrlString = self.imagesUrlStrings;
//
//    [self.signUpRequest resumeWithCompletionBlock:^(SignUpRequest *signUpRequest) {
//            SignUpFlow *strongSelf = weakSelf;
//
//            if (signUpRequest.isFailure) {
//                if (completionBlock) {
//                    completionBlock(strongSelf, nil, signUpRequest.error);
//                }
//            } else {
//                strongSelf.user = signUpRequest.user;
//                strongSelf.userStorage.accessToken = signUpRequest.accessToken;
//            [strongSelf.networkSession setAccessToken: signUpRequest.accessToken];
//
//            [strongSelf loadUserWithConletionBlock:^(User *loadedUser, NSError *error) {
//
//                [strongSelf.userStorage saveUserAfterEmailLogin: loadedUser];
//
//                    if (completionBlock) {
//                        completionBlock(strongSelf, signUpRequest.user, signUpRequest.error);
//                    }
//                }];
//            }
//        }];
//    }

    public interface CompletionErrorHandler {
        void completionHandlerWithError(String error);
    }
}
