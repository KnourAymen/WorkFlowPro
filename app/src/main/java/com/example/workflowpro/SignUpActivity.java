package com.example.workflowpro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

public class SignUpActivity extends AppCompatActivity {

    private CustomEditText phoneNumberEditText, signUpBirthDayEditText;
    private CountryCodePicker countryCodePicker;
    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_NUMBERS = 1;
    boolean bool = true;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private EditText signUpEmailEditText, signUpPasswordEditText, signUpFirstNameEditText, signUpLastNameEditText, signUpAddressEditText, signUpCompanyNameEditText, signUpTitleEditText;
    private Button btnSignUp;
    private TextView loginRedirectText;
    String[] civilityItems = {"Madam", "Sir", "Miss"};
    String[] userTypesItems = {"Administrator", "Technician", "Limited Technician", "View Only", "Requester"};
    AutoCompleteTextView signUpCivilityEditText, signUpUserTypesEditText;
    ArrayAdapter<String> civilityArrayAdapter;
    ArrayAdapter<String> userTypesArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        signUpCivilityEditText = findViewById(R.id.sign_up_civility_edit_text);
        signUpEmailEditText = findViewById(R.id.sign_up_email_edit_text);
        signUpPasswordEditText = findViewById(R.id.sign_up_password_edit_text);
        signUpFirstNameEditText = findViewById(R.id.sign_up_first_name_edit_text);
        signUpLastNameEditText = findViewById(R.id.sign_up_last_name_edit_text);
        signUpBirthDayEditText = findViewById(R.id.sign_up_birth_day_edit_text);
        signUpAddressEditText = findViewById(R.id.sign_up_address_edit_text);
        phoneNumberEditText = findViewById(R.id.phone_number_edit_text);
        signUpCompanyNameEditText = findViewById(R.id.sign_up_company_name_edit_text);
        signUpTitleEditText = findViewById(R.id.sign_up_title_edit_text);
        signUpUserTypesEditText = findViewById(R.id.sign_up_user_types_edit_text);

        btnSignUp = findViewById(R.id.sign_up_button);

        firebaseAuth = FirebaseAuth.getInstance();
        loginRedirectText = findViewById(R.id.login_redirect_text);

        countryCodePicker = findViewById(R.id.country_code_picker);

        countryCodePicker.registerCarrierNumberEditText(phoneNumberEditText);

        /** ######################################################################################## */

        civilityArrayAdapter = new ArrayAdapter<String>(this, R.layout.list_item, civilityItems);
        signUpCivilityEditText.setAdapter(civilityArrayAdapter);

