package com.example.workflowpro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {

    ImageView workFlowImageView;
    TextView appNameTextView;
    TextView textualLogoTextView;
    Animation translateBottom, translateTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        workFlowImageView = findViewById(R.id.work_flow_image_view);
        appNameTextView = findViewById(R.id.app_name_text_view);
        textualLogoTextView = findViewById(R.id.textual_logo_text_view);


        translateBottom = AnimationUtils.loadAnimation(this, R.anim.translate_bottom);
        translateTop = AnimationUtils.loadAnimation(this, R.anim.translate_top);
        workFlowImageView.setAnimation(translateTop);
        appNameTextView.setAnimation(translateBottom);
        textualLogoTextView.setAnimation(translateBottom);

        int SPLASH_SCREEN = 3500;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intentToMainActivity = new Intent(SplashScreenActivity.this, IntroActivity.class);
                startActivity(intentToMainActivity);
                finish();
            }
        }, SPLASH_SCREEN);
    }
}