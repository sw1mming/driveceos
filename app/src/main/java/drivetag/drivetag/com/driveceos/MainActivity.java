package drivetag.drivetag.com.driveceos;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import drivetag.drivetag.com.driveceos.data_layer.requests.MdtRequest;
import drivetag.drivetag.com.driveceos.data_layer.requests.ServerRequest;
import drivetag.drivetag.com.driveceos.data_layer.requests.SocialNetworkEnableRequest;

import static drivetag.drivetag.com.driveceos.data_layer.requests.SocialNetworkEnableRequest.SocialNetwork.SocialNetworkFacebook;

/**
 * Created by yuriy on 3/22/17.
 */

public class MainActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /**  MdtRequest  */
//        MdtRequest mdtRequest = new MdtRequest("driven_leaders", "all", 0);
//        mdtRequest.resumeWithCompletionHandler(new ServerRequest.ServerCompletionHandler() {
//            @Override
//            public void completionHandler(ServerRequest request) {
//                System.out.println();
//            }
//
//            @Override
//            public void completionHandlerWithError(String error) {
//                System.out.println();
//            }
//        });



        /**  TwitterLoginRequest  */
//        TwitterLoginRequest twitterLoginRequest = new TwitterLoginRequest("EAAUmQyOW8JMBAHi13J33hOqZBkUpPtHBHXVgb0TgNqGBFGZByRDzuiqpvJiZAez2Q0ZBtgXgx8v52vlwtDTyj377ZC7iTxZAQXOq2XtHazpGf8QJMsgCP7uB9qZBDFJSMZAbvZCoGkloZAr6RK7yxxqT4wpdOHijQRsWhxAxshICk54mxCBQiIoYFhyEH70sfKS2iv7HmpjICl2Km7OPRYaZAIeyfmGAHJr0vMZD", "869147083162811", "dsvwv");
//        twitterLoginRequest.resumeWithCompletionHandler(new ServerRequest.ServerCompletionHandler() {
//            @Override
//            public void completionHandler(ServerRequest request) {
//                System.out.println();
//            }
//
//            @Override
//            public void completionHandlerWithError(String error) {
//                System.out.println();
//            }
//        });


        /**  MergeAccountsRequest  */
//        MergeAccountsRequest mergeAccountsRequest = new MergeAccountsRequest();
//        mergeAccountsRequest.resumeWithCompletionHandler(new ServerRequest.ServerCompletionHandler() {
//            @Override
//            public void completionHandler(ServerRequest request) {
//                System.out.println();
//            }
//
//            @Override
//            public void completionHandlerWithError(String error) {
//                System.out.println();
//            }
//        });


        /**  SocialNetworkEnableRequest  */
//        SocialNetworkEnableRequest socialNetworkEnableRequest = new SocialNetworkEnableRequest(SocialNetworkFacebook);
//        socialNetworkEnableRequest.resumeWithCompletionHandler(new ServerRequest.ServerCompletionHandler() {
//            @Override
//            public void completionHandler(ServerRequest request) {
//                System.out.println();
//            }
//
//            @Override
//            public void completionHandlerWithError(String error) {
//                System.out.println();
//            }
//        });



        /**  FacebookLoginRequest  */
//        FacebookLoginRequest facebookLoginRequest = new FacebookLoginRequest("EAAUmQyOW8JMBAHi13J33hOqZBkUpPtHBHXVgb0TgNqGBFGZByRDzuiqpvJiZAez2Q0ZBtgXgx8v52vlwtDTyj377ZC7iTxZAQXOq2XtHazpGf8QJMsgCP7uB9qZBDFJSMZAbvZCoGkloZAr6RK7yxxqT4wpdOHijQRsWhxAxshICk54mxCBQiIoYFhyEH70sfKS2iv7HmpjICl2Km7OPRYaZAIeyfmGAHJr0vMZD", "869147083162811");
//        facebookLoginRequest.resumeWithCompletionHandler(new ServerRequest.ServerCompletionHandler() {
//            @Override
//            public void completionHandler(ServerRequest request) {
//                System.out.println();
//            }
//
//            @Override
//            public void completionHandlerWithError(String error) {
//                System.out.println();
//            }
//        });


        /**  LoadCurrentUserRequest  */
//        LoadCurrentUserRequest loadCurrentUserRequest = new LoadCurrentUserRequest();
//        loadCurrentUserRequest.resumeWithCompletionHandler(new ServerRequest.ServerCompletionHandler() {
//            @Override
//            public void completionHandler(ServerRequest request) {
//                System.out.println();
//            }
//
//            @Override
//            public void completionHandlerWithError(String error) {
//                System.out.println();
//            }
//        });


        /**  EmployeeTitlesRequest  */
