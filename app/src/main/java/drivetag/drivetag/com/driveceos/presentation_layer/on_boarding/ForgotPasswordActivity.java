package drivetag.drivetag.com.driveceos.presentation_layer.on_boarding;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import drivetag.drivetag.com.driveceos.R;

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
        Toast.makeText(this, "Isn't ready yet", Toast.LENGTH_SHORT).show();
    }

    public void cancelClick(View view) {
        finish();
    }
}
