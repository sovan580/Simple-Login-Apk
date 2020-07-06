package com.example.robotics;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.util.Patterns;

public class SignUp extends AppCompatActivity {
    TextInputLayout txt_fullname, txt_email, txt_password, num;
    Button signup;
    DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    FirebaseUser User;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        txt_fullname = findViewById(R.id.fname);
        txt_email = findViewById(R.id.email);
        txt_password = findViewById(R.id.pass);
        num = findViewById(R.id.number);
        signup = findViewById(R.id.up);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("person");

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               registerUser();
            }
        });

    }

    private void registerUser() {
        final String fullname=txt_fullname.getEditText().getText().toString().trim();
        final String email=txt_email.getEditText().getText().toString().trim();
        final String phone=num.getEditText().getText().toString().trim();
        String password=txt_password.getEditText().getText().toString().trim();
        if(fullname.isEmpty()){
            txt_fullname.setError("Please enter Full Name");
            txt_fullname.requestFocus();
            return;
        }
        if(email.isEmpty()){
            txt_fullname.setError("Please enter E-mail");
            txt_fullname.requestFocus();
            return;
        }
        if(phone.isEmpty()){
            txt_fullname.setError("Please enter Phone Number");
            txt_fullname.requestFocus();
            return;
        }
        if(password.isEmpty()){
            txt_fullname.setError("Please enter Password");
            txt_fullname.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            txt_email.setError("Enter a valid Email");
            txt_email.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    person  user=new person(fullname,email,phone);
                    databaseReference.child(mAuth.getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                          sendEmailVerification();
                        }
                    });

                }
                else {
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }

        });

    }

    private void sendEmailVerification() {
        User=mAuth.getCurrentUser();
    if(User!=null){
        User.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    Toast.makeText(SignUp.this,"Sign Up Successful. Verification link sent!!!",Toast.LENGTH_SHORT).show();
                    finish();
                    Intent intent=new Intent(SignUp.this,Login.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(SignUp.this,"Verification link has not been sent !!!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    }
}
