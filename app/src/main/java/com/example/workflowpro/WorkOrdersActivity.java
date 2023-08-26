package com.example.workflowpro;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class WorkOrdersActivity extends AppCompatActivity {

    AppCompatRadioButton noneRadioButton, lowRadioButton, mediumRadioButton, highRadioButton;

    RadioGroup requestPriorityRadioButton;
    Button showDialogButton, workerButton, locationButton, assetButton, dateButton, teamButton;
    ImageView myImageView;
    ActivityResultLauncher<String> myContent;
    Toolbar requestToolBar;
    ImageView doneImageView, requestImageView;
    TextInputEditText requestTitleEditText, requestDescriptionEditText, locationEditText, assetEditText, dateEditText, teamEditText;
    AutoCompleteTextView dropDownMenuItems;

    String[] items = {"Electricity", "Informatics", "Electronics", "Industry", "Farming"};
    ArrayAdapter<String> arrayAdapter;

    int CAPTURE_CODE = 101;
    int REQUEST_CODE = 100;
    Uri myImageUri;
    byte[] ImageByteArray;
    RadioButton selectedRadioButton;

    Bitmap bitmap;

    DatabaseReference databaseReference;
    ArrayList<Request> requestArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_orders);

        requestToolBar = findViewById(R.id.request_tool_bar);
        setSupportActionBar(requestToolBar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        showDialogButton = findViewById(R.id.show_dialog_btn);

        dropDownMenuItems = findViewById(R.id.drop_down_menu_items);

        workerButton = findViewById(R.id.worker_btn);
        locationButton = findViewById(R.id.location_btn);
        assetButton = findViewById(R.id.asset_btn);
        dateButton = findViewById(R.id.date_btn);
        teamButton = findViewById(R.id.team_btn);

        requestTitleEditText = findViewById(R.id.request_title_edit_text);
        requestDescriptionEditText = findViewById(R.id.request_description_edit_text);
        locationEditText = findViewById(R.id.location_edit_text);
        assetEditText = findViewById(R.id.asset_edit_text);
        teamEditText = findViewById(R.id.team_edit_text);
        dateEditText = findViewById(R.id.date_edit_text);

        requestImageView = findViewById(R.id.request_image_view);
        doneImageView = findViewById(R.id.done_image_view);

        requestPriorityRadioButton = findViewById(R.id.request_priority_radio_button);

        noneRadioButton = findViewById(R.id.none_radio_button);
        lowRadioButton = findViewById(R.id.low_radio_button);
        mediumRadioButton = findViewById(R.id.medium_radio_button);
        highRadioButton = findViewById(R.id.high_radio_button);

        int selectedId = requestPriorityRadioButton.getCheckedRadioButtonId();
        selectedRadioButton = findViewById(selectedId);

        arrayAdapter = new ArrayAdapter<String>(this, R.layout.list_item, items);
        dropDownMenuItems.setAdapter(arrayAdapter);

        /** ################################################################################################################################# */

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Requests");
        requestArrayList = new ArrayList<>();

        doneImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitRequest();
            }
        });

        myContent = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), result);
                    requestImageView.setImageBitmap(bitmap);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    ImageByteArray = stream.toByteArray();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });

/*        showDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(MainActivity.this, R.style.Dialog);
                dialog.setContentView(R.layout.image_dialog);

                dialog.findViewById(R.id.take_image_btn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myContent.launch("image/*");
                        dialog.dismiss();
                    }
                });

                dialog.findViewById(R.id.camera_roll_btn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED ||
                                    checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
                            ) {

                                String[] tabPermission = {android.Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};
                                requestPermissions(tabPermission, REQUEST_CODE);
                                dialog.dismiss();
                            } else {
                                takePicture();
                                dialog.dismiss();
                            }
                        } else {
                            takePicture();
                            dialog.dismiss();
                        }
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
        });*/

        /** ################################################################################################################################# */

/*        workerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToWorkerActivity = new Intent(MainActivity.this, WorkerActivity.class);
                startActivity(intentToWorkerActivity);
            }
        });*/

        /** ################################################################################################################################# */

