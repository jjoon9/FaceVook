package com.example.joon.facevook;

import java.util.UUID;

/**
 * Created by Joon on 2017-07-22.
 */

public class Article {
    private String mProfileImage="";
    private String mWriter="";
    private String mTime="";
    private String mContentText="";
    private String mContentImage="";
    private int mLikes=0;
    private int mComments=0;
    private int mShares=0;
    private UUID mId;

    public Article(String profileImage, String writer, String time, String contentText, String contentImage, int likes, int comments, int shares) {
        mProfileImage = profileImage;
        mWriter = writer;
        mTime = time;
        mContentText = contentText;
        mContentImage = contentImage;
        mLikes = likes;
        mComments = comments;
        mShares = shares;

        mId = UUID.randomUUID();
    }

    public void setLikes(int likes){
        mLikes+=likes;
    }
    public Article(){
        mId = UUID.randomUUID();
    }

    public UUID getId(){
        return mId;
    }

    public String getWriter() {
        return mWriter;
    }

    public String getTime() {
        return mTime;
    }

    public String getContentText() {
        return mContentText;
    }

    public String getProfileImage() {
        return mProfileImage;
    }

    public String getContentImage() {

        return mContentImage;
    }

    public int getLikes() {
        return mLikes;
    }

    public int getComments() {
        return mComments;
    }

    public int getShares() {
        return mShares;
    }
    public String getInfoLeft(){
        return "좋아요 " + mLikes +"개";
    }
    public String getInfoRight(){
        return "댓글 " + mComments +"개  공유 " + mShares+"회";
    }
    //@@이거 있을필요없지. getLikes ..만 쓰는걸로해야지 이따구로 코딩하지말자.근데귀찮으니깐 일단냅두자 ㅇㅇ
}
