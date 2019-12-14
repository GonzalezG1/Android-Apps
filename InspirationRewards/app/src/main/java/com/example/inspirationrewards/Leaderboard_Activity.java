package com.example.inspirationrewards;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Leaderboard_Activity extends AppCompatActivity
        implements View.OnClickListener, View.OnLongClickListener {
//Should be done also
    private RecyclerView recyclerView;
    private ProfileAdapter profileAdapter;
    private final List<User> userList = new ArrayList<>();  // Main content is here
    private ProfileAdapter uAdapter; // Data to recyclerview adapter
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard_);
        recyclerView = findViewById(R.id.recycler);
        uAdapter = new ProfileAdapter(userList, this);
        recyclerView.setAdapter(uAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        user=(User) getIntent().getSerializableExtra("profile");

        GetAllProfilesAsyncTask getAllProfilesAsyncTask = new GetAllProfilesAsyncTask(this, user);
        getAllProfilesAsyncTask.execute();
        //need to work on this!!!

        //might need to put action bar else done
    }
//done
    @Override
    public void onClick(View v) {  // click listener called by ViewHolder clicks

        Intent toAwarda = new Intent(this, Award_Activity.class);
        startActivity(toAwarda);
        Toast.makeText(v.getContext(), "onclicked " , Toast.LENGTH_SHORT).show();
        //this should take them to the award activity of that user when clicked

    }
//done
    @Override
    public boolean onLongClick(View v) {  // long click listener called by ViewHolder long clicks
        Intent toAwarda = new Intent(this, Award_Activity.class);
        startActivity(toAwarda);
        Toast.makeText(v.getContext(), "longlicked " , Toast.LENGTH_SHORT).show();

        return true;
    }

    public void addProfile(User currProfile) {
        userList.add(currProfile);

        if (currProfile.getUsername().equals(user.getUsername())) {
            userList.remove(currProfile);
        }

        Collections.sort(userList, new Comparator<User>() {
            @Override
            public int compare(User p1, User p2) {

                List<Reward> p1Rewards = p1.getRewardList();
                int p1Total = 0;
                for (int i = 0; i < p1Rewards.size(); i++) {
                    Reward currReward = p1Rewards.get(i);
                    p1Total += currReward.getAmount();
                }

                List<Reward> p2Rewards = p2.getRewardList();
                int p2Total = 0;
                for (int i = 0; i < p2Rewards.size(); i++) {
                    Reward currReward = p2Rewards.get(i);
                    p2Total += currReward.getAmount();
                }
                if (p1Total > p2Total) {
                    return -1;
                }
                else if (p1Total < p2Total) {
                    return 1;
                }
                return 0;
            }
        });
        profileAdapter.notifyDataSetChanged(); //saw it professor's notes
    }
    //doneeee ???

}
