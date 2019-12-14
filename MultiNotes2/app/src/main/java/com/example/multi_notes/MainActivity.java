package com.example.multi_notes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONObject;
import org.w3c.dom.Text;

import android.util.JsonWriter;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

    public class MainActivity extends AppCompatActivity
            implements View.OnClickListener, View.OnLongClickListener {

        private TextView textView;

        private static final String TAG = "MainActivity";
        private EditText title;
        private EditText note;
        private EditText last_update_time;

        private TextView display_title;
        private TextView display_note;

        private ArrayList<Notes> MyNotes = new ArrayList<>();
        private RecyclerView recyclerView;
        private NotesAdapter mAdapter;

        private static final int Edit_Request_CODE = 1;
        //added last
        private Notes notes;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            textView = findViewById(R.id.textView);
            recyclerView = findViewById(R.id.notes_recycler);

            mAdapter = new NotesAdapter(MyNotes, this);

            recyclerView.setAdapter(mAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

display_note= (TextView)findViewById(R.id.Title_Note);
display_note=(TextView)findViewById(R.id.Written_text);
   //         for (int i = 0; i < 10; i++) {
     //           MyNotes.add(new Notes());
         //   }

        }
        @Override
        public void onClick(View v) {  // click listener called by ViewHolder clicks

            int pos = recyclerView.getChildLayoutPosition(v);
            Notes m = MyNotes.get(pos);

            Toast.makeText(v.getContext(), "SHORT " + m.toString(), Toast.LENGTH_SHORT).show();
        }
        @Override
        public boolean onLongClick(View v) {  // long click listener called by ViewHolder long clicks
            int pos = recyclerView.getChildLayoutPosition(v);
            Notes m = MyNotes.get(pos);
            Toast.makeText(v.getContext(), "LONG " + m.toString(), Toast.LENGTH_SHORT).show();

            return false;
        }
            @Override
            public boolean onCreateOptionsMenu (Menu menu){
                getMenuInflater().inflate(R.menu.testmenu, menu);
                return true;
            }


            public boolean onOptionsItemSelected (MenuItem item){
                Log.d(TAG, "onOptionsItemSelected:");
                textView.setText("You picked: " + item.getTitle().toString());
                switch (item.getItemId()) {
                    case R.id.Add_Note_Label:
                        Intent intent = new Intent(this, Edit_Activity.class);
                        startActivity(intent);
                        Toast.makeText(this, "you want to add a note", Toast.LENGTH_SHORT).show();

                        intent.putExtra("NOT SURE WHAT GOES HERE", "SOME DATA");
                        startActivityForResult(intent, Edit_Request_CODE);
                        return true;

                    case R.id.Info_label:
                        Intent intent1 = new Intent(this, About_Activity.class);
                        startActivity(intent1);
                        Toast.makeText(this, "You want to see the info", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        return super.onOptionsItemSelected(item);
                }
            }

        @Override
            protected void onActivityResult (
            int requestCode, int resultCode, Intent data) {
            //    super.onActivityResult(requestCode, resultCode, data);

            //pretty sure i need to fix this

            if (resultCode == RESULT_OK) {

                 String text=data.getStringExtra("The Note");
                //   display_title.setText(text);
                // display_note.setText(text);
                // String text1=text.indexOf(",")

                /*if (data.equals("Title Passed")) {
                    String text = data.getStringExtra("Title Passed");
                    display_title.setText(text);
                } else {
                    String text1 = data.getStringExtra("Written Text Passed");
                    display_note.setText(text1);
                }
                */

                Log.d(TAG, "onActivityResult: ");
                Toast.makeText(this, "Result is ok :D",
                        Toast.LENGTH_SHORT).show();
            }


                     else{
                    Toast.makeText(this, "OTHER result not OK!",
                            Toast.LENGTH_SHORT).show();
                }


            }
        }




