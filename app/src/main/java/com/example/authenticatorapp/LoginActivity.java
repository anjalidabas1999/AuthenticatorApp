package com.example.authenticatorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText email,password;
    Button btn;
    TextView txt;
    ProgressBar per;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email=findViewById(R.id.eTemail);
        password=findViewById(R.id.eTpswrd);
        btn=findViewById(R.id.Btnlogin);
        per=findViewById(R.id.prog);
        txt=findViewById(R.id.tVcreate);
        fAuth=FirebaseAuth.getInstance();

        txt.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
             }
         });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail=email.getText().toString().trim();
                String psd=password.getText().toString().trim();
                if(TextUtils.isEmpty(mail)){
                    email.setError("Email is required");
                    return;
                }
                if(TextUtils.isEmpty(psd)){
                    password.setError("Password is required");
                    return;
                }
                /*if(psd.length()<6){
                    password.setError("Password must be >=6 characters");
                    return;
                }*/

                per.setVisibility(View.VISIBLE);
                // Authenticate the user

                fAuth.signInWithEmailAndPassword(mail,psd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Logged In Successfully!!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else{
                            Toast.makeText(LoginActivity.this, "Error!!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            per.setVisibility(View.GONE);

                        }
                    }
                });
            }
        });


    }
}
