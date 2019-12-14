package com.example.inspirationrewards;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Edit_Activity extends AppCompatActivity {

    //THIS SHOULD BE DONE
    //no more errors 11/17/19
    private TextView edit_PositionLabel;
    private EditText edit_PositionText;
    private TextView edit_StoryLabel;
    private EditText edit_StoryText;
    private EditText edit_UsernameText;
    private EditText edit_PasswordText;
    private CheckBox edit_AdminCheckBox;
    private TextView edit_StoryCharCount;
    private ImageView edit_Photo;
    private ImageView edit_AddIcon;
    private EditText edit_FirstnameText;
    private EditText edit_LastnameText;
    private TextView edit_DepartmentLabel;
    private EditText edit_DepartmentText;
    private File currentImageFile;
    private int CAMERA_REQ = 0;
    private int GALLERY_REQ = 1;
    private String encodedImage = "";
    private static final String TAG = "Edit_Activity";
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_);
        Log.d(TAG, "onCreate: ");

        user = (User) getIntent().getSerializableExtra("profile"); //Remember I passed it as profile instead of user
        edit_UsernameText = findViewById(R.id.editUsernameText);
        edit_UsernameText.setText(user.getUsername());
        edit_PasswordText = findViewById(R.id.editPasswordText);
        edit_PasswordText.setText(user.getPassword());
        edit_AdminCheckBox = findViewById(R.id.editAdminCheckBox);
        edit_AdminCheckBox.setChecked(user.isAdmin());
        edit_FirstnameText = findViewById(R.id.editFirstnameText);
        edit_FirstnameText.setText(user.getFirstname());
        edit_LastnameText = findViewById(R.id.editLastnameText);
        edit_LastnameText.setText(user.getLastname());
        edit_DepartmentLabel = findViewById(R.id.editDepartmentLabel);
        edit_DepartmentText = findViewById(R.id.editDepartmentText);
        edit_DepartmentText.setText(user.getDepartment());
        edit_PositionLabel = findViewById(R.id.editPositionLabel);
        edit_PositionText = findViewById(R.id.editPositionText);
        edit_PositionText.setText(user.getPosition());
        edit_StoryLabel = findViewById(R.id.editStoryLabel);
        edit_StoryText = findViewById(R.id.editStoryText);
        edit_StoryText.setText(user.getStory());
        edit_StoryText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(360)});
        edit_StoryCharCount = findViewById(R.id.editStoryCharCount);
        edit_Photo = findViewById(R.id.edit_Photo);
        Bitmap bm = BitmapFactory.decodeByteArray(user.getPhoto(), 0, user.getPhoto().length);
        edit_Photo.setImageBitmap(bm);
        edit_AddIcon = findViewById(R.id.editAddIcon);
        add_Text_L();  //need new function. make sure to add/define it later
    }

    private void add_Text_L(){

        Log.d(TAG, "add_Text_L: ");
        edit_StoryText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int len = s.length();
                String countText = "(" + len + " of 360)";
                //need this. Not sure if it's right tho
                edit_StoryCharCount.setText(countText);
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: ");
        Intent toUserProfile = new Intent(this, Profile_Activity.class);
        switch (item.getItemId()) {
            case (android.R.id.home):
                finish();
                break;
            case (R.id.save_icon):
                update_Profile();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    public void update_Profile() {
        Log.d(TAG, "update_Profile: ");
        user.setPassword(edit_PasswordText.getText().toString());
        user.setAdmin(edit_AdminCheckBox.isChecked());
       user.setFirstname(edit_FirstnameText.getText().toString());
        user.setLastname(edit_LastnameText.getText().toString());
        user.setDepartment(edit_DepartmentText.getText().toString());
        user.setPosition(edit_PositionText.getText().toString());
        user.setStory(edit_StoryText.getText().toString());

        if (!encodedImage.isEmpty()) {
            user.setPhoto(Base64.decode(encodedImage, Base64.DEFAULT));
        }
//need to do this!!! Update_ProfileAsyncTask
        Update_ProfileAsyncTask update_ProfileAsyncTask = new Update_ProfileAsyncTask(this, user);
        update_ProfileAsyncTask.execute();
    }
//like the create profile activity!

    public void toGallery() {
        Log.d(TAG, "toGallery: ");
        Intent pictureFromGalleryIntent = new Intent(Intent.ACTION_PICK);
        pictureFromGalleryIntent.setType("image/*");
        startActivityForResult(pictureFromGalleryIntent, GALLERY_REQ);
    }

    public void startCamera() {
        Log.d(TAG, "startCamera: ");
        currentImageFile = new File(getExternalCacheDir(), "profileimage" + System.currentTimeMillis() + ".jpg");
        Intent pictureFromCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        pictureFromCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(currentImageFile));
        startActivityForResult(pictureFromCameraIntent, CAMERA_REQ);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        Log.d(TAG, "onActivityResult: ");

        if (requestCode == CAMERA_REQ && resultCode == RESULT_OK) {
            toIVCamera(); //implement first

            encodedImage = convertToBase64();
        }

        else if (requestCode == GALLERY_REQ && resultCode == RESULT_OK) {
            toIVGallery(data); //implement second 

            encodedImage = convertToBase64();
        }
    }

    private void toIVCamera() {
        Log.d(TAG, "toIVCamera: ");
        Uri imageRef = Uri.fromFile(currentImageFile);
        edit_Photo.setImageURI(imageRef);

        currentImageFile.delete();
    }

    private void toIVGallery(Intent data) {
        Log.d(TAG, "toIVGallery: ");
        Log.d(TAG, "toIVGallery: ");
        Uri imageRef = data.getData();

        if (imageRef != null) {
            InputStream imageStream = null;
            try {
                imageStream = getContentResolver().openInputStream(imageRef);
            } catch (FileNotFoundException e) {
                
            }

            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            edit_Photo.setImageBitmap(selectedImage);
        }
    }

    public String convertToBase64() {
        Log.d(TAG, "convertToBase64: ");
        Bitmap origBitmap = ((BitmapDrawable) edit_Photo.getDrawable()).getBitmap();
        ByteArrayOutputStream bitmapAsByteArrayStream = new ByteArrayOutputStream();
        origBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bitmapAsByteArrayStream);
        String imgString = Base64.encodeToString(bitmapAsByteArrayStream.toByteArray(), Base64.DEFAULT);
        return imgString;

    }
}





