package co.lesha.vkchat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.MalformedURLException;
import java.net.URL;

import Util.Constants;
public class OAuthActivity extends AppCompatActivity {

    private static final String TAG = "OAuthActivity";
    private static final String PREFS_OAUTH = "oauth";
    private static final String PREFS_OAUTH_TOKEN = "access_token";
    private static final String PREFS_OAUTH_EXPIRES_IN = "expires_in";
    private static final String PREFS_OAUTH_EXPIRY = "validUntil";
    private SharedPreferences sharedPreferences;


    private String getState() {
        return String.valueOf(Math.round(Math.random() * 1000000) % 1000000);
    }

    private String getOauthUrl() {
        //return String.format("%s?client_id=%s&display=page&redirect_uri=%s&scope=friends,messages&response_type=token&v=5.69&state=%s",
        return String.format("%s?client_id=%s&display=page&redirect_uri=%s&scope=offline,friends,messages&response_type=token&v=5.69&state=%s",
                Constants.OAUTH_URL, Constants.CLIENT_ID, Constants.REDIRECT_URL, getState());
    }

    private String getAccessToken(String redirect) {
        try {
            URL url = new URL(redirect);
            if (url.getPath().equals(Constants.REDIRECT_PATH)) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String ref = url.getRef();
                String access_token = "";
                String expires_in = "0";
                for (String s : ref.split("&")) {
                    String[] kv = s.split("=");
                    editor.putString(kv[0], kv[1]);
                    if (kv[0].equals(PREFS_OAUTH_TOKEN)) {
                        access_token = kv[1];
                    } else if (kv[0].equals(PREFS_OAUTH_EXPIRES_IN)) {
                        expires_in = kv[1];
                    }
                }

                Long tsLong = System.currentTimeMillis()/1000 + Long.valueOf(expires_in);

                editor.putLong(PREFS_OAUTH_EXPIRY, tsLong);
                editor.apply();
                return access_token;
            } else return "";
        } catch (MalformedURLException e) {
            return "";
        }
    }

    private void goToChatList(String token) {
        Intent I = new Intent(OAuthActivity.this, ChatListActivity.class);
        I.putExtra(Constants.TOKEN, token);
        startActivity(I);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences(PREFS_OAUTH, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(PREFS_OAUTH_TOKEN, "");

        if (!token.equals("")) {
            // check for expiration;
            Long tsLong = System.currentTimeMillis()/1000;
            Long validUntil = sharedPreferences.getLong(PREFS_OAUTH_EXPIRY, 0);
            if (tsLong < validUntil) {
                goToChatList(token);
                return;
            }
        }

        setContentView(R.layout.activity_oauth);
        WebView wv = findViewById(R.id.oauth_webview);
        wv.loadUrl(getOauthUrl());
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String url) {
                String token = getAccessToken(url);
                if (token.equals("")) {
                    return false;
                }
                Log.d(TAG, "shouldOverrideUrlLoading: "+ token);
                goToChatList(token);
                return true;
            }
        });
    }
}
