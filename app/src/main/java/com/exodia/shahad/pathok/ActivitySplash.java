package com.exodia.shahad.pathok;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class ActivitySplash extends AppCompatActivity {

    private LoginButton fbLoginButton; //fb login button
    private AccessToken fbAccessToken;
    private CallbackManager fbCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //status bar color
        getWindow().setStatusBarColor(getResources().getColor(R.color.splash_back_notification));

        //find all the elements
        findElements();

        fbLoginButton.setReadPermissions(Arrays.asList("public_profile", "email"));


        if(!isLoggedInFb()){
            Toast.makeText(getApplicationContext(), "not", Toast.LENGTH_LONG).show();
            fbLoginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    logIntoFb();
                }
            });
        }
        //*************fb login handling*************
    }

    //-----------------------------------onCreate()_finishes------------------------------------------

    boolean isLoggedInFb(){
        fbAccessToken = AccessToken.getCurrentAccessToken();
        return fbAccessToken != null && !fbAccessToken.isExpired();
    }

    void logIntoFb(){
        fbCallbackManager = CallbackManager.Factory.create();
        fbLoginButton.setReadPermissions(Arrays.asList("public_profile", "email"));

        fbLoginButton.registerCallback(fbCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "canceled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    void findElements(){
        fbLoginButton = (LoginButton) findViewById(R.id.splash_fb_login_button);
    }

    //get facebook result from login activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        fbCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void getFbUserProfileData(AccessToken accessToken){
        GraphRequest graphRequest = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String userName = object.getString("name");
                    String userEmail = object.getString("email");

                    Log.e("fb infos, name", userName);
                    Log.e("fb infos, email", userEmail);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Bundle params = new Bundle();
        params.putString("fields", "name, email");
        graphRequest.setParameters(params);
        graphRequest.executeAsync();
    }

    AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if(currentAccessToken != null){
                getFbUserProfileData(currentAccessToken);
            }
        }
    };
}
