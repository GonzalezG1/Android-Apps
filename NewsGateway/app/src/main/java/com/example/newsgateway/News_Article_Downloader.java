package com.example.newsgateway;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class News_Article_Downloader extends AsyncTask<String, Void, String> {
    private static final String TAG = "News_Article_Downloader";
    private NewsService new_service;
    private String id;
    private String APIkey="&apiKey=1c7427461c7a4da596067ce12bb5119e";
    private final String articleURL = "https://newsapi.org/v2/top-headlines?sources=";

    public News_Article_Downloader(NewsService ns, String id) {
        new_service = ns;
        this.id = id;
    }

    @Override
    protected String doInBackground(String... strings) {
        Log.d(TAG, "doInBackground: ");
        String finalURL = articleURL+id+APIkey;
        Uri dataUri = Uri.parse(finalURL);
        String urlToUse = dataUri.toString();
        StringBuilder sb = new StringBuilder();

        try{
            URL url = new URL(urlToUse);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader((new InputStreamReader(is)));
            String line;
            while((line=reader.readLine()) != null){
                sb.append(line);
            }
            return sb.toString();
        } catch (Exception e){
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d(TAG, "onPostExecute: ");
        ArrayList<com.example.newsgateway.Article> articles = parseJSON(s);
        new_service.setArticles(articles);
    }

    private ArrayList<com.example.newsgateway.Article> parseJSON(String json){
        //this is where i usually mess up!!!!!!!!!!!!!!!!!!!!!

        Log.d(TAG, "parseJSON: ");
        ArrayList<com.example.newsgateway.Article> articleList = new ArrayList<>();
        try{
            JSONObject obj = new JSONObject(json);
            JSONArray articles = obj.getJSONArray("articles");
            for(int i=0; i<articles.length(); i++){
                JSONObject article = articles.getJSONObject(i);
                String author=null, title=null, description=null, url=null, urlToImage=null, publishedAt=null;
                if(article.has("author")){
                    author = article.getString("author");
                }
                if(article.has("title")){
                    title = article.getString("title");
                }
                if(article.has("description")){
                    description = article.getString("description");
                }
                if(article.has("url")){
                    url = article.getString("url");
                }
                if(article.has("urlToImage")){
                    urlToImage = article.getString("urlToImage");
                }
                if(article.has("publishedAt")){
                    publishedAt = article.getString("publishedAt");
                }
                com.example.newsgateway.Article a = new com.example.newsgateway.Article(author, title, description, url, urlToImage, publishedAt);
                articleList.add(a);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return articleList;
    }

}
