package com.example.inspirationrewards;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;


public class MainActivity extends AppCompatActivity {
//i think this is done!
    private static final String TAG = "MainActivity";
    public ProgressBar progressBar;
    private EditText username_View;
    private EditText password_View;
    private TextView createAccount_View;
    private CheckBox rememberCheckBox;
    private Location location;
    private LocationManager locationManager;
    private Criteria criteria;
    private SharedPreferences userPreferences;
    private SharedPreferences.Editor ed;

    //assingment says that this is where app determines location
    public String state = "";
    public String city = "";

    private static int locationRequestCode = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: ");
        username_View=findViewById(R.id.username);
        password_View=findViewById(R.id.password);
        //this is done
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        //when it comes to saving
        userPreferences=getSharedPreferences("User_Prefenrences", Context.MODE_PRIVATE);
        ed=userPreferences.edit();
        //done
        userPreferences = this.getSharedPreferences("REMEMBER_CREDENTIALS", Context.MODE_PRIVATE);
        ed= userPreferences.edit();
        //If remember clicked

        if (userPreferences.contains("username") && userPreferences.contains("password")) {
            // Set editText to the username and mark the checkbox
            String savedUserName = userPreferences.getString("username", "");
            username_View.setText(savedUserName);

            // Set editText to the password and mark the checkbox
            String savedPassword = userPreferences.getString("password", "");
            password_View.setText(savedPassword);

            // Set the checkbox to true
            rememberCheckBox.setChecked(true);
        }

        //need to find their location
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //copied from professors code
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_MEDIUM);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, locationRequestCode);
        }
        else {
            fetchLocation(); //called
        }





    }

    //copied and modifier from professor's code
    @SuppressLint("MissingPermission")
    private void fetchLocation() {
        String bestProvider = locationManager.getBestProvider(criteria, true);
        location = locationManager.getLastKnownLocation(bestProvider);
        if (location != null) {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            try {
                List<Address> address = geocoder.getFromLocation(latitude, longitude, 1);
                state = address.get(0).getAdminArea();
                city = address.get(0).getLocality();

                Log.d("Location", "city: " + city);
                Log.d("Location", "state: " + state);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
public void login(View v) {
    if (!username_View.getText().toString().isEmpty() && !password_View.getText().toString().isEmpty()) {
        progressBar.setVisibility(View.VISIBLE); //this should show progress bar
        LoginAsyncTask loginAsyncTask = new LoginAsyncTask(this, username_View.getText().toString(), password_View.getText().toString(), city, state);
        loginAsyncTask.execute();
    }
    else {
        Toast.makeText(this, "Enter your username or password", Toast.LENGTH_SHORT).show();
    }
    }
    public void goToCreateProfile(View v){
        //this should take them to Create Profile Activity 
        Log.d(TAG, "goToCreateProfile: ");
        Intent intent = new Intent(MainActivity.this, Create_Profile_Activity.class);
        intent.putExtra("State", state);
        intent.putExtra("City", city);
        startActivity(intent);
    }
    public void goToProfile(User a){
        progressBar.setVisibility(View.GONE);
        Log.d(TAG, "goToProfile: ");
        Intent intent=new Intent(MainActivity.this, Profile_Activity.class);
        intent.putExtra("profile", a); //make sure user is serializable/ This is where i messed up on stockwatch
        startActivity(intent);

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 7) {
            fetchLocation();
        }

    }
}
