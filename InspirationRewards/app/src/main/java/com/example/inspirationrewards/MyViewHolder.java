package com.example.inspirationrewards;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
//DONE
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView thumbnail;
        public TextView name;
        public TextView award_point_total;
        public TextView position;
        public TextView department;

        public MyViewHolder(View view) {
            super(view);
            thumbnail = view.findViewById(R.id.the_picture);
            name = view.findViewById(R.id.name_label);
            position = view.findViewById(R.id.position);
            award_point_total = view.findViewById(R.id.award_point_total_label);
            department=view.findViewById(R.id.department_label);



        }
    }