        signUpCivilityEditText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                signUpCivilityEditText.setText(item);
            }
        });

        /** ######################################################################################## */

        userTypesArrayAdapter = new ArrayAdapter<String>(this, R.layout.list_item, userTypesItems);
        signUpUserTypesEditText.setAdapter(userTypesArrayAdapter);

        signUpUserTypesEditText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                signUpUserTypesEditText.setText(item);
            }
        });

        /** ######################################################################################## */

        phoneNumberEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (bool) {
                        getPhoneNumberDynamically();
                        bool = false;
                    }
                }
                return false;
            }
        });

        /** ######################################################################################## */

        MaterialDatePicker<Long> materialDatePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();

        signUpBirthDayEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {

                    materialDatePicker.show(getSupportFragmentManager(), "Material_Date_Picker");
                    materialDatePicker.setCancelable(false);
                    materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                        @Override
                        public void onPositiveButtonClick(Object selection) {
                            signUpBirthDayEditText.setText(materialDatePicker.getHeaderText());
                        }
                    });
                }
                return false;
            }
        });

        /** ######################################################################################## */

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference("users");

                String userAddress = signUpAddressEditText.getText().toString().trim();
                String userBirthDay = signUpBirthDayEditText.getText().toString().trim();
                String userCivility = signUpCivilityEditText.getText().toString().trim();
                String userCompanyName = signUpCompanyNameEditText.getText().toString().trim();
                String userFirstName = signUpFirstNameEditText.getText().toString().trim();
                String userLastName = signUpLastNameEditText.getText().toString().trim();
                String userPassword = signUpPasswordEditText.getText().toString().trim();
                String userPhoneNumber = phoneNumberEditText.getText().toString().trim();
                String userTitle = signUpTitleEditText.getText().toString().trim();
                String userTypes = signUpUserTypesEditText.getText().toString().trim();
                String userWorkEmail = signUpEmailEditText.getText().toString().trim().trim();

                if (userCivility.isEmpty()) {
                    signUpCivilityEditText.setError("Civility cannot be empty");
                }
                if (userWorkEmail.isEmpty()) {
                    signUpEmailEditText.setError("Email cannot be empty");
                }
                if (userPassword.isEmpty()) {
                    signUpPasswordEditText.setError("Password cannot be empty");
                }
                if (userFirstName.isEmpty()) {
                    signUpFirstNameEditText.setError("First name cannot be empty");
                }
                if (userLastName.isEmpty()) {
                    signUpLastNameEditText.setError("Last name cannot be empty");
                }
                if (userBirthDay.isEmpty()) {
                    signUpBirthDayEditText.setError("Birth day cannot be empty");
                }
                if (userAddress.isEmpty()) {
                    signUpAddressEditText.setError("Address cannot be empty");
                }
                if (userPhoneNumber.isEmpty()) {
                    phoneNumberEditText.setError("Phone number cannot be empty");
                }
                if (userCompanyName.isEmpty()) {
                    signUpCompanyNameEditText.setError("Company name cannot be empty");
                }
                if (userTitle.isEmpty()) {
                    signUpTitleEditText.setError("Title cannot be empty");
                }
                if (userTitle.isEmpty()) {
                    signUpTitleEditText.setError("Select User Type");
                } else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                    builder.setCancelable(false);
                    builder.setView(R.layout.progress_layout);
                    AlertDialog dialog = builder.create();
                    dialog.show();

                    firebaseAuth.createUserWithEmailAndPassword(userWorkEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Users users = new Users(userAddress, userBirthDay, userCivility, userCompanyName, userFirstName, userLastName, userPassword, userPhoneNumber, userTitle, userTypes, userWorkEmail);

                                databaseReference.child(encodeEmail(userWorkEmail)).setValue(users);
                                dialog.dismiss();
                                sendEmailVerification();
                                Toast.makeText(SignUpActivity.this, "SignUp Successful, Validate email ", Toast.LENGTH_SHORT).show();

                            } else {
                                dialog.dismiss();
                                Toast.makeText(SignUpActivity.this, "SignUp Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });
    }


    private void getPhoneNumberDynamically() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_NUMBERS)
                    == PackageManager.PERMISSION_GRANTED) {
                showDialog();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_PHONE_NUMBERS},
                        MY_PERMISSIONS_REQUEST_READ_PHONE_NUMBERS);
            }
        } else {
            showDialog();
        }
    }

    private void showDialog() {
        Dialog dialog;
        dialog = new Dialog(SignUpActivity.this, R.style.Dialog);
        dialog.setContentView(R.layout.phone_number_dialog);
        TextView userPhoneNumberEditText = dialog.findViewById(R.id.user_phone_number_text_view);
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String phoneNumber;
        try {
            phoneNumber = telephonyManager.getLine1Number();
            userPhoneNumberEditText.setText(phoneNumber);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        dialog.findViewById(R.id.take_phone_number_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumberEditText.setText(userPhoneNumberEditText.getText().toString());
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.cancel_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_PHONE_NUMBERS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showDialog();
                } else {
                    Toast.makeText(getApplicationContext(), "Denied", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    public String encodeEmail(String email) {
        return email.replace(".", ",").replace("@", "_");
    }

    private void sendEmailVerification() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Intent intentToLoginActivity = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intentToLoginActivity);
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}