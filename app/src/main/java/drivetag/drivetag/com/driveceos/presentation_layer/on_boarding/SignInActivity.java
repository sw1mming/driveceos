package drivetag.drivetag.com.driveceos.presentation_layer.on_boarding;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;

import java.util.Arrays;

import drivetag.drivetag.com.driveceos.DTApplication;
import drivetag.drivetag.com.driveceos.R;
import drivetag.drivetag.com.driveceos.business_layer.FacebookSignInFlow;
import drivetag.drivetag.com.driveceos.business_layer.SignInFlow;
import drivetag.drivetag.com.driveceos.business_layer.TwitterSignInFlow;
import drivetag.drivetag.com.driveceos.data_layer.models.User;
import drivetag.drivetag.com.driveceos.data_layer.requests.ServerRequest;
import drivetag.drivetag.com.driveceos.presentation_layer.BaseActivity;
import drivetag.drivetag.com.driveceos.presentation_layer.MainActivity;


/**
 * Created by artem.
 */

public class SignInActivity extends BaseActivity {

    private EditText emailEditText;

    private EditText passwordEditText;

    private Button signInButton;

    private Button signUpButton;

    private ImageButton linkedInButton;

    private TextView forgotPasswordTextView;

    private FacebookSignInFlow facebookSignInFlow;

    /**
     * Interface.
     */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_sign_in);
        setupViews();
        fillViews();
        facebookSignInFlow = new FacebookSignInFlow((DTApplication) getApplicationContext());

        final String login = "rayban@mailinator.com";
        final String password = "qwe123546";
        emailEditText.setText(login);
        passwordEditText.setText(password);
//        PublicPostToMeRequest request = new PublicPostToMeRequest(true);
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

    public void twitterClick(View view) {
       new TwitterSignInFlow((DTApplication) getApplication(), new TwitterSignInFlow.FlowCompletionHandler() {
           @Override
           public void completionHandler(User user, String error) {
               // TODO: 30.04.2017  move
           }

           @Override
           public void completionHandlerWithError(String error) {

           }

           @Override
           public void switchCompletionHandler(User user) {

           }
       }).resumeWithCompletionHandler(this);
    }

    public void facebookClick(View view) {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
    }

    /**
     * Private.
     */

    private void setupViews() {
        emailEditText = (EditText) findViewById(R.id.email_edit_text);
        passwordEditText = (EditText) findViewById(R.id.password_edit_text);
        signInButton = (Button) findViewById(R.id.sign_in_button);
        signUpButton = (Button) findViewById(R.id.sign_up_button);
        linkedInButton = (ImageButton) findViewById(R.id.linked_in_image_button);
        forgotPasswordTextView = (TextView) findViewById(R.id.forgot_password_text_view);
    }

    private void fillViews() {
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                SignInFlow signInFlow = new SignInFlow(emailEditText.getText().toString(), passwordEditText.getText().toString());
                SignInFlow signInFlow = new SignInFlow("rayban@mailinator.com", "qwe123456", (DTApplication) getApplication());
                signInFlow.resumeWithComletionBlock(new SignInFlow.CompletionHandler() {
                    @Override
                    public void completionHandler(ServerRequest request) {
                        startActivity(new Intent(SignInActivity.this, MainActivity.class));
                    }

                    @Override
                    public void completionHandlerWithError(String error) {
                        Toast.makeText(SignInActivity.this, error, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            }
        });

        socialsActions();

        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, ForgotPasswordActivity.class));
            }
        });
    }

    private void socialsActions() {
        linkedInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SignInActivity.this, "linkedin api", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        facebookSignInFlow.onActivityResult(requestCode, resultCode, data);
    }
}
