package com.simpleideas.gymmate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.facebook.Profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;

import java.io.ByteArrayOutputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class SingUpActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    private EditText email, password;

    private TextView alreadyHere;

    private Button register;

    private final String TAG = "SignUpActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        email = (EditText) findViewById(R.id.emailT);
        password = (EditText) findViewById(R.id.passwordT);
        register = (Button) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

    }



    public void registerUser(){

        String emailS = email.getText().toString().trim();
        String passwordS = password.getText().toString().trim();

        if (TextUtils.isEmpty(emailS)){

            Toast.makeText(this, "Please insert a email adress", Toast.LENGTH_SHORT).show();

            return;
        }

        if (TextUtils.isEmpty(passwordS)){

            Toast.makeText(this, "Please insert a password", Toast.LENGTH_SHORT).show();

            return;
        }

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(emailS, passwordS).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (!task.isSuccessful()){
                    Toast.makeText(SingUpActivity.this, "Nothing happened", Toast.LENGTH_SHORT).show();
                }

                if (task.isSuccessful()){

                    UserInfo user = firebaseAuth.getCurrentUser();

//                    try {
//                        URL userPhotoUrl = new URL(user.getPhotoUrl().getAuthority(),user.getPhotoUrl().getHost(),user.getPhotoUrl().getFragment());
//                        saveFacebookProfilePicture(userPhotoUrl);
//                    } catch (MalformedURLException e) {
//                        e.printStackTrace();
//                    }

                    Toast.makeText(SingUpActivity.this, "Registration succesfull!", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(SingUpActivity.this, StartActivity.class)
//                    .putExtra("displayName", user.getDisplayName()));

                }

            }
        });

    }

    private void saveFacebookProfilePicture(final URL imageURL){
        new Thread(new Runnable() {

            @Override
            public void run() {
                try{
                    Bitmap bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

                    byte[] bitmapByteArray = baos.toByteArray();

                    String encodedBitmap = Base64.encodeToString(bitmapByteArray, Base64.DEFAULT);

                    SharedPreferences preferences = getSharedPreferences(Constants.bitmapP,MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();

                    editor.putString("firebaseBitmap", encodedBitmap);

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
