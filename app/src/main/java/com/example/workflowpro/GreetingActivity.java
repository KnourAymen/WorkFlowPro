package com.example.workflowpro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GreetingActivity extends AppCompatActivity {

    Button btnLogin, btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greeting);

        btnLogin = findViewById(R.id.login_button);
        btnSignUp = findViewById(R.id.sign_up_button);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToLoginActivity = new Intent(GreetingActivity.this, LoginActivity.class);
                startActivity(intentToLoginActivity);
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToSignUpActivity = new Intent(GreetingActivity.this, SignUpActivity.class);
                startActivity(intentToSignUpActivity);
            }
        });
    }
}