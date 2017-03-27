package drivetag.drivetag.com.driveceos.presentation_layer.on_boarding;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import drivetag.drivetag.com.driveceos.BaseActivity;
import drivetag.drivetag.com.driveceos.R;
import drivetag.drivetag.com.driveceos.business_layer.SignInFlow;
import drivetag.drivetag.com.driveceos.data_layer.requests.ServerRequest;


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
                        System.out.println();
                    }

                    @Override
                    public void completionHandlerWithError(String error) {
                        System.out.println();
                    }
                });
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: show Sign Up screen;
                System.out.println();
            }
        });

        socialsActions();

        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: show WebView;
                System.out.println();
            }
        });
    }

    private void socialsActions() {
        facebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: call Facebook flow;
                System.out.println();
            }
        });

        twitterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: call Twitter flow;
                System.out.println();
            }
        });

        linkedInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: call LinkedIn flow;
                System.out.println();
            }
        });
    }
}
