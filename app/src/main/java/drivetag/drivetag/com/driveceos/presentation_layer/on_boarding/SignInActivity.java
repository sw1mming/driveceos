package drivetag.drivetag.com.driveceos.presentation_layer.on_boarding;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
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

public class SignInActivity extends BaseActivity {

    private FacebookSignInFlow facebookSignInFlow;

    /**
     * Interface.
     */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_sign_in);
        facebookSignInFlow = new FacebookSignInFlow((DTApplication) getApplicationContext());
        facebookSignInFlow.registerFacebookCallback();
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

    public void linkedInClick(View view) {
        Toast.makeText(SignInActivity.this, "linkedIn api", Toast.LENGTH_SHORT).show();
    }

    public void facebookClick(View view) {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
    }

    public void signUpClick(View view) {
        startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
    }

    public void forgotClick(View view) {
        startActivity(new Intent(SignInActivity.this, ForgotPasswordActivity.class));
    }

    public void signInClick(View view) {
        final EditText emailEditText = (EditText) findViewById(R.id.email_edit_text);
        final EditText passwordEditText = (EditText) findViewById(R.id.password_edit_text);
        String login = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        // TODO: remove in production
        if (TextUtils.isEmpty(login)) {
            login = "rayban@mailinator.com";
        }
        if (TextUtils.isEmpty(password)) {
            password = "qwe123456";
        }
        final SignInFlow signInFlow = new SignInFlow(login, password, (DTApplication) getApplication());
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        facebookSignInFlow.onActivityResult(requestCode, resultCode, data);
    }
}
