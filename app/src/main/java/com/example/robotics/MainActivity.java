package com.example.robotics;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private  static  int SPLASH=2500;
    Animation top,bot;
    ImageView image;
    TextView m,v;
    FirebaseAuth mAuth;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        top = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bot = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        image = findViewById(R.id.imageView);
        m = findViewById(R.id.textView);
        v = findViewById(R.id.textView2);
        image.setAnimation(top);
        m.setAnimation(bot);
        v.setAnimation(bot);
    }
    @Override
    protected void onStart(){
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser User=mAuth.getCurrentUser();
                if(User!=null){
                    Intent i=new Intent(MainActivity.this,User.class);
                    startActivity(i);
                    finish();
                }else{
                Intent intent = new Intent(MainActivity.this,Login.class);
                startActivity(intent);
                finish();}
            }
        },SPLASH);
    }
}
