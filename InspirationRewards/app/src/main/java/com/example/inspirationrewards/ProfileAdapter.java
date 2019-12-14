package com.example.inspirationrewards;

import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


//should be done!
public class ProfileAdapter extends RecyclerView.Adapter<MyViewHolder>{
    private List<User> userList;
    private Leaderboard_Activity leaderboardActivity;

    public ProfileAdapter(List<User> ul, Leaderboard_Activity la) {
        this.userList = ul;
        leaderboardActivity = la;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.leaderboard_lis, parent, false);
        itemView.setOnClickListener(leaderboardActivity);
        itemView.setOnLongClickListener(leaderboardActivity);
        return new MyViewHolder(itemView);
        //should be done??
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position){
        User user = userList.get(position);
        String strPoints = intToString(user.getPoints());

        holder.name.setText(user.getLastname() + ", " + user.getFirstname());
        holder.position.setText(user.getPosition());
        holder.department.setText(user.getDepartment());
        holder.award_point_total.setText(strPoints);
        holder.thumbnail.setImageBitmap(BitmapFactory.decodeByteArray(user.getPhoto(), 0, user.getPhoto().length));
    }
    public static String intToString(int i){
        String s = "" + i;
        return s;
    }

    @Override
    public int getItemCount(){
        return userList.size();
    }


}

