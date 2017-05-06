package drivetag.drivetag.com.driveceos.presentation_layer.on_boarding;

import android.content.Intent;
import android.net.UrlQuerySanitizer;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import drivetag.drivetag.com.driveceos.R;

public class ResetPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        Intent intent = getIntent();
        String providerUrl = intent.getData().toString();
        UrlQuerySanitizer sanitizer = new UrlQuerySanitizer();
        sanitizer.setAllowUnregisteredParamaters(true);
        sanitizer.parseUrl(providerUrl);
        String code = sanitizer.getValue("code");
    }

    public void confirmClick(View view) {
        final String password = ((EditText) findViewById(R.id.password_edit_text)).getText().toString();
        final String confirmPassword = ((EditText) findViewById(R.id.confirm_password_edit_text)).getText().toString();
        Toast.makeText(this, "Isn't ready yet", Toast.LENGTH_SHORT).show();
    }

    public void cancelClick(View view) {
        finish();
    }
}
