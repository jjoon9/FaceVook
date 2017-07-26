package com.example.joon.facevook;

import android.content.Context;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Joon on 2017-07-22.
 */

public class ArticleLab {

    private static ArticleLab sArticleLab;
    private List<Article> mArticles;

    private Article getExmapleArticle(){
        String profileImage = "https://scontent-hkg3-1.xx.fbcdn.net/v/t1.0-1/c0.39.100.100/p100x100/18881788_1374109939347364_2755026976106239427_n.jpg?oh=f9fa78097ca0b57179516906f41df267&oe=59FCBEC1";
        String writer = "김준구";
        String time = "2017-03-02 17:00:00";
        String contentText="테스트테스트";
        String contentImage="https://scontent-hkg3-1.xx.fbcdn.net/v/t31.0-0/p526x296/20157168_1418089554949402_812759217658178995_o.jpg?oh=1e709670eb3379db61dd80d64f9d6911&oe=59FD3EBF";
        int likes=32533;
        int comments=5153;
        int shares=2414;
        return new Article(profileImage,writer,time,contentText,contentImage,likes,comments,shares);
    }
    String OkHttpRes="";
    String OkHttpUrl="";
    private String okHttpRun(String url) {
        OkHttpUrl = url;
        Thread mThread = new Thread(){
            @Override
            public void run() {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(OkHttpUrl).build();
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    OkHttpRes = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        };
        mThread.start();
        try{
            mThread.join();
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        return OkHttpRes;


    }

    private Article getExampleArticlesByGraphAPI()  {
        String myJsonData = okHttpRun("https://graph.facebook.com/dokchi4/posts");
        String contentText = myJsonData;
        String profileImage = "https://scontent-hkg3-1.xx.fbcdn.net/v/t1.0-1/c0.39.100.100/p100x100/18881788_1374109939347364_2755026976106239427_n.jpg?oh=f9fa78097ca0b57179516906f41df267&oe=59FCBEC1";
        String writer = "김준구";
        String time = "2017-03-02 17:00:00";
        String contentImage="https://scontent-hkg3-1.xx.fbcdn.net/v/t31.0-0/p526x296/20157168_1418089554949402_812759217658178995_o.jpg?oh=1e709670eb3379db61dd80d64f9d6911&oe=59FD3EBF";
        int likes=3;
        int comments=5;
        int shares=2;
        return new Article(profileImage,writer,time,contentText,contentImage,likes,comments,shares);

    }

    public ArticleLab(Context context) {
        mArticles = new ArrayList<>();
//        mArticles.add(getExampleArticlesByGraphAPI());
        mArticles.add(getExmapleArticle());
        mArticles.add(getExmapleArticle());



    }

    public static ArticleLab get(Context context){
        if(sArticleLab == null){
            sArticleLab = new ArticleLab(context);
        }
        return sArticleLab;
    }

    public List<Article> getArticles(){
        return mArticles;
    }

    public Article getArticle(UUID id){
        for (Article article : mArticles){
            if (article.getId().equals(id)){
                return article;
            }
        }
        return null;
    }


}
