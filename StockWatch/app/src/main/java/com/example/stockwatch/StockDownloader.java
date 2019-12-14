package com.example.stockwatch;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class StockDownloader extends AsyncTask<String, Double, String> {

    private MainActivity mainActivity;

 private final String APIKEY = "sk_cfb4c1ab260446a0b35fb7b5df7b2b40";
    private String rawURL = "https://cloud.iexapis.com/stable/stock/";
    private final String postfix="/quote?token=";
    private String requestMethod = "GET";
    private static final String TAG = "StockDownloader";

    //Symbol and name for stocks, pos for keeping track of list position
    private String symbol;
    private String name;
    private int position;

    public StockDownloader(MainActivity ma, int pos){
        this.mainActivity = ma;
        this.position = pos;
    }

    @Override
    protected String doInBackground(String... strArgs){

        //Init Stock symbol and name
        this.symbol = strArgs[0];
        this.name = strArgs[1];

        // URI/URL creation and StringBuilder
        rawURL = rawURL + strArgs[0] + postfix+APIKEY;
        Uri rawURI = Uri.parse(rawURL);
        String parsedURI = rawURI.toString();
        StringBuilder stringBuilder = new StringBuilder();

        try{
            //set up a connection to URL
            URL url = new URL(parsedURI);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(requestMethod);

            Log.d(TAG, "doInBackground: GET method worked!");

            //Parse JSON
            InputStream inputStream = con.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            //put newly parsed JSON into a string
            String currentLine;
            while((currentLine =  bufferedReader.readLine()) != null){
                stringBuilder.append(currentLine).append("\n");
            }
            return stringBuilder.toString();
        }
        catch(MalformedURLException e){
            e.printStackTrace();
            return null;
        }
        catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String str){
        super.onPostExecute(str);

        try{

            JSONObject jo = new JSONObject(str);

            ///Get relevant stock data latestPrice, change, changePercent
            Double latestPrice = Double.valueOf(jo.getDouble("latestPrice"));
            Double change = Double.valueOf(jo.getDouble("change"));
            Double changePercent = Double.valueOf(jo.getDouble("changePercent"));

            //Make that new stock object
            Stock s = new Stock(this.symbol, this.name, latestPrice, change, changePercent);


            if(this.position > -1){
                //If stock exists UPDATE STOCK s
                this.mainActivity.updateStock(s, this.position);
            }

            else{
                this.mainActivity.addStock(s);  //If stock doesn't exist ADD STOCK s
            }
        }
        catch(JSONException e){
            e.printStackTrace();
            return;
        }
    }
}
