package drivetag.drivetag.com.driveceos.presentation_layer.linked_in_login_fragment;

import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ClientCertRequest;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import drivetag.drivetag.com.driveceos.R;

public class LinkedInLoginFragment extends Fragment {

    WebView webView;

    public LinkedInLoginFragment() {
    }

    public static LinkedInLoginFragment newInstance(String userId) {
        LinkedInLoginFragment viewPreferenceFragment = new LinkedInLoginFragment();
        Bundle bundle = new Bundle();
        bundle.putString("userId", userId);
        viewPreferenceFragment.setArguments(bundle);

        return viewPreferenceFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_linked_in_login, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final String userId = getArguments().getString("userId");
        Toast.makeText(getActivity(), userId, Toast.LENGTH_SHORT).show();

        webView = (WebView)getActivity().findViewById(R.id.linked_in_web_view);


        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("https://dev.drivetagdev.com/api/auth/linkedin/loginpopup");
        webView.setWebViewClient(new AppWebViewClient());
    }

    public class AppWebViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
//            setProgressBar(false);
//            view.loadUrl("document.body.innerHTML");


//            Uri url1 = Uri.parse(webView.getUrl());
//            Set<String> paramNames = url1.getQueryParameterNames();
//
//            HashMap<String, String> parameters = new HashMap<>();
//
//            for (String key: paramNames) {
//                String value = url1.getQueryParameter(key);
//                parameters.put(key, value);
//                System.out.println();
//            }
//            System.out.println();
//            try {

            view.evaluateJavascript(
                    "document.body.innerHTML",
                    new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String html) {
                            Log.d("HTML", html);
                            String a = " \\\"result\\\":\\\"logged\\\"";
                            Uri url1 = Uri.parse(a);

                            List<String> sss = url1.getQueryParameters("dt_access_token");

                            Set<String> paramNames = url1.getQueryParameterNames();
                            HashMap<String, String> parameters = new HashMap<>();
                            for (String key: paramNames) {
                                String value = url1.getQueryParameter(key);
                                parameters.put(key, value);
                                System.out.println();
                            }
                        }

                        //
                    });
        }
    }
}
