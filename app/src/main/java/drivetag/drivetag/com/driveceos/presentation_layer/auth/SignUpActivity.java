package drivetag.drivetag.com.driveceos.presentation_layer.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import drivetag.drivetag.com.driveceos.presentation_layer.BaseActivity;
import drivetag.drivetag.com.driveceos.R;

/**
 * Created by artem on 3/24/17.
 */

public class SignUpActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, SignInActivity.class));
    }
}
