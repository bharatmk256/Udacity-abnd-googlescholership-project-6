package com.example.bharat.news;

import android.text.TextUtils;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class QueryUtils {
    private static final  String LOG_TAG = QueryUtils.class.getSimpleName();
    private QueryUtils(){
    }

    public static List <News> fetchNewsData(String requestUrl){
        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try{
            jsonResponse = makeHttpRequest(url);
        }catch (IOException e){
            Log.e(LOG_TAG, "proble making HTTP REQUEST.",e);
        }
        List<News> news = extractFeatureFromJson(jsonResponse);
        return news;
    }

    private static URL createUrl(String stringUrl){
        URL url = null;
        try{
            url = new URL(stringUrl);
        }catch (MalformedURLException e){
            Log.e(LOG_TAG,"problem building URL",e);
        }
        return url;
    }
    private static String makeHttpRequest(URL url)throws IOException{
        String jsonResponse = "";

        if (url == null){
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* in milliSecond */);
            urlConnection.setReadTimeout(15000 /* in milliSecond */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode()==200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }else {
                Log.e(LOG_TAG,"Error response code: " + urlConnection.getResponseCode());
            }
        }catch (IOException e){
            Log.e(LOG_TAG ,"error retrieving json Response",e);
        }
        finally {
            if (urlConnection != null ){
                urlConnection.disconnect();
            }if (inputStream !=null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }
    private static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output = new StringBuilder();
        if (inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null){
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
    private static List<News> extractFeatureFromJson(String newsJSON){
        if (TextUtils.isEmpty(newsJSON)){
            return null;
        }
        List<News> newses = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(newsJSON);
            JSONObject responseObject = baseJsonResponse.getJSONObject("response");
            JSONArray newsArray = responseObject.getJSONArray("results");


            for (int i = 0; i < newsArray.length(); i++){
                JSONObject currentNews = newsArray.getJSONObject(i);
                JSONArray tags = currentNews.optJSONArray("tags");
                String  newsTitle = currentNews.getString("webTitle");
                String topic = currentNews.getString("sectionName");
                String authorName = "";
                String url = currentNews.getString("webUrl");
                String date = currentNews.getString("webPublicationDate");

                if (tags.length()>0){
                    JSONObject jsonObject = tags.optJSONObject(0);
                    authorName = jsonObject.getString("webTitle");
                }

                News news = (new News(newsTitle,topic,authorName,url,date));
                newses.add(news);
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the NEWS JSON results", e);
        }
        return newses;
    }

}
