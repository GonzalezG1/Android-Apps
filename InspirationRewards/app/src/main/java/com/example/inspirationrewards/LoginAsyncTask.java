package com.example.inspirationrewards;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static java.net.HttpURLConnection.HTTP_OK;

//should be done!!!
public class LoginAsyncTask extends AsyncTask<String, Double, String> {
    private static final String TAG = "LoginAsyncTask";
    private static final String ID="A20348998";
    private static final String baseURL="http://inspirationrewardsapi-env.6mmagpm2pv.us-east-2.elasticbeanstalk.com";
    private static boolean status = false;

    @SuppressLint("StaticFieldLeak")
    private MainActivity mainActivity; //this is connected to main instead of a login activity
    //does the same thing but just a different name
    private String username;
    private String password;
    private String city;
    private String state;
    private int responseCode;
    private String rawURL = "";
    private String requestMethod = "";

    public LoginAsyncTask(MainActivity ma, String username, String password, String city, String state){
        this.mainActivity = ma;
        this.username = username;
        this.password = password;
        this.city = city;
        this.state = state;
    }
    @Override
    protected String doInBackground(String... args){

        String rawURL = baseURL + "/login";
        BufferedReader BR = null;
        HttpURLConnection con = null;

        JSONObject jBody = new JSONObject();
        try {
            Log.d(TAG, "doInBackground: ");
            Uri parsedURI = Uri.parse(rawURL);
            URL url = new URL(parsedURI.toString());
            con = (HttpURLConnection) url.openConnection();

            jBody.put("studentId", ID);
            jBody.put("username", this.username);
            jBody.put("password", this.password);

            con.setRequestMethod("POST");
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

                status = true;
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

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
    @Override
    protected void onPostExecute(String str){
        if(str != null){
            if(status){
                Log.d("Login", "onPostExecute: " + str);

                try{
                    JSONObject jObject = new JSONObject(str);
                    String firstname = jObject.getString("firstName");
                    String lastname = jObject.getString("lastName");
                    String username = jObject.getString("username");
                    String password = jObject.getString("password");
                    String department = jObject.getString("department");
                    String story = jObject.getString("story");
                    String position = jObject.getString("position");
                    int pointsToAward = jObject.getInt("pointsToAward");
                    boolean admin = jObject.getBoolean("admin");
                    byte[] photo = Base64.decode(jObject.getString("imageBytes"), Base64.DEFAULT);
                    String location = city + ", " + state;
                    List<Reward> rewardList = new ArrayList<>();
                    JSONArray jArray = new JSONArray(jObject.getString("rewards"));

                    for(int i=0; i < jArray.length(); i++){
                        JSONObject currentObject = jArray.getJSONObject(i);
                        Reward newReward = new Reward(
                                currentObject.getInt("value"),
                                currentObject.getString("username"),
                                currentObject.getString("name"),
                                currentObject.getString("notes"),
                                currentObject.getString("date")
                        );
                        rewardList.add(newReward);
                    }

                    User currentUser = new User(username, password, firstname, lastname, department, position, story, admin, pointsToAward, photo, location, rewardList);
                    this.mainActivity.goToProfile(currentUser);
                }
                catch(JSONException e){
                    Log.d(TAG, "onPostExecute: JSONObject created");
                }
            }
            else{
                Log.d("Login", "onPostExecute: " + str);
                Toast.makeText(mainActivity, "Username or password is not corrrect", Toast.LENGTH_SHORT).show();
               mainActivity.progressBar.setVisibility(View.GONE);
            }
        }
    }


}
