package com.example.inspirationrewards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Award_Activity extends AppCompatActivity {

    private ImageView award_Photo;
    private TextView award_Firstname;
    private TextView award_Lastname;
    private TextView awardPointsAwarded;
    private TextView awardDepartmentLabel;
    private TextView award_Department;
    private TextView award_PositionLabel;
    private TextView award_Position;
    private TextView award_StoryLabel;
    private TextView award_Story;
    private TextView awardCommentCount;
    private EditText award_CommentText;
    private TextView awardPointsToSendLabel;
    private EditText awardPointsToSendText;
    private TextView award_CommentLabel;
    private TextView awardPointsAwardedLabel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_award_);

        //assigned them all
        award_Firstname = findViewById(R.id.award_firstname);
        award_Lastname = findViewById(R.id.award_lastname);
        award_Photo = findViewById(R.id.imageView4);
        awardPointsAwardedLabel = findViewById(R.id.award_point_total_label);
        awardPointsAwarded = findViewById(R.id.points_awarded);
        awardDepartmentLabel = findViewById(R.id.awards_department_label);
        award_Department = findViewById(R.id.awards_department);
        award_PositionLabel = findViewById(R.id.award_positionlabel);
        award_Position = findViewById(R.id.award_postion);
        award_StoryLabel = findViewById(R.id.story_label);
        award_Story = findViewById(R.id.awards_story);
        awardPointsToSendLabel = findViewById(R.id.award_points_to_send);
        awardPointsToSendText = findViewById(R.id.award_PointsToSendText);
        award_CommentLabel = findViewById(R.id.comment_label);
        awardCommentCount = findViewById(R.id.comment_count);
        award_CommentText = findViewById(R.id.award_comment_Text); //need something else
        award_CommentText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(80)}); //figured it out :D
        //need new function
        TextListener();

    }


    private void TextListener(){
        award_CommentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //not sure what i need here. ASK Teacher Assistant!
                //********************
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int len = s.length();
                String countText = "(" + len + " of 80)";
                awardCommentCount.setText(countText);
            }
            @Override
            public void afterTextChanged(Editable s) {
              // ask TA?
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_award, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch(item.getItemId()){
           //there's only one item
            case(R.id.award_save):
                Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
