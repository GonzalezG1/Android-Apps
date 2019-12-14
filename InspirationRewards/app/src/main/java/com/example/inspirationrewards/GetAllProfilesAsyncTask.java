package com.example.inspirationrewards;

import android.annotation.SuppressLint;
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
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static java.net.HttpURLConnection.HTTP_OK;

public class GetAllProfilesAsyncTask extends AsyncTask<String, Double, String> {
    private static final String TAG = "GetAllProfilesAsyncTask";
    private static final String ID="A20348998";
    private static final String baseURL="http://inspirationrewardsapi-env.6mmagpm2pv.us-east-2.elasticbeanstalk.com";

    @SuppressLint("StaticFieldLeak") //professor did this lol
    private Leaderboard_Activity leaderboardActivity;
    private User currentUser;
    private String rawURL = "";
    private String requestMethod = "";
    private static boolean status = false;

    public GetAllProfilesAsyncTask(Leaderboard_Activity la,User c){
        this.leaderboardActivity = la;
        this.currentUser= c;
    }


    @Override
    protected void onPostExecute(String str){
        Log.d(TAG, "onPostExecute: 1");
        //always get stuck here
        //fix??
        if(str != null){
            if(status){
                Log.d(TAG, "onPostExecute: ");
                
                //try to add get all users
                try{
                    JSONArray jArray = new JSONArray(str);

                    for(int i=0; i < jArray.length(); i++){

                        JSONObject JO = jArray.getJSONObject(i);
                        String firstname = JO.getString("firstName");
                        String lastname = JO.getString("lastName");
                        String username = JO.getString("username");
                        String department = JO.getString("department");
                        String position = JO.getString("position");
                        String story = JO.getString("story");
                        String password = ""; //we don't want to get this for a view of a user's profile
                        int pointsToAward = JO.getInt("pointsToAward");
                        boolean admin = JO.getBoolean("admin");
                        byte[] photo = Base64.decode(JO.getString("imageBytes"), Base64.DEFAULT);
                        String location = JO.getString("location");

                        if(!JO.isNull("rewards")){
                            JSONArray rewardsArray = new JSONArray(JO.getString("rewards"));
                            List<Reward> rewardList = new ArrayList<>();

                            for(int j=0; j < rewardsArray.length(); j++){
                                JSONObject rewardObject = rewardsArray.getJSONObject(j);
                                String rewardUsername = rewardObject.getString("username");
                                String rewardName = rewardObject.getString("name");
                                String rewardDate = rewardObject.getString("date");
                                String rewardNote = rewardObject.getString("notes");
                                int rewardValue = rewardObject.getInt("value");

                                rewardList.add(new Reward(rewardValue, rewardUsername, rewardName, rewardNote, rewardDate));
                            }
                            User newProfile = new User(username, password, firstname, lastname, department, position, story, admin, pointsToAward, photo, location, rewardList);
                            this.leaderboardActivity.addProfile(newProfile);
                        }
                        else {
                            User newProfile = new User(username, password, firstname, lastname, department, position, story, admin, pointsToAward, photo, location, new ArrayList<Reward>());
                            this.leaderboardActivity.addProfile(newProfile);
                        }
                    }
                }
                catch(JSONException e){
                    Log.d(TAG, "onPostExecute: JSON exception");
                    //might as well
                }
            }
            else{
                Log.d(TAG, "onPostExecute: else thing");

                //might as well
            }
        }
    }

    @Override
    protected String doInBackground(String... strings){
        Log.d(TAG, "doInBackground: ");
        String rawURL = baseURL + "/allprofiles"; //from professor's thing
        BufferedReader BR = null;
        HttpURLConnection con = null;

        try{
            Log.d(TAG, "doInBackground: ");
            Uri parsedURI = Uri.parse(rawURL);
            URL url = new URL(parsedURI.toString());
            con = (HttpURLConnection) url.openConnection();

            //this is where i mess upp. be careful***
            JSONObject jBody = getJSONBody();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Accept", "application/json");
            con.connect();

            OutputStreamWriter OSW = new OutputStreamWriter(con.getOutputStream());
            OSW.write(jBody.toString());
            OSW.close();

            int responseCode = con.getResponseCode();
            StringBuilder SB = new StringBuilder();

            if(responseCode == HTTP_OK){
                BR = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String currentLine;

                while(null != (currentLine  = BR.readLine())){
                    SB.append(currentLine).append("\n");
                }

                status = true;
                return SB.toString();
            }
            else{
                BR = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                String currentLine;

                while(null != (currentLine  = BR.readLine())){
                    SB.append(currentLine).append("\n");
                }

                return SB.toString();
            }
        }
        catch(MalformedURLException e){
            Log.d(TAG, "doInBackground: not working");
        }
        catch(ProtocolException e){
            Log.d(TAG, "doInBackground: p exception");
        }
        catch(IOException e){
            Log.d(TAG, "doInBackground: IOException");
        }
        return null;
    }
    
    private JSONObject getJSONBody() {
        Log.d(TAG, "getJSONBody: made it here!!");

        JSONObject jBody = new JSONObject();
        try {
            jBody.put("studentId", ID);
            jBody.put("username", currentUser.getUsername());
            jBody.put("password", currentUser.getPassword());
        } catch (JSONException e) {
            Log.d(TAG, "getJSONBody: json exception");
        }
        return jBody;
    }
}
