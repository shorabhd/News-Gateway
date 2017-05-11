package com.developer.shorabhd.newsgateway;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by shorabhd on 5/6/17.
 */

public class MyFragment extends Fragment {

    public static final String TAG = ".NewsFragment";
    TextView textView, textView1, textView2,textView3, textView4;
    ImageView imageView;
    static MainActivity ma;

    public static final MyFragment newInstance(MainActivity m, Article a, int pos, int size)
    {
        ma = m;
        MyFragment f = new MyFragment();
        Bundle bdl = new Bundle(1);
        bdl.putSerializable("Article", a);
        bdl.putInt("Position", pos);
        bdl.putInt("Total", size);
        f.setArguments(bdl);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "NewsFragment: onCreateView: ");

        final Article articles = (Article) getArguments().getSerializable("Article");
        int position  = getArguments().getInt("Position");
        int length  = getArguments().getInt("Total");


        View v = inflater.inflate(R.layout.myfragment_layout, container, false);
        textView=(TextView) v.findViewById(R.id.source);
        textView1=(TextView) v.findViewById(R.id.description);
        imageView = (ImageView)v.findViewById( R.id.imageNews);
        textView2=(TextView) v.findViewById(R.id.authorName);
        textView3=(TextView) v.findViewById(R.id.publishDate);
        textView4=(TextView) v.findViewById(R.id.position);

        textView1.setMovementMethod(new ScrollingMovementMethod());

        ma.openPicassoPhoto(articles.getUrlToImage(),imageView);
        textView.setText(articles.getTitle());
        textView1.setText(articles.getDesc());


        if(articles.getAuthor().equals("null") || articles.getAuthor().isEmpty())
            textView2.setText("");

        else
            textView2.setText(articles.getAuthor());

        if(articles.getPublishDate().equals("null") || articles.getPublishDate().isEmpty())
            textView3.setText("");

        else {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
            SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.ENGLISH);
            try{
                Date d = sdf.parse(articles.getPublishDate());
                String formattedTime = output.format(d);
                textView3.setText(formattedTime);
            }
            catch (ParseException e){
                e.printStackTrace();
            }

        }

        position=position+1;
        String pos = position +" of "+ length;
        textView4.setText(pos);


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: textView:"+textView.getText().toString() + articles.getUrl());
                clickNews(v, articles.getUrl());

            }
        });

        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: textView1:"+textView.getText().toString() + articles.getUrl());
                clickNews(v, articles.getUrl());

            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: imageView:"+textView.getText().toString() + articles.getUrl());
                clickNews(v, articles.getUrl());

            }
        });

        return v;
    }
    public void clickNews(View v, String url){
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}
