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

    private MyNumbersAdaptor myNumbersAdaptor;

    private GetMyNumbersTask mAuthTask = null;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Get user id from Login Activity
        String userId = "";
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        userId = sharedPreferences.getString(getString(R.string.user_id_after_login), "");

        //Fetch all my numbers via user id in an async task
        mAuthTask = new GetMyNumbersTask(userId);
        mAuthTask.execute((Void) null);

    }

    private ArrayList<MyNumbers> AddMyNumbersToArrayList(JSONArray jsonArray) {

        ArrayList<MyNumbers> myNumbersArrayList = new ArrayList<>();
        try {
            for(int index = 0; index < jsonArray.length(); index++)
            {
                JSONObject jsonObject = Common.getJsonObject(jsonArray, index);

                String numbers = jsonObject.getString("numbers");
                String timeStamp = jsonObject.getString("timestamp");

                //TODO: need to implement logic to determine state of my numbers
                myNumbersArrayList.add(new MyNumbers(numbers, timeStamp, "Active"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return myNumbersArrayList;
    }

    public class GetMyNumbersTask extends AsyncTask<Void, Void, JSONArray> {

        private final String userId;

        String REGISTER_URL = "https://free-lottery.herokuapp.com/api/get_user_numbers.php?user_id=";

        GetMyNumbersTask(String userIdd) {
            userId = userIdd;
        }

        @Override
        protected JSONArray doInBackground(Void... params) {
            return BaseApi.getHttpRequest(REGISTER_URL + userId);
        }

        @Override
        protected void onPostExecute(JSONArray jsonArrayOfMyNumbers) {

            //Parse all of my numbers and add it to an array list
            ArrayList<MyNumbers> myNumbers = AddMyNumbersToArrayList(jsonArrayOfMyNumbers);
            myNumbersAdaptor = new MyNumbersAdaptor(getActivity(), myNumbers);

            //set all of my numbers into UI
            setListAdapter(myNumbersAdaptor);
        }
    }


}
