package com.example.workflowpro;

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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private EditText loginEmailEditText, loginPasswordEditText;
    private Button btnLogin;
    private TextView signUpRedirectText;
    String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        loginEmailEditText = findViewById(R.id.login_email_edit_text);
        loginPasswordEditText = findViewById(R.id.login_password_edit_text);
        btnLogin = findViewById(R.id.login_button);
        signUpRedirectText = findViewById(R.id.sign_up_redirect_text);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userEmail = loginEmailEditText.getText().toString();
                String userPassword = loginPasswordEditText.getText().toString();
/*
                if (!validateUserEmail() || !validatePassword()) {
                } else {
                    checkUser();
                }*/

                if (!userEmail.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
                    if (!userPassword.isEmpty()) {
                        firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        //Intent intent = getIntent();
                                        // String userEmail = intent.getStringExtra("USER_EMAIL");
                                        verifyEmailAddress();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        loginPasswordEditText.setError("Empty fields are not allowed");
                    }
                } else if (userEmail.isEmpty()) {
                    loginEmailEditText.setError("Empty fields are not allowed");
                } else {
                    loginEmailEditText.setError("Please enter correct email");
                }
            }
        });
        signUpRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
    }

    public Boolean validateUserEmail() {
        String theUserEmail = loginEmailEditText.getText().toString();
        if (theUserEmail.isEmpty()) {
            loginEmailEditText.setError("Email cannot be empty");
            return false;
        } else {
            loginEmailEditText.setError(null);
            return true;
        }
    }

    public Boolean validatePassword() {
        String theUserPassword = loginPasswordEditText.getText().toString();
        if (theUserPassword.isEmpty()) {
            loginPasswordEditText.setError("Password cannot be empty");
            return false;
        } else {
            loginPasswordEditText.setError(null);
            return true;
        }
    }

    public void checkUser() {
        String userEmail = loginEmailEditText.getText().toString().trim();
        String userPassword = loginPasswordEditText.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("userWorkEmail").equalTo(userEmail);
        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    loginEmailEditText.setError(null);
                    String theEncodedUserEmail = encodeEmail(userEmail);
                    String passwordFromDB = snapshot.child(theEncodedUserEmail).child("userPassword").getValue(String.class);
                    if (passwordFromDB.equals(userPassword)) {
                        loginEmailEditText.setError(null);
                        String userCivilityFromDB = snapshot.child(theEncodedUserEmail).child("userCivility").getValue(String.class);
                        String userWorkEmailFromDB = snapshot.child(theEncodedUserEmail).child("userWorkEmail").getValue(String.class);
                        String userPasswordFromDB = snapshot.child(theEncodedUserEmail).child("userPassword").getValue(String.class);
                        String userFirstNameFromDB = snapshot.child(theEncodedUserEmail).child("userFirstName").getValue(String.class);
                        String userLastNameFromDB = snapshot.child(theEncodedUserEmail).child("userLastName").getValue(String.class);
                        String userBirthDayFromDB = snapshot.child(theEncodedUserEmail).child("userBirthDay").getValue(String.class);
                        String userAddressFromDB = snapshot.child(theEncodedUserEmail).child("userAddress").getValue(String.class);
                        String userPhoneNumberFromDB = snapshot.child(theEncodedUserEmail).child("userPhoneNumber").getValue(String.class);
                        String userCompanyNameFromDB = snapshot.child(theEncodedUserEmail).child("userCompanyName").getValue(String.class);
                        String userTitleFromDB = snapshot.child(theEncodedUserEmail).child("userTitle").getValue(String.class);

                        Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                        intent.putExtra("CIVILITY", userCivilityFromDB);
                        intent.putExtra("WORK_EMAIL", userWorkEmailFromDB);
                        intent.putExtra("PASSWORD", userPasswordFromDB);
                        intent.putExtra("FIRST_NAME", userFirstNameFromDB);
                        intent.putExtra("LAST_NAME", userLastNameFromDB);
                        intent.putExtra("BIRTH_DAY", userBirthDayFromDB);
                        intent.putExtra("ADDRESS", userAddressFromDB);
                        intent.putExtra("PHONE_NUMBER", userPhoneNumberFromDB);
                        intent.putExtra("COMPANY_NAME", userCompanyNameFromDB);
                        intent.putExtra("TITLE", userTitleFromDB);
                        startActivity(intent);
                    } else {
                        loginPasswordEditText.setError("Invalid Credentials");
                        loginPasswordEditText.requestFocus();
                    }
                } else {
                    loginEmailEditText.setError("User does not exist");
                    loginEmailEditText.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public String encodeEmail(String email) {
        return email.replace(".", ",").replace("@", "_");
    }

    public String decodeEmail(String encodedEmail) {
        return encodedEmail.replace(",", ".").replace("_", "@");
    }

    private void verifyEmailAddress() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser.isEmailVerified()) {
            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String userTypes = snapshot.child(encodeEmail(userEmail)).child("userTypes").getValue(String.class);

                    if (userTypes.equals("Technician")) {
                        Intent intentToTechnicianActivity = new Intent(LoginActivity.this, TechnicianActivity.class);
                        startActivity(intentToTechnicianActivity);
                    } else if (userTypes.equals("Limited Technician")) {
                        Intent intentToLimitedTechnician = new Intent(LoginActivity.this, LimitedTechnicianActivity.class);
                        startActivity(intentToLimitedTechnician);
                    } else if (userTypes.equals("View Only")) {
                        Intent intentToViewOnlyActivity = new Intent(LoginActivity.this, ViewOnlyActivity.class);
                        startActivity(intentToViewOnlyActivity);
                    } else if (userTypes.equals("Administrator")) {
                        Intent intentToAdministratorActivity = new Intent(LoginActivity.this, AdministratorActivity.class);
                        startActivity(intentToAdministratorActivity);
                    } else {
                        Intent intentToRequesterActivity = new Intent(LoginActivity.this, RequesterActivity.class);
                        startActivity(intentToRequesterActivity);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Please, verify your account", Toast.LENGTH_LONG).show();
        }
    }
}