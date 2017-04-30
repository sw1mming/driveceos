package drivetag.drivetag.com.driveceos.presentation_layer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import drivetag.drivetag.com.driveceos.R;
import drivetag.drivetag.com.driveceos.presentation_layer.on_boarding.SignInActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        // TODO: auth logic, if logged - go to main else go to sign in
        startActivity(new Intent(this, SignInActivity.class));
    }
}
