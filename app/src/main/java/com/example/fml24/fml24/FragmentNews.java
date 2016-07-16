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

        JSONArray test = null;
            test = BaseApi.getHttpRequest("https://free-lottery.herokuapp.com/api/get_announcement.php");
        news = new ArrayList<>();
        try {

            JSONObject jsonobject = test.getJSONObject(0);
            String name = jsonobject.getString("description");


            news.add(new News("News Title 1", "Feb 6, 2010, 2:30pm", name));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        news.add(new News("News Title 2", "Feb 29, 2011, 7:30pm", "Body2"));
        news.add(new News("News Title 3", "Feb 19, 2016, 9:30pm", "Body3"));

        newsAdaptor = new NewsAdaptor(getActivity(), news);

        setListAdapter(newsAdaptor);


    }
}
