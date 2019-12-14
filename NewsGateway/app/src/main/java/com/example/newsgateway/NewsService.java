package com.example.newsgateway;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import java.util.ArrayList;


import static com.example.newsgateway.MainActivity.Msg_To_Service;

public class NewsService  extends Service {
    private static final String TAG = "NewsService";
    private ArrayList<Article> articleList = new ArrayList<>(); //need this
    private ServiceReceiver sReceiver = new ServiceReceiver(); //need this
    private NewsService new_ser = this;
    private boolean isRunning = true; //need this to check

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        IntentFilter filter = new IntentFilter(Msg_To_Service);
        registerReceiver(sReceiver, filter);

        new Thread(new Runnable() {
            @Override
            public void run() {

                while(isRunning){
                    while(articleList.isEmpty()){
                        try{
                            Thread.sleep(300);
                        } catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                    sendArticles(articleList);
                }
            }
        }).start();

        return Service.START_STICKY; //professor mentioned this in class
    }

    public void setArticles(ArrayList<com.example.newsgateway.Article> articles_set){ //fixed
        articleList.clear();
        for(Article as: articles_set){
            articleList.add(as);
        }
    }

    public void sendArticles(ArrayList<Article> articles_sent){ //fixed
        Intent intent = new Intent();
        intent.setAction(MainActivity.News_Story);
        intent.putExtra(MainActivity.Story_List, articles_sent);
        intent.putExtra("Page", 0);
        sendBroadcast(intent);
        articleList.clear();
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(sReceiver);
        isRunning = false;
        super.onDestroy();
    }//done copied from professor' code example

    class ServiceReceiver extends BroadcastReceiver { //done
        @Override
        public void onReceive(Context context, Intent intent) {
            switch(intent.getAction()){
                case Msg_To_Service:
                    String id = intent.getStringExtra("SourceID");
                    new News_Article_Downloader(new_ser,id).execute();
                    break;
            }
        }
    }
}
