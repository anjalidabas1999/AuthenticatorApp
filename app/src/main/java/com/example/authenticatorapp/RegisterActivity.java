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

public class RegisterActivity extends AppCompatActivity {
    EditText fullname,email,psswrd,phn;
    Button register;
    TextView login;
    FirebaseAuth fAuth;
    ProgressBar prg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        fullname=findViewById(R.id.eTfull);
        email=findViewById(R.id.eTemail);
        psswrd=findViewById(R.id.eTpswrd);
        phn=findViewById(R.id.eTphn);
        register=findViewById(R.id.Btnregister);
        login=findViewById(R.id.tVlogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });

        fAuth=FirebaseAuth.getInstance();
        prg=findViewById(R.id.pgb);


        if(fAuth.getCurrentUser()!=null){
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail=email.getText().toString().trim();
                String psd=psswrd.getText().toString().trim();
                if(TextUtils.isEmpty(mail)){
                    email.setError("Email is required");
                    return;
                }
                if(TextUtils.isEmpty(psd)){
                    psswrd.setError("Password is required");
                    return;
                }
                if(psd.length()<6){
                    psswrd.setError("Password must be >=6 characters");
                    return;
                }
                prg.setVisibility(View.VISIBLE);
                //register the user in firebase
                fAuth.createUserWithEmailAndPassword(mail,psd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "User Created.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);

                        }else{
                            Toast.makeText(RegisterActivity.this, "Error!!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            prg.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });



    }
}