/*        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToLocationActivity = new Intent(MainActivity.this, LocationActivity.class);
                startActivity(intentToLocationActivity);
            }
        });*/

        /** ################################################################################################################################# */

/*        assetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToAssetActivity = new Intent(MainActivity.this, AssetActivity.class);
                startActivity(intentToAssetActivity);
            }
        });*/

        /** ################################################################################################################################# */

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        /** ################################################################################################################################# */

/*
        teamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToTeamActivity = new Intent(WorkOrdersActivity.this, Team.class);
                startActivity(intentToTeamActivity);
            }
        });
*/

        /** ################################################################################################################################# */

        MaterialDatePicker materialDatePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getSupportFragmentManager(), "Material_Date_Picker");
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        dateEditText.setText(materialDatePicker.getHeaderText());
                    }
                });
            }
        });

        /** ################################################################################################################################# */
    }


    public void onRadioButtonClicked(View view) {

        boolean isChecked = ((AppCompatRadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.none_radio_button:
                if (isChecked) {
                    noneRadioButton.setTextColor(Color.WHITE);
                    lowRadioButton.setTextColor(Color.RED);
                    mediumRadioButton.setTextColor(Color.RED);
                    highRadioButton.setTextColor(Color.RED);
                }
                break;

            case R.id.low_radio_button:
                if (isChecked) {
                    noneRadioButton.setTextColor(Color.RED);
                    lowRadioButton.setTextColor(Color.WHITE);
                    mediumRadioButton.setTextColor(Color.RED);
                    highRadioButton.setTextColor(Color.RED);
                }
                break;

            case R.id.medium_radio_button:
                if (isChecked) {
                    noneRadioButton.setTextColor(Color.RED);
                    lowRadioButton.setTextColor(Color.RED);
                    mediumRadioButton.setTextColor(Color.WHITE);
                    highRadioButton.setTextColor(Color.RED);
                }
                break;

            case R.id.high_radio_button:
                if (isChecked) {
                    noneRadioButton.setTextColor(Color.RED);
                    lowRadioButton.setTextColor(Color.RED);
                    mediumRadioButton.setTextColor(Color.RED);
                    highRadioButton.setTextColor(Color.WHITE);
                }
                break;
        }
    }

    /**
     * #################################################################################################################################
     */

    private void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // ContentValues myValues = new ContentValues();
        // myValues.put(MediaStore.Images.Media.TITLE, "New Image");
        //   myValues.put(MediaStore.Images.Media.DESCRIPTION, "New Image from Camera");
        // myImageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, myValues);
        //Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, myImageUri);

        // if (cameraIntent.resolveActivity(getPackageManager()) != null) {
        //      startActivityForResult(cameraIntent, CAPTURE_CODE);
        //  }

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "New Image");
            values.put(MediaStore.Images.Media.DESCRIPTION, "New Image from Camera");
            myImageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, myImageUri);
            startActivityForResult(takePictureIntent, CAPTURE_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePicture();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAPTURE_CODE && resultCode == RESULT_OK) {
            Bitmap imageBitmap = null;
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), myImageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            requestImageView.setImageBitmap(imageBitmap);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            ImageByteArray = byteArrayOutputStream.toByteArray();

        }
    }

    private void submitRequest() {

        String theRequestCategory = dropDownMenuItems.getText().toString();
        String theRequestDate = dateEditText.getText().toString();
        String theRequestDescription = requestDescriptionEditText.getText().toString();
        String theRequestId = databaseReference.push().getKey();
        String requestPriority = selectedRadioButton.getText().toString();
        String requestTitle = requestTitleEditText.getText().toString();

        Request request = new Request("No asset now", theRequestCategory, "No requester now", theRequestDate, theRequestDescription, theRequestId, "No location now", requestPriority, "Requested", "No team now", getCurrentDate(), requestTitle, "No workers now");
        if (theRequestId != null) {
            databaseReference.child(theRequestId).setValue(request);
        }
    }

    private String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE hh:mm a");
        return simpleDateFormat.format(calendar.getTime());
    }
}