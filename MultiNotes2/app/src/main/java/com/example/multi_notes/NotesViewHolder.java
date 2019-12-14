package com.example.multi_notes;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


public class NotesViewHolder  extends RecyclerView.ViewHolder {


    public TextView title;
    public TextView WrittenNotes;
    public TextView dateTime;

    public NotesViewHolder (View view) {
        super(view);
        title = view.findViewById(R.id.Title_Note);;
        WrittenNotes = view.findViewById(R.id.Written_text);
        dateTime = view.findViewById(R.id.dateTime);
    }
}
