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

        //So that we can run network on UI thread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Get all news annoucement from Api
        JSONArray jsonArrayOfNews = BaseApi.getHttpRequest("https://free-lottery.herokuapp.com/api/get_announcement.php");

        mAuthTask = new GetNewsTask();
        mAuthTask.execute((Void) null);

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

    public class GetNewsTask extends AsyncTask<Void, Void, Boolean> {

        String REGISTER_URL = "https://free-lottery.herokuapp.com/api/get_announcement.php";


        GetNewsTask() {
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                // Simulate network access.
                // TODO: attempt authentication against a network service.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, REGISTER_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(getActivity(), "GetNews Async Task called.", Toast.LENGTH_LONG).show();
                                return;
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getActivity(), "Server busy. Please try to again later.", Toast.LENGTH_LONG).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                requestQueue.add(stringRequest);
                return true;
            }catch (Exception e) {
                return false;
            }
        }
    }


}
