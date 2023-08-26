package com.example.workflowpro;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ProfileActivity extends AppCompatActivity {

    TextView userNameTextView, workEmailTextView, userCivilityTextView, userFirstNameTextView, userSecondNameTextView, userBirthDayTextView, userEmailTextView, userPasswordTextView, userPhoneNumberTextView, userAddressTextView, userTitleTextView;
    Uri uri;
    ImageView profileImageView;
    String imageURL;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    String theUserWorkEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userNameTextView = findViewById(R.id.user_name_text_view);
        workEmailTextView = findViewById(R.id.work_email_text_view);
        userCivilityTextView = findViewById(R.id.user_civility_text_view);
        userFirstNameTextView = findViewById(R.id.user_first_name_text_view);
        userSecondNameTextView = findViewById(R.id.user_second_name_text_view);
        userBirthDayTextView = findViewById(R.id.user_birth_day_text_view);
        userEmailTextView = findViewById(R.id.user_email_text_view);
        userPasswordTextView = findViewById(R.id.user_password_text_view);
        userPhoneNumberTextView = findViewById(R.id.user_phone_number_text_view);
        userAddressTextView = findViewById(R.id.user_address_text_view);
        userTitleTextView = findViewById(R.id.user_title_text_view);
        profileImageView = findViewById(R.id.profile_image_view);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users");

        showAllUserData();

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            uri = data.getData();
                            profileImageView.setImageURI(uri);
                            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(uri.getLastPathSegment());
                            storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                    while (!uriTask.isComplete()) ;
                                    Uri urlImage = uriTask.getResult();
                                    imageURL = urlImage.toString();
                                    databaseReference.child(encodeEmail(workEmailTextView.getText().toString())).child("userProfileImage").setValue(imageURL);

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                }
                            });
                        } else {
                            Toast.makeText(ProfileActivity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });
    }

    public void showAllUserData() {
        Intent intent = getIntent();
        String theUserName = intent.getStringExtra("FIRST_NAME");
        theUserWorkEmail = intent.getStringExtra("WORK_EMAIL");
        String theUserCivility = intent.getStringExtra("CIVILITY");
        String theUserFirstName = intent.getStringExtra("FIRST_NAME");
        String theUserSecondName = intent.getStringExtra("LAST_NAME");
        String theUserBirthDay = intent.getStringExtra("BIRTH_DAY");
        String theUserEmail = intent.getStringExtra("WORK_EMAIL");
        String theUserPassword = intent.getStringExtra("PASSWORD");
        String theUserPhoneNumber = intent.getStringExtra("PHONE_NUMBER");
        String theUserAddress = intent.getStringExtra("ADDRESS");
        String theUserTitle = intent.getStringExtra("TITLE");

        userNameTextView.setText(theUserName);
        workEmailTextView.setText(theUserWorkEmail);
        userCivilityTextView.setText(theUserCivility);
        userFirstNameTextView.setText(theUserFirstName);
        userSecondNameTextView.setText(theUserSecondName);
        userBirthDayTextView.setText(theUserBirthDay);
        userEmailTextView.setText(theUserEmail);
        userPasswordTextView.setText(theUserPassword);
        userPhoneNumberTextView.setText(theUserPhoneNumber);
        userAddressTextView.setText(theUserAddress);
        userTitleTextView.setText(theUserTitle);
    }

    public String encodeEmail(String email) {
        return email.replace(".", ",").replace("@", "_");
    }

}