package com.example.fml24.fml24;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fml24.fml24.API.BaseApi;
import com.example.fml24.fml24.Adaptor.NewsAdaptor;
import com.example.fml24.fml24.Model.News;
import com.example.fml24.fml24.Common;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by adu on 16-07-12.
 */
public class FragmentNews extends ListFragment{

    private NewsAdaptor newsAdaptor;
    private GetNewsTask mAuthTask = null;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Fetch all news using async task
        mAuthTask = new GetNewsTask();
        mAuthTask.execute((Void) null);
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

    public class GetNewsTask extends AsyncTask<Void, Void, JSONArray> {

        String REGISTER_URL = "https://free-lottery.herokuapp.com/api/get_announcement.php";

        GetNewsTask() {
        }

        @Override
        protected JSONArray doInBackground(Void... params) {

            //Get all news annoucement from Api
            return BaseApi.getHttpRequest("https://free-lottery.herokuapp.com/api/get_announcement.php");
        }

        @Override
        protected void onPostExecute(JSONArray jsonArrayOfNews)
        {
            //todo: add error handling when no internet / JSONArray is null

            //Parse all news and add it to an array list
            ArrayList<News> news = AddNewsToArrayList(jsonArrayOfNews);
            newsAdaptor = new NewsAdaptor(getActivity(), news);

            //set all news into UI
            setListAdapter(newsAdaptor);
        }
    }


}
