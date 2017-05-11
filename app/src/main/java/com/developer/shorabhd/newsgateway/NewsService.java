package com.developer.shorabhd.newsgateway;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import java.util.ArrayList;

/**
 * Created by shorabhd on 5/6/17.
 */

public class NewsService extends Service {

    private static String ACTION_MSG_TO_SERVICE="ACTION_MSG_TO_SERVICE";
    private static String ACTION_NEWS_STORY = "ACTION_NEWS_STORY";
    private static final String TAG = ".NewsService";
    private boolean isRunning = true;
    private ArrayList<Article> articleList = new ArrayList<Article>();
    private ServiceReceiver serviceReceiver;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        serviceReceiver = new ServiceReceiver();
        IntentFilter filter = new IntentFilter(ACTION_MSG_TO_SERVICE);
        registerReceiver(serviceReceiver, filter);
        new Thread(new Runnable() {
                @Override
                public void run() {
                    while (isRunning) {
                        while (articleList.isEmpty()){
                            try {
                                Thread.sleep(250);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        if (!articleList.isEmpty()) {
                            Log.d(TAG,"SHORABH "+articleList.size());
                            Intent intent = new Intent();
                            intent.setAction(ACTION_NEWS_STORY);
                            intent.putExtra("articleList",articleList);
                            sendBroadcast(intent);
                        }
                        articleList.clear();
                        Log.d(TAG, "run: ");
                    }
                 }
        }).start();
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Destroying the service of NewsService!", Toast.LENGTH_SHORT).show();
        unregisterReceiver(serviceReceiver);
        isRunning=false;
        super.onDestroy();
    }

    public void setArticles(ArrayList<Article> articleList) {
        Log.d(TAG,String.valueOf(articleList.size()));
        if(!articleList.isEmpty()){
            this.articleList.clear();
            this.articleList = articleList;
        }
    }

    public class ServiceReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent.getAction().equals(ACTION_MSG_TO_SERVICE)){
                String sourceId = intent.getStringExtra("SOURCE_OBJECT");
                new NewsArticleDownloader(NewsService.this).execute(sourceId);
            }
        }
    }
}
