package com.example.bharat.news;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;


public class NewsAdapter extends ArrayAdapter<News> {

    //private static final String LOCATION_SEPARATOR ="of";

    public NewsAdapter(Context context, List<News> newses){
        super(context,0,newses);
    }

    private String dateFormat(String simpleDate){
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM h:mm a");
        String formattedDate = null;
        try{
            formattedDate = outputFormat.format(inputFormat.parse(simpleDate));

        } catch (ParseException e) {
            Log.e("NewsAdapter", "Problem To Get Date", e);
        }
        return formattedDate;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.activity_main, parent, false);
        }
        News currentNews = getItem(position);

        TextView topicView = (TextView) listItemView.findViewById(R.id.news_topic);
        //for topic of news
        topicView.setText(currentNews.getmTopic());

        TextView titleVies = (TextView) listItemView.findViewById(R.id.news_title);
        //for title of news
        titleVies.setText(currentNews.getmTitle());

        TextView publisherView = (TextView) listItemView.findViewById(R.id.news_author);
        //for name of author but it will show no author
        publisherView.setText(currentNews.getmAuthor());

        TextView dateView = (TextView) listItemView.findViewById(R.id.news_date);
        String formattedDate = dateFormat(currentNews.getmDate());
        //for formatted date to show when it's published
        dateView.setText(formattedDate);

        return listItemView;
    }

}
