package com.example.fml24.fml24;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fml24.fml24.API.BaseApi;
import com.example.fml24.fml24.Adaptor.NewsAdaptor;
import com.example.fml24.fml24.Model.News;
import com.example.fml24.fml24.Common;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by adu on 16-07-12.
 */
public class FragmentNews extends ListFragment{

    private NewsAdaptor newsAdaptor;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //So that we can run network on UI thread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Get all news annoucement from Api
        JSONArray jsonArrayOfNews = BaseApi.getHttpRequest("https://free-lottery.herokuapp.com/api/get_announcement.php");

        //Parse all news and add it to an array list
        ArrayList<News> news = AddNewsToArrayList(jsonArrayOfNews);
        newsAdaptor = new NewsAdaptor(getActivity(), news);

        //set all news into UI
        setListAdapter(newsAdaptor);
    }


    private ArrayList<News> AddNewsToArrayList(JSONArray jsonArray) {
        ArrayList<News> news = new ArrayList<>();
        try {
            for(int index = 0; index < jsonArray.length(); index++)
            {
                JSONObject jsonObject = Common.getJsonObject(jsonArray, index);

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
