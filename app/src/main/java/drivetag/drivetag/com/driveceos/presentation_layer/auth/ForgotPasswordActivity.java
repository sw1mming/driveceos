package drivetag.drivetag.com.driveceos.presentation_layer.auth;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

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
        WebView webview = (WebView) findViewById(R.id.webView);
        webview.loadUrl("http://google.com");
    }
}
