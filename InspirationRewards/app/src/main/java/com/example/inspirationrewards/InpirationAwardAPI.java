package com.example.inspirationrewards;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class InpirationAwardAPI extends AsyncTask<String, Double, String> {

    private static final String ID="A20348998";
    private static final String baseURL="http://inspirationrewardsapi-env.6mmagpm2pv.us-east-2.elasticbeanstalk.com";
    private static final String TAG = "InpirationAwardAPI";

    @SuppressLint("StaticFieldLeak") //professor did this in his example!
    private Award_Activity award_Activity;
    private String date;
    private String comment;
    private String receiver_Username;
    private String receiver_Name;
    private int amount;
// all of them are assigned but never accessed. might need to fix later!!!
    @Override
    //double checking

    protected String doInBackground(String... strArgs){
        Log.d(TAG, "doInBackground: ");

        return "";
    }


    @Override
    protected void onPostExecute(String str){
        Log.d(TAG, "onPostExecute: ");
        super.onPostExecute(str);
    }

    //LOL I misspelled Inspiration ):
    private InpirationAwardAPI(Award_Activity aa){
        Log.d(TAG, "InpirationAwardAPI: ");

        this.award_Activity = aa;
    }

}

