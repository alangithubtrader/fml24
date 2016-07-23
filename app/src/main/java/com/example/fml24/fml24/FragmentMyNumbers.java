package com.example.fml24.fml24;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v4.app.ListFragment;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fml24.fml24.API.BaseApi;
import com.example.fml24.fml24.Adaptor.MyNumbersAdaptor;
import com.example.fml24.fml24.Model.MyNumbers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by adu on 16-07-12.
 */
public class FragmentMyNumbers extends ListFragment{

    public static final String USER_ID = "user_id";

    private MyNumbersAdaptor myNumbersAdaptor;

    private GetMyNumbersTask mAuthTask = null;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //So that we can run network on UI thread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Get user id from Login Activity
        String userId = "";
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        userId = sharedPreferences.getString(getString(R.string.user_id_after_login), "");

        //Get all news annoucement from Api
        JSONArray jsonArrayOfNews = BaseApi.getHttpRequest("https://free-lottery.herokuapp.com/api/get_user_numbers.php?user_id=" + userId);

        mAuthTask = new GetMyNumbersTask(userId);
        mAuthTask.execute((Void) null);

        //Parse all news and add it to an array list
        ArrayList<MyNumbers> news = AddMyNumbersToArrayList(jsonArrayOfNews);
        myNumbersAdaptor = new MyNumbersAdaptor(getActivity(), news);

        //set all news into UI
        setListAdapter(myNumbersAdaptor);
    }

    private ArrayList<MyNumbers> AddMyNumbersToArrayList(JSONArray jsonArray) {
        ArrayList<MyNumbers> myNumbersArrayList = new ArrayList<>();
        try {
            for(int index = 0; index < jsonArray.length(); index++)
            {
                JSONObject jsonObject = Common.getJsonObject(jsonArray, index);

                String numbers = jsonObject.getString("numbers");
                String timeStamp = jsonObject.getString("timestamp");

                myNumbersArrayList.add(new MyNumbers(numbers, timeStamp, "Active"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return myNumbersArrayList;
    }

    public class GetMyNumbersTask extends AsyncTask<Void, Void, Boolean> {

        private final String userId;

        String REGISTER_URL = "https://free-lottery.herokuapp.com/api/get_user_numbers.php?user_id=";


        GetMyNumbersTask(String userIdd) {
            userId = userIdd;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                // Simulate network access.
                // TODO: attempt authentication against a network service.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, REGISTER_URL + userId,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(getActivity(), "GetMyNumbers Async Task called.", Toast.LENGTH_LONG).show();
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
                        params.put(USER_ID, userId);
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
