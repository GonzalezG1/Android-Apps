package com.example.multi_notes;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesViewHolder> {
    private static final String TAG = "NotesAdapter";

    private List<Notes> MyNotes;
    private MainActivity mainAct;

    public NotesAdapter(List<Notes> notesList, MainActivity ma) {
        this.MyNotes = notesList;
        mainAct = ma;

    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: MAKING NEW MyViewHolder");

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_entry, parent, false);


        return new NotesViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: FILLING VIEW HOLDER Employee " + position);

        Notes notes=MyNotes.get(position);


        holder.title.setText(notes.getTitle());
        holder.WrittenNotes.setText(notes.getWrittenNotes());
        holder.dateTime.setText(new Date().toString());
    }

    @Override
    public int getItemCount() {
        return MyNotes.size();
    }


}
