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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Objects;

public class ActivitySplash extends AppCompatActivity {

    //fb login essentials
    private LoginButton fbLoginButton;
    private AccessToken fbAccessToken;
    private CallbackManager fbCallbackManager;

    //google sign in essentials
    private GoogleSignInAccount account;
    private GoogleSignInClient googleSignInClient;
    private SignInButton googleSignInButton;
    private static final int GOOGLE_LOGIN_RC = 2019;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //status bar color
        getWindow().setStatusBarColor(getResources().getColor(R.color.splash_back_notification));

        //find all the elements
        findElements();

        //request google user's information
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);


        //check if someone is logged in or not
        if(isLoggedInGoole() || isLoggedInFb()){ //logged in
            //hide the login/sign in buttons
            fbLoginButton.setVisibility(View.GONE);
            googleSignInButton.setVisibility(View.GONE);


        }else{ //not logged in
            fbLoginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    logIntoFb();
                }
            });
            googleSignInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    signIntoGoole();
                }
            });
        }
    }

    //-----------------------------------onCreate()_finishes------------------------------------------

    void findElements(){
        fbLoginButton = (LoginButton) findViewById(R.id.splash_fb_login_button);
        googleSignInButton = (SignInButton) findViewById(R.id.splash_google_sign_in_button);
    }

    boolean isLoggedInFb(){
        fbAccessToken = AccessToken.getCurrentAccessToken();
        return fbAccessToken != null && !fbAccessToken.isExpired();
    }

    boolean isLoggedInGoole(){
        account = GoogleSignIn.getLastSignedInAccount(this);
        return account != null;
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

    void signIntoGoole(){
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, GOOGLE_LOGIN_RC);
    }


    //get facebook result from login activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GOOGLE_LOGIN_RC){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleGoogleSignInResult(task);
        }else{
            fbCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleGoogleSignInResult(Task<GoogleSignInAccount> completedTask){
        try {
            account = completedTask.getResult(ApiException.class);
            getGoogleUserProfileData();
        } catch (ApiException e) {
            Log.w("splash", "signInResult:failed code=" + e.getStatusCode());
        }
    }

    private void getGoogleUserProfileData(){
        account = GoogleSignIn.getLastSignedInAccount(this);

        if(account != null){
            String name = Objects.requireNonNull(account).getDisplayName();
            String email = account.getEmail();
            String imageUrl = Objects.requireNonNull(account.getPhotoUrl()).toString();

            Log.e("google infos, name", name);
            Log.e("google infos, email", email);
            Log.e("google infos, image", imageUrl);
        }
    }

    private void getFbUserProfileData(AccessToken accessToken){
        GraphRequest graphRequest = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String userName = object.getString("name");
                    String userEmail = object.getString("email");

                    Profile profile = Profile.getCurrentProfile();
                    String userImage = profile.getProfilePictureUri(50,50).toString();

                    Log.e("fb infos, name", userName);
                    Log.e("fb infos, email", userEmail);
                    Log.e("fb infos, image", userImage);
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
