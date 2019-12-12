package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import java.text.DecimalFormat;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    //private EditText userText;
    private TextView output1;
    private TextView output2;
    private TextView output3;
    private EditText numText;
    private TextView history;
    private int value;
    private RadioButton rb1;
    private RadioButton rb2;
    private double value1;
    private double result;
    private boolean True;
    private double value2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        output2 = (TextView) findViewById(R.id.Degrees_Known);
        output3 = (TextView) findViewById(R.id.Degrees_NotKnown);
        numText=(EditText)findViewById(R.id.Degrees_Input);
        history=(TextView)findViewById(R.id.history);
        output1=(TextView) findViewById(R.id.Degrees_Output);
        rb1= (RadioButton) findViewById(R.id.FtoC_label);
        rb2= (RadioButton)findViewById(R.id.CtoF_label);

    }
    //this function will be called by the first button clicked (degrees_known
public void doConversion(View v) {
    Log.d(TAG, "doConversion: ");

if (rb2.isChecked()) {
    String newText=numText.getText().toString();
    value1=Double.parseDouble(newText);
    result=(double)((value1*1.8)+32);
   // DecimalFormat value_1 = new DecimalFormat("##.#");
    String result1=String.format("%.1f", result);

 //   output1.setText(String.valueOf(value_1.format(result)));
    output1.setText(String.valueOf(result1));

    String historyText=history.getText().toString();
   // history.setText("C to F: "+ value_1.format(value1) + " C  ==> "+ value_1.format(result) +" F" + "\n"  + historyText);
    history.setText("C to F: "+ String.format("%.1f" ,value1) + " C  ==> "+ String.format("%.1f", result) +" F" + "\n"  + historyText);
    numText.setText("");


    }
else  {


    String newText=numText.getText().toString();
    value1= Double.parseDouble(newText);
    result =((value1-32.0)/1.8);
    String result1=String.format("%.1f", result);
   // DecimalFormat value_1 = new DecimalFormat("#.#");
  //  output1.setText(String.valueOf(value_1.format(result)));
    output1.setText(String.valueOf(result1));
    String historyText=history.getText().toString();
    history.setText("F to C: "+ String.format("%.1f" ,value1) + " F  ==> "+ String.format("%.1f", result)+" C" + "\n" + historyText);
    numText.setText("");

}
}
    public void  FtoC_clicked(View v)
    {
        output2.setText("Fahrenheit Degrees: ");
        output3.setText("Celsius Degrees: ");

        Log.d(TAG, "FtoC_clicked: ");

    }
    public void CtoF_clicked(View w)
    {
        Log.d(TAG, "CtoF_clicked: ");
        output2.setText("Celsius Degrees: ");
        output3.setText("Fahrenheit Degrees: ");

    }
public void doClear(View v)
{
    Log.d(TAG, "doClear: ");
    history.setText("");
}
    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putString("OUTPUT1", output1.getText().toString());
        outState.putString("OUTPUT2", output2.getText().toString());
        outState.putString("OUTPUT3", output3.getText().toString());
        outState.putString("HISTORY", history.getText().toString());
        // Call super last
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // Call super first
        super.onRestoreInstanceState(savedInstanceState);

        output1.setText(savedInstanceState.getString("OUTPUT1"));
        output2.setText(savedInstanceState.getString("OUTPUT2"));
        output3.setText(savedInstanceState.getString("OUTPUT3"));
        history.setText(savedInstanceState.getString("HISTORY"));
}
}
