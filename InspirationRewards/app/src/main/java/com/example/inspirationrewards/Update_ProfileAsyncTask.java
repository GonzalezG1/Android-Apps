package com.example.inspirationrewards;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static java.net.HttpURLConnection.HTTP_OK;

public class Update_ProfileAsyncTask extends AsyncTask<String, Double, String> {

//KEEP GETTING ERRORS HERE
    //FIX

    private static final String TAG = "GetAllProfilesAsyncTask";
    private static final String ID="A20348998";
    private static final String baseURL="http://inspirationrewardsapi-env.6mmagpm2pv.us-east-2.elasticbeanstalk.com";

    @SuppressLint("StaticFieldLeak")
    private User user;
    private Edit_Activity edit_Activity;
    private String URL = "";
    private String requestMethod = "";

    private static boolean stat = false;

    public Update_ProfileAsyncTask(Edit_Activity ea, User u){
        this.edit_Activity = ea;
        this.user = u;
    }

    //edited from professor's notes
    @Override
    protected String doInBackground(String... strArgs){
      //careful!
        Log.d(TAG, "doInBackground: ");

        String URL = baseURL + "/profiles";
        BufferedReader BR = null;
        HttpURLConnection con = null;

        JSONObject jBody = new JSONObject();
        try {
            Log.d(TAG, "doInBackground: ");


            jBody.put("studentId", ID);
            jBody.put("username", user.getUsername());
            jBody.put("password", user.getPassword());
            jBody.put("firstName",user.getFirstname());
            jBody.put("lastName", user.getLastname());
            jBody.put("pointsToAward", user.getPoints());
            jBody.put("department", user.getDepartment());
            jBody.put("story", user.getStory());
            jBody.put("position", user.getPosition());
            jBody.put("admin", user.isAdmin());
            jBody.put("location", user.getLocation());
            jBody.put("imageBytes", Base64.encode(user.getPhoto(), Base64.DEFAULT)); //!!!! might be wrong??!

            JSONArray jsonArray = new JSONArray(); //Uusually mess up here
            for (int i = 0; i < user.getRewardList().size(); i++) {
                JSONObject jsonObject = new JSONObject();
                Reward reward = user.getRewardList().get(i);
                jsonObject.put("name", reward.getSenderName());
                jsonObject.put("date", reward.getDate());
                jsonObject.put("notes", reward.getComment());
                jsonObject.put("value", reward.getAmount());

                jsonArray.put(jsonObject);
            }

            jBody.put("rewardRecords", jsonArray);

            Uri parsedURI = Uri.parse(URL); //*****
            URL url = new URL(parsedURI.toString());
            con = (HttpURLConnection) url.openConnection();


            con.setRequestMethod("PUT");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Accept", "application/json");
            con.connect();

            OutputStreamWriter OSW = new OutputStreamWriter(con.getOutputStream());
            OSW.write(jBody.toString());
            OSW.close();

            int responseCode = con.getResponseCode();

            StringBuilder SB = new StringBuilder();
            if (responseCode == HTTP_OK){
                BR = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String currentLine;
                while(null != (currentLine = BR.readLine())){
                    SB.append(currentLine).append("\n");
                }

                stat = true;
                return SB.toString();
            }
            else{
                Log.d(TAG, "doInBackground: ");
                BR = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                String currentLine;
                while(null != (currentLine = BR.readLine())){
                    SB.append(currentLine).append("\n");
                }
                return SB.toString();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return " ";
    }

    @Override
    protected void onPostExecute(String str){
        if (stat) {
            Log.d(TAG, "onPostExecute: ");

            Intent intent = new Intent();
            intent.putExtra("profile", user);
            edit_Activity.setResult(1, intent);
            edit_Activity.finish();
        }
    }

}


