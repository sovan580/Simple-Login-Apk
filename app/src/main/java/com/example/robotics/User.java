package com.example.robotics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class User extends AppCompatActivity {
    EditText fullname,email,phone,password;
    Button update,users,logout;
    DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    String Fname,Email,Phone,Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        fullname=findViewById(R.id.name);
        email=findViewById(R.id.mail);
        phone=findViewById(R.id.phone);
        password=findViewById(R.id.pa);
        update=findViewById(R.id.update);
        users=findViewById(R.id.users);
        logout=findViewById(R.id.logout);
        mAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference("person");
        firebaseUser=mAuth.getCurrentUser();
        showUserData();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fname=fullname.getText().toString();
                Email=email.getText().toString();
                Phone=phone.getText().toString();
                Password=password.getText().toString();
                if(Fname.isEmpty()){
                    fullname.setError("Please enter User's Name");
                    fullname.requestFocus();
                    return;
                }
                if(Email.isEmpty()){
                    email.setError("Please enter User's E-mail");
                    fullname.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
                    email.setError("Please enter a valid E-mail address");
                    fullname.requestFocus();
                    return;
                }
                if(Phone.isEmpty()){
                    phone.setError("Please enter User's Phone Number");
                    phone.requestFocus();
                    return;
                }
                if(Password.isEmpty()){
                    password.setError("Please enter a Password");
                    password.requestFocus();
                    return;
                }
                updateUserInfo();
                updateUserPassword();
                Toast.makeText(getApplicationContext(), "Login again to see Changes!!!", Toast.LENGTH_SHORT).show();
                finish();
                Intent intent=new Intent(User.this,Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(User.this,AllUsers.class);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent intent=new Intent(User.this,Login.class);
                startActivity(intent);
            }
        });
    }

    private void updateUserInfo() {
        final person Person=new person(Fname,Email,Phone);
        firebaseUser.updateEmail(Email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    databaseReference.child(mAuth.getCurrentUser().getUid()).setValue(Person).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                //Toast.makeText(User.this,"Information Updated",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    private void updateUserPassword(){
        firebaseUser.updatePassword(Password).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    //Toast.makeText(User.this,"Password Changed",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void showUserData() {
        databaseReference.child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                person Person=dataSnapshot.getValue(person.class);
                fullname.setText(Person.getFullname());
                email.setText(Person.getEmail());
                phone.setText(Person.getNumber());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}

