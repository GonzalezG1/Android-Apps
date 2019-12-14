package com.example.inspirationrewards;


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
import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static java.net.HttpURLConnection.HTTP_OK;

public class CreateProfileAsyncTask extends AsyncTask<String, Double, String> {

    //not the same as get all profiles

    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String department;
    private String position;
    private String story;
    private String location;
    private String photo;
    private boolean admin;
    //I think I need this

    private Create_Profile_Activity create_Activity;
    private static final String ID="A20348998";
    private static final String URL="http://inspirationrewardsapi-env.6mmagpm2pv.us-east-2.elasticbeanstalk.com";

    private static boolean stat = false; //just like edit_activity?

    public CreateProfileAsyncTask(
            String username,
            String password,
            String firstname,
            String lastname,
            String department,
            String position,
            String story,
            boolean admin,
            String location,
            String photo,
            Create_Profile_Activity create_Activity
    ){
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.department = department;
        this.position = position;
        this.story = story;
        this.admin = admin;
        this.location = location;
        this.photo = photo;
        this.create_Activity = create_Activity;

}
    @Override
    protected String doInBackground(String... strArgs){
        String rawURL = URL + "/profiles";
        BufferedReader BR = null;
        HttpURLConnection con = null;

        try{
            Uri parsedURI = Uri.parse(rawURL);
            URL url = new URL(parsedURI.toString());
            con = (HttpURLConnection) url.openConnection();

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
                while(null != (currentLine = BR.readLine())){
                    SB.append(currentLine).append("\n");
                }

                stat = true;
                return SB.toString();
            }
            else{
                BR = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                String currentLine;
                while(null != (currentLine = BR.readLine())){
                    SB.append(currentLine).append("\n");
                }
                return SB.toString();
            }
        }
        catch(MalformedURLException e){
            Log.d("Foo", "doInBackground: malformed url");
        }
        catch(IOException e){
            Log.d("Foo", "doInBackground: " + e);
        }
        finally{
            //Close connection
            if(con != null){
                con.disconnect();
            }
            if(BR != null){
                try{
                    BR.close();
                }
                catch(IOException e){
                    Log.d(TAG, "doInBackground: error closing bufferedreader");
                }
            }
        }
        return null;
    }
    @Override
    protected void onPostExecute(String str){
        if(stat) {
            Log.d("Foo", "onPostExecute: success" + str);
            User user = new User(
                    username,
                    password,
                    firstname,
                    lastname,
                    department,
                    position,
                    story,
                    admin,
                    1000,
                    Base64.decode(photo, Base64.DEFAULT),
                    location,
                    new ArrayList<Reward>()
            );
            this.create_Activity.to_Profile(user);
        }
        else{
            Log.d("Foo", "onPostExecute: failure" + str);
        }
    }
    private JSONObject getJSONBody(){

        JSONObject jBody = new JSONObject();
        try{
            jBody.put("studentId", ID);
            jBody.put("username", this.username);
            jBody.put("password", this.password);
            jBody.put("firstName", this.firstname);
            jBody.put("lastName", this.lastname);
            jBody.put("department", this.department);
            jBody.put("story", this.story);
            jBody.put("position", this.position);
            jBody.put("admin", this.admin);
            jBody.put("location", this.location);
            jBody.put("imageBytes", this.photo);
            jBody.put("pointsToAward", 1000);
            jBody.put("rewardRecords", new JSONArray()); //I added this yesterday, not sure why?
        }
        catch(JSONException e){
            Log.d(TAG, "getJSONBody: something went wrong in making a json body");
        }
        return jBody;
    }
}


