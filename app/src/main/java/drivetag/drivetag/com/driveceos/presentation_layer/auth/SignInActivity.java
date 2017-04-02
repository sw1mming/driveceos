package drivetag.drivetag.com.driveceos.presentation_layer.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import drivetag.drivetag.com.driveceos.R;
import drivetag.drivetag.com.driveceos.business_layer.SignInFlow;
import drivetag.drivetag.com.driveceos.data_layer.requests.ServerRequest;
import drivetag.drivetag.com.driveceos.presentation_layer.BaseActivity;
import drivetag.drivetag.com.driveceos.presentation_layer.MainActivity;


/**
 * Created by artem on 3/24/17.
 */

public class SignInActivity extends BaseActivity {

    private EditText emailEditText;

    private EditText passwordEditText;

    private Button signInButton;

    private Button signUpButton;

    private ImageButton facebookButton;

    private ImageButton twitterButton;

    private ImageButton linkedInButton;

    private TextView forgotPasswordTextView;


    /** Interface. */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        setupViews();
        fillViews();
    }


    /** Private. */

    private void setupViews () {
        emailEditText = (EditText) findViewById(R.id.email_edit_text);
        passwordEditText = (EditText) findViewById(R.id.password_edit_text);
        signInButton = (Button) findViewById(R.id.sign_in_button);
        signUpButton = (Button) findViewById(R.id.sign_up_button);
        facebookButton = (ImageButton) findViewById(R.id.facebook_image_button);
        twitterButton = (ImageButton) findViewById(R.id.twitter_image_button);
        linkedInButton = (ImageButton) findViewById(R.id.linked_in_image_button);
        forgotPasswordTextView = (TextView) findViewById(R.id.forgot_password_text_view);
    }

    private void fillViews() {
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignInFlow signInFlow = new SignInFlow(emailEditText.getText().toString(), passwordEditText.getText().toString());
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
        facebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SignInActivity.this, "facebook api", Toast.LENGTH_SHORT).show();
            }
        });

        twitterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SignInActivity.this, "twitter api", Toast.LENGTH_SHORT).show();
            }
        });

        linkedInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SignInActivity.this, "linkedin api", Toast.LENGTH_SHORT).show();
            }
        });
    }
}