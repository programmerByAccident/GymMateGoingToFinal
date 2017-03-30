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

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.simpleideas.gymmate.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * Created by Geprge on 3/27/2017.
 */

public class FacebookActivityMyOwnImplementation extends AppCompatActivity{

    CallbackManager mCallBackManager;
    String TAG = "FacebookActivityCustom";
    private ProfileTracker mProfileTracker;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facebook_activity_layout);

        if (getSharedPreferences("bitmap", MODE_PRIVATE).contains("bitmap") == true){

            Intent firstIntent  = new Intent(FacebookActivityMyOwnImplementation.this, StartActivity.class);
            startActivity(firstIntent);
            finish();

        }else{
            createBehaviourForFacebook();
        }


    }

    public void createBehaviourForFacebook(){

        LoginButton loginButton = (LoginButton) findViewById(R.id.login);
        loginButton.setReadPermissions(new ArrayList<String>());
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
                            Intent tranzitionIntent = new Intent(FacebookActivityMyOwnImplementation.this, StartActivity.class);
                            tranzitionIntent.putExtra("facebookFlag", true);
                            startActivity(tranzitionIntent);
                            finish();

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
            return;
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
}