//        EmployeeTitlesRequest request = new EmployeeTitlesRequest("rayban@mailinator.com");
//        request.resumeWithCompletionHandler(new ServerRequest.ServerCompletionHandler() {
//            @Override
//            public void completionHandler(ServerRequest request) {
//                System.out.println();
//            }
//
//            @Override
//            public void completionHandlerWithError(String error) {
//                System.out.println();
//            }
//        });

        /**  VerifyRequest  */
//        VerifyRequest verifyRequest = new VerifyRequest("safcvafceqwcwcqqewc@gmail.com");
//        verifyRequest.resumeWithCompletionHandler(new VerifyRequest.ServerCompletionHandler() {
//            @Override
//            public void completionHandler(ServerRequest request) {
//                System.out.println();
//            }
//
//            @Override
//            public void completionHandlerWithError(String error) {
//                System.out.println();
//            }
//        });


        /**  CheckEmailTypeRequest  */
//        CheckEmailTypeRequest checkEmailTypeRequest = new CheckEmailTypeRequest("safcvafceqwcwcqqewc@gmail.com");
//        checkEmailTypeRequest.resumeWithCompletionHandler(new CheckEmailTypeRequest.ServerCompletionHandler() {
//            @Override
//            public void completionHandler(ServerRequest request) {
//                System.out.println();
//            }
//
//            @Override
//            public void completionHandlerWithError(String error) {
//                System.out.println();
//            }
//        });


        /**  SuggestedIdentifierRequest  */
//        SuggestedIdentifierRequest suggestedIdentifierRequest = new SuggestedIdentifierRequest("dcefd3Secc@mailinator.com", "dcefd3Secc","Dfvcdsdfgfd");
//        suggestedIdentifierRequest.resumeWithCompletionHandler(new SuggestedIdentifierRequest.ServerCompletionHandler() {
//            @Override
//            public void completionHandler(ServerRequest request) {
//                System.out.println();
//            }
//
//            @Override
//            public void completionHandlerWithError(String error) {
//                System.out.println();
//            }
//        });


        /**  ImageUploadRequest  */
//        Drawable myIcon = getResources().getDrawable( R.drawable.facebook_enabled_icon );
//
//        ImageUploadRequest imageUploadRequest = new ImageUploadRequest(myIcon);
//        imageUploadRequest.resumeWithCompletionHandler(new ImageUploadRequest.ServerCompletionHandler() {
//            @Override
//            public void completionHandler(ServerRequest request) {
//                System.out.println();
//            }
//
//            @Override
//            public void completionHandlerWithError(String error) {
//                System.out.println();
//            }
//        });


//        String fileName = String.format("text_%d", 1);
//                System.out.println(fileName);
//        System.out.println(fileName);


//        log.debug( String.format("%s is %d years old, er, young", "Al", 45) );

//        Intent intent = new Intent(this, UserProfileActivity.class);
//        startActivity(intent);

//        SignInRequest signInRequest = new SignInRequest("rayban@mailinator.com", "qwe123456");
//        signInRequest.resumeWithCompletionHandler(new SignInRequest.ServerCompletionHandler() {
//            @Override
//            public void completionHandler(ServerRequest request) {
//                System.out.println();
//            }
//
//            @Override
//            public void completionHandlerWithError(String error) {
//                System.out.println();
//            }
//        });

        /**  UpdateCoverPhotoRequest  */
//
//          UpdateCoverPhotoRequest updateCoverPhotoRequest = new UpdateCoverPhotoRequest(null);
//          updateCoverPhotoRequest.resumeWithCompletionHandler(new UpdateCoverPhotoRequest.ServerCompletionHandler() {
//            @Override
//            public void completionHandler(ServerRequest request) {
//                System.out.println();
//            }
//
//            @Override
//            public void completionHandlerWithError(String error) {
//                System.out.println();
//            }
//        });

        /**  UpdateMyPageRequest  */

//
//        List<String> array = new ArrayList<>();
//        array.add("https://www.rover.com/blog/wp-content/uploads/2015/05/dog-candy-junk-food-599x340.jpg");
//
//
//          UpdateMyPageRequest updateMyPageRequest = new UpdateMyPageRequest(array);
//          updateMyPageRequest.resumeWithCompletionHandler(new UpdateMyPageRequest.ServerCompletionHandler() {
//            @Override
//            public void completionHandler(ServerRequest request) {

//        MyTeamupRequest request = new MyTeamupRequest();
//        request.resumeWithCompletionHandler(new MyTeamupRequest.MyTeamupCompletionHandler<List<User>, List<User>>() {
//
//            @Override
//            public void completionHandler(List<User> historyUsers, List<User> incomingUsers, ServerRequest request) {
//                System.out.println();
//            }
//
//            @Override
//            public void completionHandlerWithError(String error) {
//                System.out.println();
//            }
//        });
    }
}
