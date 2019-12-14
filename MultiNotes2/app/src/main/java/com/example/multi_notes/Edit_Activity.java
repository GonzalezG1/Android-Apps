package com.example.multi_notes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONObject;
import android.util.JsonWriter;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
public class Edit_Activity extends AppCompatActivity {

    private static final String TAG = "Edit_Activity";
    private TextView textView;
    private EditText editText;
    private EditText title_input;
    private EditText note_input;

    //new code
    private Notes notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_);
        title_input = (EditText)findViewById(R.id.title);
        note_input= (EditText)findViewById(R.id.note);

        Intent intent = getIntent();
        if (intent.hasExtra("The Note")) {
            notes= (Notes) intent.getSerializableExtra("The Note");
        }

        //added new
        notes = loadFile();  // Load the JSON containing the product data - if it exists
        if (notes != null) { // null means no file was loaded
            title_input.setText(notes.getTitle());
            note_input.setText(notes.getWrittenNotes());
        }
        super.onResume();
    }
    //added new
    private Notes loadFile() {
        Log.d(TAG, "loadFile: loading JASON File");
        notes = new Notes();
        try {
            InputStream is = getApplicationContext().
                    openFileInput(getString(R.string.file_name));
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            JSONObject jsonObject = new JSONObject(sb.toString());
            String title = jsonObject.getString("title");
            String Written_note = jsonObject.getString("Written_Note");
            notes.setTitle(title);
            notes.setWrittenNotes(Written_note);

        } catch (
                FileNotFoundException e) {
            Toast.makeText(this, getString(R.string.no_file), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return notes;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.second_menu, menu);
        return true;
    }

   /* public boolean onOptionsItemSelected(MenuItem item) {

        Log.d(TAG, "onOptionsItemSelected: ");

        //textView.setText("You picked: " + item.getTitle().toString());
        switch (item.getItemId()) {
            case R.id.Save:
                Log.d(TAG, "onOptionsItemSelected: ");
                Notes n = new Notes(title_input.getText().toString(), note_input.getText().toString());
                Intent data = new Intent(); // Used to hold results data to be returned to original activity
                data.putExtra("The Note", n);
                setResult(RESULT_OK, data); //passes it?
                finish(); // This closes the current activity, returning us to the original activity
                return true;
            default:
                return super.onOptionsItemSelected(item);



        }
    }
*/
   public void doThis(MenuItem item)
   {
     //  Log.d(TAG, "onOptionsItemSelected: ");
       Log.d(TAG, "doThis: ");
       Notes n = new Notes(title_input.getText().toString(), note_input.getText().toString());
       Intent data = new Intent(); // Used to hold results data to be returned to original activity
      // data.putExtra("The Note", n);
       data.putExtra("Title Passed", title_input.getText().toString());
       setResult(RESULT_OK, data); //passes it?
       Intent data1=new Intent();
       data1.putExtra("Written Text Passed", note_input.getText().toString());
       setResult(RESULT_OK, data1);

       finish(); // This closes the current activity, returning us to the original activity
   }


    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed: ");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(Edit_Activity.this, "Saved", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(Edit_Activity.this, "Canceled", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setMessage("Save note 'Set DVR'?");
        builder.setTitle("Your note is not saved!!");
        AlertDialog dialog = builder.create();
        dialog.show();

       // super.onBackPressed();
    //   finish();
    }

       //added new
        @Override
        protected void onPause() {
            Log.d(TAG, "onPause: ");
            notes.setTitle(title_input.getText().toString());
            notes.setWrittenNotes(note_input.getText().toString());
            saveNotes();
            super.onPause();

        }
        //added new
        private void saveNotes() {
            Log.d(TAG, "saveNotes: ");

            try {
                FileOutputStream fos = getApplicationContext().
                        openFileOutput(getString(R.string.file_name), Context.MODE_PRIVATE);

                JsonWriter writer = new JsonWriter(new OutputStreamWriter(fos, getString(R.string.encoding)));
                writer.setIndent("  ");
                writer.beginObject();
                writer.name("title").value(notes.getTitle());
                writer.name("Written_Note").value(notes.getWrittenNotes());
                writer.endObject();
                writer.close();
                Toast.makeText(this, getString(R.string.saved), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
}
