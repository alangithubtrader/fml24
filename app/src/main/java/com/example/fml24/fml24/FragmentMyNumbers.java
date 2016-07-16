package com.example.fml24.fml24;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ListFragment;

import com.example.fml24.fml24.API.BaseApi;
import com.example.fml24.fml24.Adaptor.MyNumbersAdaptor;
import com.example.fml24.fml24.Model.MyNumbers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by adu on 16-07-12.
 */
public class FragmentMyNumbers extends ListFragment{

    private MyNumbersAdaptor myNumbersAdaptor;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //So that we can run network on UI thread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Get all news annoucement from Api
        JSONArray jsonArrayOfNews = BaseApi.getHttpRequest("https://free-lottery.herokuapp.com/api/get_user_numbers.php?user_id=92");

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

}
