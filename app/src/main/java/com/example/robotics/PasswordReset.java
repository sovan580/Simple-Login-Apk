package com.example.robotics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordReset extends AppCompatActivity {
    Button sent;
    EditText email;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);
        sent=findViewById(R.id.send);
        email=findViewById(R.id.link);
        mAuth=FirebaseAuth.getInstance();
        sent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterEmail();
            }
        });
    }

    private void enterEmail() {
        String userEmail=email.getText().toString();
        if(userEmail.isEmpty()){
            email.setError("Enter a Valid E-mail");
            email.requestFocus();
            return;
        }
        mAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(PasswordReset.this,"Reset link sent !!!",Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(PasswordReset.this,Login.class));

                }
                else{
                    Toast.makeText(PasswordReset.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
