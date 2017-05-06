package drivetag.drivetag.com.driveceos.presentation_layer.on_boarding;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import drivetag.drivetag.com.driveceos.R;
import drivetag.drivetag.com.driveceos.data_layer.requests.ForgotPasswordRequest;
import drivetag.drivetag.com.driveceos.data_layer.requests.ServerRequest;

public class ForgotPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    public void sendClick(View view) {
        final String email = ((EditText) findViewById(R.id.email_edit_text)).getText().toString();
        new ForgotPasswordRequest(email).resumeWithCompletionHandler(new ServerRequest.ServerCompletionHandler() {
            @Override
            public void completionHandler(ServerRequest request) {
                Toast.makeText(ForgotPasswordActivity.this, "Email has been sent!", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void completionHandlerWithError(String error) {
                Toast.makeText(ForgotPasswordActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void cancelClick(View view) {
        finish();
    }
}
