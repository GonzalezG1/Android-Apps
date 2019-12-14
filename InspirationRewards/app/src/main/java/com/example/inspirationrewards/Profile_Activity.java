package com.example.inspirationrewards;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class Profile_Activity extends AppCompatActivity {


    //should be done
    //add attributes here
    private ImageView profile_Photo;
    private TextView profile_Lastname;
    private TextView profile_Firstname;
    private TextView profile_Username;
    private TextView profile_Location;
    private TextView profilePointsAwardedLabel;
    private TextView profilePointsAwarded;
    private TextView profileDepartmentLabel;
    private TextView profile_Department;
    private TextView profilePositionLabel;
    private TextView profile_Position;
    private TextView profilePointsToAwardLabel;
    private TextView profilePointsToAward;
    private TextView profileStoryLabel;
    private TextView profile_Story;
    private TextView profileRewardHistoryLabel;

    User user;
    private static int EDIT_REQ = 7;

    private static int MY_LOCATION_REQUEST_CODE = 100;
    private Location current_Location;
    private LocationManager location_Manager;
    private Criteria criteria;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_);
user=(User) getIntent().getSerializableExtra("profile"); // check MainActivity for name of code
        profile_Lastname=findViewById(R.id.lastname);
        profile_Lastname.setText(user.getLastname());
        profile_Firstname=findViewById(R.id.firstname);
        profile_Firstname.setText(user.getFirstname());
        profile_Username=findViewById(R.id.your_username);
        profile_Username.setText(user.getUsername());
        profile_Location=findViewById(R.id.your_location);
        profile_Location.setText(user.getLocation());
        profile_Photo=findViewById(R.id.your_picture);
        Bitmap bm= BitmapFactory.decodeByteArray(user.getPhoto(), 0 , user.getPhoto().length); //check in case it's wrong
        profilePositionLabel=findViewById(R.id.position_label); //your position undernearth
        profile_Position=findViewById(R.id.your_position);
        profile_Position.setText(user.getPosition());
        profilePositionLabel=findViewById(R.id.points_to_Award_label);
        profilePointsToAward=findViewById(R.id.yourpoints);
        profilePointsToAward.setText(""+user.getPoints());
        profileStoryLabel=findViewById(R.id.your_story_label);
        profile_Story=findViewById(R.id.your_story);
        profile_Story.setText(user.getStory());

        location_Manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAccuracy(Criteria.ACCURACY_MEDIUM);


        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },
                    MY_LOCATION_REQUEST_CODE);
        }
        else{
            setLocation();
        }
    }

    //LOCATION METHODS
    private void setLocation() {
        String best_Provider = location_Manager.getBestProvider(criteria, true);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent toLeaderBoard= new Intent(this, Leaderboard_Activity.class);
        Intent toEdit=new Intent(this, Edit_Activity.class);
        Intent toLogin=new Intent(this, MainActivity.class);
        switch (item.getItemId()) {
            case R.id.edit:
                //goes to edit activity
              //  Intent intent = new Intent(Profile_Activity.this, Edit_Activity.class);
              //  startActivity(intent);
                toEdit.putExtra("profile", user);
                startActivityForResult(toEdit, EDIT_REQ);
                break;
            //    return true;
            case R.id.add_rewards:
              //  Intent intent1=new Intent(Profile_Activity.this, Leaderboard_Activity.class);
            //    startActivity(intent1);
                toLeaderBoard.putExtra("profile", user);
                startActivity(toLeaderBoard);
                break;

            //    return true;
         //   default:
       //         return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == EDIT_REQ) {
            if (resultCode == 1) {
                user= (User) data.getSerializableExtra("profile");

                profile_Lastname.setText(user.getLastname());
                profile_Firstname.setText(user.getFirstname());
                profile_Username.setText(user.getUsername());
                profile_Location.setText(user.getLocation());

                Bitmap bm = BitmapFactory.decodeByteArray(user.getPhoto(), 0, user.getPhoto().length);
                profile_Photo.setImageBitmap(bm);

                profile_Department.setText(user.getDepartment());
                profile_Position.setText(user.getPosition());
                profile_Story.setText(user.getStory());

            }
        }
    }

}

