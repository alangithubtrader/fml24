package com.example.fml24.fml24;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ListFragment;
import android.util.Log;

import com.example.fml24.fml24.API.BaseApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * Created by adu on 16-07-12.
 */
public class FragmentNews extends ListFragment{

    private ArrayList<News> news;
    private NewsAdaptor newsAdaptor;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        JSONArray jsonArrayOfNews = BaseApi.getHttpRequest("https://free-lottery.herokuapp.com/api/get_announcement.php");

        ArrayList<News> news = AddNewsToArrayList(jsonArrayOfNews);

        newsAdaptor = new NewsAdaptor(getActivity(), news);

        setListAdapter(newsAdaptor);


    }

    private ArrayList<News> AddNewsToArrayList(JSONArray jsonArray) {
        ArrayList<News> news = new ArrayList<>();
        try {
            for(int index = 0; index < jsonArray.length(); index++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(index);

                String title = jsonObject.getString("title");
                String timeStamp = jsonObject.getString("timestamp");
                String description = jsonObject.getString("description");

                news.add(new News(title, timeStamp, description));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return news;
    }
}
