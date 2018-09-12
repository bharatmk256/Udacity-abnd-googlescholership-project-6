package com.example.bharat.news;

public class News {

    private String mTitle;

    private String mTopic;

    private String mAuthor;

    private String mUrl;

    private String mDate;

    public News(String title, String topic, String author, String url, String  date){
        mTitle = title;
        mTopic = topic;
        mAuthor = author;
        mUrl = url;
        mDate = date;
    }
    public String getmTitle(){
        return mTitle;
    }
    public String getmTopic(){
        return mTopic;
    }
    public String getmAuthor(){
        return mAuthor;
    }
    public String getmUrl(){
        return mUrl;
    }
    private String getmDate(){
        return mDate;
    }

}
