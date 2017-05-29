package com.simpleideas.gymmate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;

import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.auth.FirebaseAuth;
import com.simpleideas.gymmate.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Geprge on 3/27/2017.
 */

public class FacebookActivityMyOwnImplementation extends AppCompatActivity implements View.OnClickListener{

    CallbackManager mCallBackManager;
    String TAG = "FacebookActivityCustom";
    private ProfileTracker mProfileTracker;
    private Button singUpViaemail, btn_fb_login;
    private TextView alreadyRegistered;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facebook_activity_layout);
        singUpViaemail = (Button) findViewById(R.id.viaEmail);
        btn_fb_login  = (Button) findViewById(R.id.sign_up);
        //alreadyRegistered  = (TextView) findViewById(R.id.alreadyRegistered);
        if (getSharedPreferences("bitmap", MODE_PRIVATE).contains("bitmap") == true){

            Intent firstIntent  = new Intent(FacebookActivityMyOwnImplementation.this, StartActivity.class);
            startActivity(firstIntent);
            finish();

        }else{
            createBehaviourForFacebook();
        }


    }

    public void createBehaviourForFacebook(){

        singUpViaemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FacebookActivityMyOwnImplementation.this, SingUpActivity.class));
            }
        });
        btn_fb_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(FacebookActivityMyOwnImplementation.this, Arrays.asList("public_profile", "user_friends"));
            }
        });

//        alreadyRegistered.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(FacebookActivityMyOwnImplementation.this, SignInActivity.class));
//                finish();
//            }
//        });


        mCallBackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mCallBackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                if (Profile.getCurrentProfile() == null){

                    mProfileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                            Log.d(TAG, "onCurrentProfileChanged: " + currentProfile.getFirstName());
                            Log.d(TAG, "onCurrentProfileChanged: " + currentProfile.getLastName());
                            Log.d(TAG, "onCurrentProfileChanged: " + currentProfile.getId());
                            saveFacebookProfilePicture(currentProfile);
//                            Intent tranzitionIntent = new Intent(FacebookActivityMyOwnImplementation.this, StartActivity.class);
//                            tranzitionIntent.putExtra("facebookFlag", true);
//                            startActivity(tranzitionIntent);
//                            finish();

                            mProfileTracker.stopTracking();
                        }
                    };

                }

                else {
                    Profile profile = Profile.getCurrentProfile();
                    saveFacebookProfilePicture(profile);
                    Intent tranzitionIntent = new Intent(FacebookActivityMyOwnImplementation.this, StartActivity.class);
                    tranzitionIntent.putExtra("facebookFlag", true);
                    startActivity(tranzitionIntent);
                    finish();
                    Log.d(TAG, "onSuccess: " + profile.getFirstName());
                }
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mCallBackManager.onActivityResult(requestCode, resultCode, data)){
            Intent tranzitionIntent = new Intent(FacebookActivityMyOwnImplementation.this, StartActivity.class);
            tranzitionIntent.putExtra("facebookFlag", true);
            startActivity(tranzitionIntent);
            finish();
        }


    }

    private void saveFacebookProfilePicture(final Profile profile){
        new Thread(new Runnable() {

            @Override
            public void run() {
                try{
                    URL imageUrl = new URL("https://graph.facebook.com/" + profile.getId() + "/picture?type=large");
                    Bitmap bitmap = BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream());
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

                    byte[] bitmapByteArray = baos.toByteArray();

                    String encodedBitmap = Base64.encodeToString(bitmapByteArray, Base64.DEFAULT);

                    SharedPreferences preferences = getSharedPreferences(Constants.bitmap,MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();

                    editor.putString("bitmap", encodedBitmap);
                    editor.putString("firstname", profile.getFirstName());
                    editor.putString("lastname", profile.getLastName());

                    editor.apply();

                    SharedPreferences firstFlat = getSharedPreferences("Dates", MODE_PRIVATE);
                    SharedPreferences.Editor firstF = firstFlat.edit();
                    firstF.putString("dates","dates");
                    firstF.apply();

                }catch(Exception e)
                {
                    Log.e(TAG, ""+e.getMessage());
                }



            }

        }).start();


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.viaEmail){

            startActivity(new Intent(FacebookActivityMyOwnImplementation.this, SingUpActivity.class));

        }
    }
}

