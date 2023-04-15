package com.example.authentification;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreenActivity extends AppCompatActivity {
        ImageView logo;
        Animation Splash_top, Splash_bottom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //hideactionbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


        initView();
        initEvent();

    }

    private void initView() {
        logo= findViewById(R.id.logo);
        //for animation
        Splash_top= AnimationUtils.loadAnimation(this, R.anim.splash_top);
        Splash_bottom= AnimationUtils.loadAnimation(this, R.anim.splash_bottom);
        logo.setAnimation(Splash_top);
    }

    public void initEvent(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Fetching the stored data from the SharedPreference
                SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                String s1 = sh.getString("email", "");
                Log.d(s1, "run: ");
                if(s1.equals("")){
                    Intent intent = new Intent(SplashScreenActivity.this,LoginActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(SplashScreenActivity.this,HomeActivity.class);
                    startActivity(intent);
                }

                finish();






            }
        },3000);

    }
}