package com.simpleideas.gymmate;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    private EditText email, password;

    private Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        firebaseAuth = FirebaseAuth.getInstance();
        email = (EditText) findViewById(R.id.emailIn);
        password = (EditText) findViewById(R.id.passwordIn);

        login = (Button) findViewById(R.id.signin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser(email.getText().toString().trim(),password.getText().toString().trim());
            }
        });

    }




    private void loginUser(String email, String password){

        if (TextUtils.isEmpty(email)){

            Toast.makeText(this, "Please insert a email adress", Toast.LENGTH_SHORT).show();

            return;
        }

        if (TextUtils.isEmpty(password)){

            Toast.makeText(this, "Please insert a password", Toast.LENGTH_SHORT).show();

            return;
        }

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                startActivity(new Intent(SignInActivity.this, StartActivity.class));
                finish();
            }
        });


    }
}
